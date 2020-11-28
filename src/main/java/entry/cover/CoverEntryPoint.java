package entry.cover;

import common.comparator.PiecesNumberComparator;
import common.cover.*;
import common.datastore.MinoOperationWithKey;
import common.datastore.blocks.LongPieces;
import common.datastore.blocks.Pieces;
import common.pattern.PatternGenerator;
import common.tetfu.common.ColorConverter;
import common.tetfu.common.ColorType;
import common.tetfu.field.ColoredField;
import common.tetfu.field.ColoredFieldFactory;
import common.tetfu.field.ColoredFieldView;
import core.action.reachable.HarddropReachable;
import core.action.reachable.LockedReachable;
import core.action.reachable.Reachable;
import core.action.reachable.SoftdropTOnlyReachable;
import core.field.Field;
import core.mino.MinoFactory;
import core.mino.MinoShifter;
import core.mino.Piece;
import core.srs.MinoRotation;
import entry.DropType;
import entry.EntryPoint;
import entry.Verify;
import entry.path.output.MyFile;
import exceptions.FinderException;
import exceptions.FinderExecuteException;
import exceptions.FinderInitializeException;
import exceptions.FinderTerminateException;
import lib.Stopwatch;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CoverEntryPoint implements EntryPoint {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private final CoverSettings settings;
    private final BufferedWriter logWriter;

    public CoverEntryPoint(CoverSettings settings) throws FinderInitializeException {
        this.settings = settings;

        // ログファイルの出力先を整備
        String logFilePath = settings.getLogFilePath();
        MyFile logFile = new MyFile(logFilePath);

        logFile.mkdirs();
        logFile.verify();

        try {
            this.logWriter = logFile.newBufferedWriter();
        } catch (IOException e) {
            throw new FinderInitializeException(e);
        }
    }

    @Override
    public void run() throws FinderException {
        int height = 24;

        ColorConverter colorConverter = new ColorConverter();

        List<CoverParameter> parameters = settings.getParameters();

        output("# Setup Field");

        for (CoverParameter parameter : parameters) {
            // Setup field
            Field field = parameter.getField();
            Verify.field(field);

            ColoredField coloredField = ColoredFieldFactory.createGrayField(field);
            parameter.getOperationList().forEach(minoOperationWithKey -> {
                ColorType colorType = colorConverter.parseToColorType(minoOperationWithKey.getPiece());
                Field minoField = minoOperationWithKey.createMinoField(height);
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < 10; x++) {
                        if (minoField.exists(x, y)) {
                            coloredField.setColorType(colorType, x, y);
                        }
                    }
                }
            });

            output(String.format("[%s]", parameter.getLabel()));
            output(ColoredFieldView.toStringWithType(coloredField, Math.min(coloredField.getUsingHeight() + 1, height)));
        }

        output("Using hold: " + (settings.isUsingHold() ? "use" : "avoid"));
        output("Drop: " + settings.getDropType().name().toLowerCase());
        output("Mode: " + settings.getCoverModes().name().toLowerCase());
        output("Priority: " + (settings.isUsingPriority() ? "yes" : "no"));

        // ========================================
        output("Searching patterns:");

        // Setup patterns
        List<String> patterns = settings.getPatterns();
        PatternGenerator generator = Verify.patterns(patterns);

        // Output patterns
        for (String pattern : patterns)
            output("  " + pattern);

        // 探索パターンの列挙
        List<LongPieces> piecesList = generator.blocksStream()
                .collect(Collectors.toSet())
                .stream()
                .sorted(PiecesNumberComparator::comparePieces)
                .map(Pieces::getPieces)
                .map(LongPieces::new)
                .collect(Collectors.toList());

        output();

        output("Searching pattern size ( no dup. ) = " + piecesList.size());

        output();

        // ========================================

        output("# Calculate");
        output("  -> Stopwatch start");
        output("     ... calculating");

        Stopwatch stopwatch = Stopwatch.createStartedStopwatch();

        ReachableForCover reachableForCover = new ReachableForCoverWrapper(createReachable(settings.getDropType(), height));
        List<BitSet> results = new ArrayList<>();

        CoverModes mode = settings.getCoverModes();

        // Check
        Cover cover;
        switch (mode) {
            case Normal: {
                cover = new NormalCover();
                break;
            }
            case B2BContinuous: {
                cover = new B2BContinuousCover();
                break;
            }
            case AnyTSpin: {
                cover = new AnyTSpinCover();
                break;
            }
            case TSpinSingle: {
                cover = new RegularTSpinCover(1);
                break;
            }
            case TSpinDouble: {
                cover = new RegularTSpinCover(2);
                break;
            }
            case TSpinTriple: {
                cover = new RegularTSpinCover(3);
                break;
            }
            case Tetris: {
                cover = new TetrisCover();
                break;
            }
            case TetrisEnd: {
                cover = new TetrisEndCover();
                break;
            }
            default: {
                throw new IllegalStateException("Unknown cover mode: " + mode);
            }
        }

        boolean isUsingPrioritized = settings.isUsingPriority();

        int parameterSize = parameters.size();
        piecesList.forEach(pieces -> {
            BitSet result = new BitSet(parameterSize);

            List<Piece> pieceList = pieces.blockStream().collect(Collectors.toList());

            for (int index = 0; index < parameterSize; index++) {
                CoverParameter parameter = parameters.get(index);
                List<MinoOperationWithKey> operations = parameter.getOperationList();
                Field field = parameter.getField();

                int maxDepth = operations.size();
                boolean success = settings.isUsingHold() ? cover.canBuildWithHold(
                        field, operations.stream(), pieceList, height, reachableForCover, maxDepth
                ) : cover.canBuild(
                        field, operations.stream(), pieceList, height, reachableForCover, maxDepth
                );

                if (success) {
                    result.set(index);

                    if (isUsingPrioritized) {
                        break;
                    }
                }
            }

            results.add(result);
        });

        // Collect
        List<AtomicInteger> successCounters = IntStream.range(0, parameterSize)
                .mapToObj(i -> new AtomicInteger(0))
                .collect(Collectors.toList());

        AtomicInteger andCounter = new AtomicInteger(0);
        AtomicInteger orCounter = new AtomicInteger(0);

        for (BitSet result : results) {
            boolean and = true;
            boolean or = false;

            for (int parameterIndex = 0; parameterIndex < parameterSize; parameterIndex++) {
                boolean success = result.get(parameterIndex);
                if (success) {
                    successCounters.get(parameterIndex).incrementAndGet();
                }

                and &= success;
                or |= success;
            }

            if (and) {
                andCounter.incrementAndGet();
            }

            if (or) {
                orCounter.incrementAndGet();
            }
        }

        stopwatch.stop();

        output("     ... done");
        output("  -> Stopwatch stop : " + stopwatch.toMessage(TimeUnit.MILLISECONDS));

        output();

        // ========================================

        output("# Output");

        output("success:");

        int all = piecesList.size();
        for (int index = 0; index < successCounters.size(); index++) {
            AtomicInteger counter = successCounters.get(index);
            CoverParameter parameter = parameters.get(index);

            int success = counter.get();
            output(String.format("%.2f %% [%d/%d]: %s",
                    success * 100.0 / all, success, all, parameter.getUrl()
            ));
        }

        output(">>>");
        {
            int i = orCounter.get();
            output(String.format("OR  = %.2f %% [%d/%d]", i * 100.0 / all, i, all));
        }
        {
            int i = andCounter.get();
            output(String.format("AND = %.2f %% [%d/%d]", i * 100.0 / all, i, all));
        }

        // ========================================

        // baseファイル
        MyFile base = new MyFile(settings.getOutputBaseFilePath());
        base.mkdirs();

        try (BufferedWriter bw = base.newBufferedWriter()) {
            // Header
            bw.write("sequence,");
            bw.write(
                    parameters.stream().map(CoverParameter::getLabel).collect(Collectors.joining(","))
            );
            bw.newLine();

            // Body
            for (int index = 0; index < piecesList.size(); index++) {
                LongPieces pieces = piecesList.get(index);
                String key = pieces.blockStream().map(Piece::getName).collect(Collectors.joining());
                bw.write(key + ",");

                BitSet result = results.get(index);
                String body = IntStream.range(0, parameterSize)
                        .mapToObj(result::get)
                        .map(success -> success ? "O" : "X")
                        .collect(Collectors.joining(","));
                bw.write(body);

                bw.newLine();
            }

            bw.flush();
        } catch (IOException e) {
            throw new FinderExecuteException("Failed to output file", e);
        }
    }

    private Reachable createReachable(DropType dropType, int maxY) {
        MinoFactory minoFactory = new MinoFactory();
        MinoShifter minoShifter = new MinoShifter();

        switch (dropType) {
            case Harddrop: {
                return new HarddropReachable(minoFactory, minoShifter, maxY);
            }
            case Softdrop: {
                MinoRotation minoRotation = MinoRotation.create();
                return new LockedReachable(minoFactory, minoShifter, minoRotation, maxY);
            }
            case SoftdropTOnly: {
                MinoRotation minoRotation = MinoRotation.create();
                return new SoftdropTOnlyReachable(minoFactory, minoShifter, minoRotation, maxY);
            }
        }

        throw new IllegalStateException("Unknown drop type: " + dropType);
    }

    private void output() throws FinderExecuteException {
        output("");
    }

    public void output(String str) throws FinderExecuteException {
        try {
            logWriter.append(str).append(LINE_SEPARATOR);
        } catch (IOException e) {
            throw new FinderExecuteException(e);
        }

        if (settings.isOutputToConsole())
            System.out.println(str);
    }

    private void flush() throws FinderExecuteException {
        try {
            logWriter.flush();
        } catch (IOException e) {
            throw new FinderExecuteException(e);
        }
    }

    @Override
    public void close() throws FinderTerminateException {
        try {
            flush();
            logWriter.close();
        } catch (IOException | FinderExecuteException e) {
            throw new FinderTerminateException(e);
        }
    }
}

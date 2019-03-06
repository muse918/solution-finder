package searcher.spins.scaffold;

import common.buildup.BuildUp;
import common.datastore.BlockField;
import common.datastore.PieceCounter;
import common.parser.OperationTransform;
import core.action.reachable.LockedReachable;
import core.field.Field;
import core.field.FieldFactory;
import core.mino.Mino;
import core.mino.MinoFactory;
import core.mino.MinoShifter;
import core.mino.Piece;
import core.neighbor.SimpleOriginalPiece;
import core.srs.MinoRotation;
import core.srs.Rotate;
import org.junit.jupiter.api.Test;
import searcher.spins.TargetY;
import searcher.spins.pieces.Scaffolds;
import searcher.spins.pieces.SimpleOriginalPieceFactory;
import searcher.spins.results.AddLastResult;
import searcher.spins.results.AddLastsResult;
import searcher.spins.results.EmptyResult;
import searcher.spins.results.Result;
import searcher.spins.scaffold.results.ScaffoldResult;
import searcher.spins.scaffold.results.ScaffoldResultWithoutT;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ScaffoldRunnerTest {
    /*
    @Test
    void case1() {
        MinoFactory minoFactory = new MinoFactory();
        MinoShifter minoShifter = new MinoShifter();

        int fieldHeight = 7;
        Field initField = FieldFactory.createField("" +
                        "XXXX______" +
                        "XXXX______" +
                        "XXXX______" +
                        "XXXX______" +
                        "XXXX______"
                , fieldHeight);

        SimpleOriginalPieceFactory factory = new SimpleOriginalPieceFactory(minoFactory, minoShifter, fieldHeight);
        ScaffoldRunner runner = new ScaffoldRunner( Scaffolds.create(factory.createMinimalPieces(initField)));

        EmptyResult emptyResult = new EmptyResult(initField, new PieceCounter(Piece.valueList()), fieldHeight);

        List<SimpleOriginalPiece> operations = Arrays.asList(
                to(Piece.I, Rotate.Spawn, 5, 2, fieldHeight),
                to(Piece.O, Rotate.Spawn, 8, 2, fieldHeight)
        );
        Result result = AddLastsResult.create(emptyResult, operations);

        int targetY = 2;
        TargetY target = new TargetY(targetY);
        List<ScaffoldResultWithoutT> results = runner.buildToAllowFilledLine(result, target, operations)
                .collect(Collectors.toList());

        assertThat(results).hasSize(35);

        verify(results, initField, fieldHeight);
    }

    @Test
    void case2() {
        MinoFactory minoFactory = new MinoFactory();
        MinoShifter minoShifter = new MinoShifter();

        int fieldHeight = 7;
        Field initField = FieldFactory.createField("" +
                "XXXX______" +
                "XXXX______" +
                "XXXX______" +
                "XXX____XXX" +
                "XXXX______"
        );

        SimpleOriginalPieceFactory factory = new SimpleOriginalPieceFactory(minoFactory, minoShifter, fieldHeight);
        ScaffoldRunner runner = new ScaffoldRunner( Scaffolds.create(factory.createMinimalPieces(initField)));

        EmptyResult emptyResult = new EmptyResult(initField, new PieceCounter(
                Stream.concat(Piece.valueList().stream(), Stream.of(Piece.I))
        ), fieldHeight);
        Result result1 = AddLastResult.create(emptyResult, to(Piece.I, Rotate.Spawn, 4, 1, fieldHeight));

        List<SimpleOriginalPiece> operations = Arrays.asList(
                to(Piece.I, Rotate.Spawn, 5, 3, fieldHeight),
                to(Piece.O, Rotate.Spawn, 8, 3, fieldHeight)
        );
        Result result = AddLastsResult.create(result1, operations);

        int targetY = 3;
        TargetY target = new TargetY(targetY);
        List<ScaffoldResultWithoutT> results = runner.buildToAllowFilledLine(result, target, operations)
                .collect(Collectors.toList());

        assertThat(results).hasSize(35);

        verify(results, initField, fieldHeight);
    }

    @Test
    void case3() {
        MinoFactory minoFactory = new MinoFactory();
        MinoShifter minoShifter = new MinoShifter();

        int fieldHeight = 7;
        Field initField = FieldFactory.createField("" +
                "__________" +
                "__________" +
                "__________" +
                "__________" +
                "__________"
        );

        SimpleOriginalPieceFactory factory = new SimpleOriginalPieceFactory(minoFactory, minoShifter, fieldHeight);
        ScaffoldRunner runner = new ScaffoldRunner(Scaffolds.create(factory.createMinimalPieces(initField)));

        EmptyResult emptyResult = new EmptyResult(initField, new PieceCounter(
                Stream.concat(Piece.valueList().stream(), Stream.of(Piece.I))
        ), fieldHeight);

        List<SimpleOriginalPiece> operations = Arrays.asList(
                to(Piece.I, Rotate.Spawn, 1, 3, fieldHeight),
                to(Piece.S, Rotate.Spawn, 4, 2, fieldHeight),
                to(Piece.J, Rotate.Left, 6, 3, fieldHeight),
                to(Piece.T, Rotate.Reverse, 8, 3, fieldHeight)
        );
        Result result = AddLastsResult.create(emptyResult, operations);

        int targetY = 3;
        TargetY target = new TargetY(targetY);
        List<ScaffoldResultWithoutT> results = runner.build(result, target, operations)
                .collect(Collectors.toList());

        assertThat(results).hasSize(72);

        verify(results, initField, fieldHeight);
    }
*/
    private SimpleOriginalPiece to(Piece piece, Rotate rotate, int x, int y, int fieldHeight) {
        return new SimpleOriginalPiece(
                OperationTransform.toFullOperationWithKey(new Mino(piece, rotate), x, y, 0L), fieldHeight
        );
    }

    private void verify(List<? extends ScaffoldResult> results, Field initField, int fieldHeight) {
        LockedReachable reachable = new LockedReachable(new MinoFactory(), new MinoShifter(), new MinoRotation(), fieldHeight);

        // BlockFieldに重複がない
        TreeSet<BlockField> blockFields = new TreeSet<>(BlockField::compareTo);
        for (ScaffoldResult scaffoldResult : results) {
            Result result = scaffoldResult.getLastResult();
            BlockField blockField = result.parseToBlockField();
            blockFields.add(blockField);
        }
        assertThat(blockFields.size()).isEqualTo(results.size());

        // すべてのミノを置く手順が存在する
        for (ScaffoldResult scaffoldResult : results) {
            Result result = scaffoldResult.getLastResult();
            boolean exists = BuildUp.existsValidBuildPattern(initField, result.operationStream().collect(Collectors.toList()), fieldHeight, reachable);
            assertThat(exists).isTrue();
        }
    }
}
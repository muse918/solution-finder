package concurrent.checker.invoker;

import common.SyntaxException;
import common.datastore.Pair;
import common.datastore.action.Action;
import common.datastore.blocks.Blocks;
import common.pattern.BlocksGenerator;
import common.pattern.IBlocksGenerator;
import common.tree.AnalyzeTree;
import concurrent.LockedCandidateThreadLocal;
import concurrent.checker.CheckerNoHoldThreadLocal;
import concurrent.checker.invoker.no_hold.ConcurrentCheckerNoHoldInvoker;
import core.action.candidate.Candidate;
import core.action.candidate.LockedCandidate;
import core.field.Field;
import core.field.FieldFactory;
import core.mino.MinoFactory;
import core.mino.MinoShifter;
import core.srs.MinoRotation;
import lib.Randoms;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import searcher.checker.CheckerNoHold;
import searcher.common.validator.PerfectValidator;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ConcurrentCheckerNoHoldInvokerTest {
    private AnalyzeTree runTestCase(String marks, IBlocksGenerator blocksGenerator, int maxClearLine, int maxDepth) throws ExecutionException, InterruptedException {
        Field field = FieldFactory.createField(marks);
        List<Blocks> searchingPieces = blocksGenerator.blocksStream().collect(Collectors.toList());
        return runTestCase(field, searchingPieces, maxClearLine, maxDepth);
    }

    private AnalyzeTree runTestCase(Field field, List<Blocks> searchingPieces, int maxClearLine, int maxDepth) throws ExecutionException, InterruptedException {
        int core = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(core);
        CheckerNoHoldThreadLocal<Action> checkerThreadLocal = new CheckerNoHoldThreadLocal<>();
        LockedCandidateThreadLocal candidateThreadLocal = new LockedCandidateThreadLocal(maxClearLine);
        ConcurrentCheckerNoHoldInvoker invoker = new ConcurrentCheckerNoHoldInvoker(executorService, candidateThreadLocal, checkerThreadLocal);

        List<Pair<Blocks, Boolean>> resultPairs = invoker.search(field, searchingPieces, maxClearLine, maxDepth);

        // 結果を集計する
        AnalyzeTree tree = new AnalyzeTree();
        for (Pair<Blocks, Boolean> resultPair : resultPairs) {
            Blocks pieces = resultPair.getKey();
            Boolean result = resultPair.getValue();
            tree.set(result, pieces);
        }

        System.out.println(tree.show());
        executorService.shutdown();

        return tree;
    }

    @Test
    void testSearch1() throws Exception {
        IBlocksGenerator blocksGenerator = new BlocksGenerator("*p7");
        int maxClearLine = 8;
        int maxDepth = 7;

        String marks = "" +
                "XXXXXX____" +
                "XXXXXX____" +
                "XXXXXX____" +
                "XXXXXX____" +
                "XXXXXX____" +
                "XXXXXX____" +
                "XXXXXXXX__" +
                "XXXXXXXX__" +
                "";

        // 結果を集計する
        AnalyzeTree tree = runTestCase(marks, blocksGenerator, maxClearLine, maxDepth);

        // Source: myself 20170616
        assertThat(tree.getSuccessPercent()).isEqualTo(1719 / 5040.0);
    }

    @Test
    void testSearch2BT4_5() throws Exception {
        IBlocksGenerator blocksGenerator = new BlocksGenerator("*p7");
        int maxClearLine = 6;
        int maxDepth = 7;

        String marks = "" +
                "XX________" +
                "XX________" +
                "XXX______X" +
                "XXXXXXX__X" +
                "XXXXXX___X" +
                "XXXXXXX_XX" +
                "";

        // 結果を集計する
        AnalyzeTree tree = runTestCase(marks, blocksGenerator, maxClearLine, maxDepth);

        // Source: myself 20170616
        assertThat(tree.getSuccessPercent()).isEqualTo(2228 / 5040.0);
    }

    @Test
    void testSearch3() throws Exception {
        IBlocksGenerator blocksGenerator = new BlocksGenerator("*p7");
        int maxClearLine = 4;
        int maxDepth = 7;

        String marks = "" +
                "X_________" +
                "X___X_____" +
                "XXXXXXX___" +
                "XXXXXX____" +
                "";

        // 結果を集計する
        AnalyzeTree tree = runTestCase(marks, blocksGenerator, maxClearLine, maxDepth);

        // Source: myself 20170415
        assertThat(tree.getSuccessPercent()).isEqualTo(727 / 5040.0);
    }

    @Test
    void testSearch4() throws Exception {
        IBlocksGenerator blocksGenerator = new BlocksGenerator("*p4");
        int maxClearLine = 3;
        int maxDepth = 3;

        String marks = "" +
                "XXX____XXX" +
                "XXX_____XX" +
                "XXXX___XXX" +
                "";

        AnalyzeTree tree = runTestCase(marks, blocksGenerator, maxClearLine, maxDepth);

        // Source: myself 20170415
        assertThat(tree.getSuccessPercent()).isEqualTo(120 / 840.0);
    }

    @Test
    void testSearch5() throws Exception {
        IBlocksGenerator blocksGenerator = new BlocksGenerator("J, I, Z, *p4");
        int maxClearLine = 4;
        int maxDepth = 6;

        String marks = "" +
                "________XX" +
                "XX_____XXX" +
                "XXX_____XX" +
                "XXX______X" +
                "";

        AnalyzeTree tree = runTestCase(marks, blocksGenerator, maxClearLine, maxDepth);

        // Source: myself 20170415
        assertThat(tree.getSuccessPercent()).isEqualTo(180 / 840.0);
    }

    @Test
    void testSearch6() throws Exception {
        IBlocksGenerator blocksGenerator = new BlocksGenerator("*p5");
        int maxClearLine = 4;
        int maxDepth = 4;

        String marks = "" +
                "XXXXXX____" +
                "XXXXXX____" +
                "XXXXXX____" +
                "XXXXXX____" +
                "";

        AnalyzeTree tree = runTestCase(marks, blocksGenerator, maxClearLine, maxDepth);

        // Source: twitter by @???
        assertThat(tree.getSuccessPercent()).isEqualTo(516 / 2520.0);
    }

    @Test
    void testSearch7() throws Exception {
        IBlocksGenerator blocksGenerator = new BlocksGenerator("*p5");
        int maxClearLine = 4;
        int maxDepth = 4;

        String marks = "" +
                "XXXXX_____" +
                "XXXXXX____" +
                "XXXXXXX___" +
                "XXXXXX____" +
                "";

        AnalyzeTree tree = runTestCase(marks, blocksGenerator, maxClearLine, maxDepth);

        // Source: twitter by @???
        assertThat(tree.getSuccessPercent()).isEqualTo(396 / 2520.0);
    }

    @Test
    void testSearch8() throws Exception {
        IBlocksGenerator blocksGenerator = new BlocksGenerator("*p5");
        int maxClearLine = 4;
        int maxDepth = 4;

        String marks = "" +
                "XXXXXX____" +
                "XXXXX_____" +
                "XXXXXX____" +
                "XXXXXXX___" +
                "";

        AnalyzeTree tree = runTestCase(marks, blocksGenerator, maxClearLine, maxDepth);

        // Source: twitter by @???
        assertThat(tree.getSuccessPercent()).isEqualTo(609 / 2520.0);
    }

    @Test
    void testSearch9() throws Exception {
        IBlocksGenerator blocksGenerator = new BlocksGenerator("*p5");
        int maxClearLine = 4;
        int maxDepth = 4;

        String marks = "" +
                "XXXXXX____" +
                "XXXXX_____" +
                "XXXXXXX___" +
                "XXXXXX____" +
                "";

        AnalyzeTree tree = runTestCase(marks, blocksGenerator, maxClearLine, maxDepth);

        // Source: twitter by @???
        assertThat(tree.getSuccessPercent()).isEqualTo(324 / 2520.0);
    }

    @Test
    void testSearch10() throws Exception {
        IBlocksGenerator blocksGenerator = new BlocksGenerator("*p7");
        int maxClearLine = 4;
        int maxDepth = 6;

        // Field
        String marks = "" +
                "X________X" +
                "X________X" +
                "XX______XX" +
                "XXXXXX__XX" +
                "";

        AnalyzeTree tree = runTestCase(marks, blocksGenerator, maxClearLine, maxDepth);

        // Source: reply in twitter from @fullfool_14
        assertThat(tree.getSuccessPercent()).isEqualTo(1439 / 5040.0);
    }

    @Test
    void testCase11() throws Exception {
        // Invoker
        IBlocksGenerator blocksGenerator = new BlocksGenerator("*p4");
        int maxClearLine = 5;
        int maxDepth = 4;

        // Field
        String marks = "" +
                "____XXXXXX" +
                "____XXXXXX" +
                "___XXXXXXX" +
                "__XXXXXXXX" +
                "___XXXXXXX" +
                "";
        AnalyzeTree tree = runTestCase(marks, blocksGenerator, maxClearLine, maxDepth);

        // Source: reply in twitter from @fullfool_14
        assertThat(tree.getSuccessPercent()).isEqualTo(477 / 2520.0);
    }

    @Test
    void testCase12() throws Exception {
        // Invoker
        IBlocksGenerator blocksGenerator = new BlocksGenerator("*p7");
        int maxClearLine = 4;
        int maxDepth = 6;

        // Field
        String marks = "" +
                "X_________" +
                "X___X_____" +
                "XXXXXXX___" +
                "XXXXXX____" +
                "";
        AnalyzeTree tree = runTestCase(marks, blocksGenerator, maxClearLine, maxDepth);

        // Source: reply in twitter from @fullfool_14
        assertThat(tree.getSuccessPercent()).isEqualTo(727 / 5040.0);
    }

    @Test
    void testCase13() throws Exception {
        // Invoker
        IBlocksGenerator blocksGenerator = new BlocksGenerator("*p7");
        int maxClearLine = 4;
        int maxDepth = 6;

        // Field
        String marks = "" +
                "XXXX______" +
                "XXXX______" +
                "XXXX______" +
                "XXXX______" +
                "";
        AnalyzeTree tree = runTestCase(marks, blocksGenerator, maxClearLine, maxDepth);

        // Source: myself 20170415
        assertThat(tree.getSuccessPercent()).isEqualTo(1902 / 5040.0);
    }

    @Test
    void testCase14() throws Exception {
        // Invoker
        IBlocksGenerator blocksGenerator = new BlocksGenerator("*p5");
        int maxClearLine = 4;
        int maxDepth = 4;

        // Field
        String marks = "" +
                "XXXX______" +
                "XXXX_____X" +
                "XXXXXXXXXX" +
                "XXXX_____X" +
                "";
        AnalyzeTree tree = runTestCase(marks, blocksGenerator, maxClearLine, maxDepth);

        // Source: myself 20170415
        assertThat(tree.getSuccessPercent()).isEqualTo(309 / 2520.0);
    }

    @Test
    @Tag("long")
    void random() throws ExecutionException, InterruptedException, SyntaxException {
        Randoms randoms = new Randoms();

        MinoFactory minoFactory = new MinoFactory();
        MinoShifter minoShifter = new MinoShifter();
        MinoRotation minoRotation = new MinoRotation();

        PerfectValidator validator = new PerfectValidator();
        CheckerNoHold<Action> checker = new CheckerNoHold<>(minoFactory, validator);

        for (int count = 0; count < 40; count++) {
            int maxClearLine = randoms.nextInt(3, 6);
            int maxDepth = randoms.nextIntClosed(3, 5);
            Candidate<Action> candidate = new LockedCandidate(minoFactory, minoShifter, minoRotation, maxClearLine);
            Field field = randoms.field(maxClearLine, maxDepth);

            IBlocksGenerator blocksGenerator = createPiecesGenerator(maxDepth);
            List<Blocks> searchingPieces = blocksGenerator.blocksStream().collect(Collectors.toList());
            AnalyzeTree tree = runTestCase(field, searchingPieces, maxClearLine, maxDepth);

            for (Blocks pieces : searchingPieces) {
                boolean check = checker.check(field, pieces.getBlocks(), candidate, maxClearLine, maxDepth);
                assertThat(tree.isSucceed(pieces)).isEqualTo(check);
            }
        }
    }

    private IBlocksGenerator createPiecesGenerator(int maxDepth) throws SyntaxException {
        switch (maxDepth) {
            case 3:
                return new BlocksGenerator("*, *p3");
            case 4:
                return new BlocksGenerator("*, *p4");
            case 5:
                return new BlocksGenerator("*, *p5");
            case 6:
                return new BlocksGenerator("*, *p6");
        }
        throw new UnsupportedOperationException();
    }
}
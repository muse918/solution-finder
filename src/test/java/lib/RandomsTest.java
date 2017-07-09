package lib;

import core.field.Field;
import core.mino.Block;
import core.srs.Rotate;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class RandomsTest {
    @Test
    void nextInt() throws Exception {
        Randoms randoms = new Randoms();
        for (int count = 0; count < 10000; count++) {
            int next = randoms.nextInt(3, 19);
            assertThat(next)
                    .isGreaterThanOrEqualTo(3)
                    .isLessThan(19);
        }
    }

    @Test
    void nextIntClosed() throws Exception {
        Randoms randoms = new Randoms();
        for (int count = 0; count < 10000; count++) {
            int next = randoms.nextIntClosed(3, 19);
            assertThat(next)
                    .isGreaterThanOrEqualTo(3)
                    .isLessThanOrEqualTo(19);
        }
    }

    @Test
    void block() throws Exception {
        Randoms randoms = new Randoms();
        for (int count = 0; count < 10000; count++) {
            Block block = randoms.block();
            assertThat(block)
                    .isIn(Arrays.asList(Block.values()));
        }
    }

    @Test
    void blocks() throws Exception {
        Randoms randoms = new Randoms();
        for (int count = 0; count < 10000; count++) {
            int size = randoms.nextInt(3, 19);
            List<Block> blocks = randoms.blocks(size);
            assertThat(blocks)
                    .hasSize(size)
                    .doesNotContainNull();
        }
    }

    @Test
    void rotate() throws Exception {
        Randoms randoms = new Randoms();
        for (int count = 0; count < 10000; count++) {
            Rotate rotate = randoms.rotate();
            assertThat(rotate)
                    .isIn(Arrays.asList(Rotate.values()));
        }
    }

    @Test
    void choose() throws Exception {
        Randoms randoms = new Randoms();
        List<Integer> bag = IntStream.range(1, 100).boxed().collect(Collectors.toList());
        for (int count = 0; count < 10000; count++) {
            Integer choose = randoms.choose(bag);
            assertThat(choose).isIn(bag);
        }
    }

    @Test
    void combinations() throws Exception {
        Randoms randoms = new Randoms();
        List<Integer> bag = IntStream.range(1, 100).boxed().collect(Collectors.toList());
        for (int count = 0; count < 10000; count++) {
            int size = randoms.nextInt(1, 15);
            List<Integer> combinations = randoms.combinations(bag, size);
            assertThat(combinations).hasSize(size);
            assertThat(bag).containsAll(combinations);
            assertThat(new HashSet<>(combinations)).hasSize(size);
        }
    }

    @Test
    void field() throws Exception {
        Randoms randoms = new Randoms();
        for (int count = 0; count < 10000; count++) {
            int height = randoms.nextIntClosed(1, 12);
            int rand = randoms.nextInt(1, height * 10);
            int numOfEmpty = rand - rand % 4;
            Field randomField = randoms.field(height, numOfEmpty);
            assertThat(randomField)
                    .matches(field -> field.getNumOfAllBlocks() == 10 * height - numOfEmpty);
        }
    }
}
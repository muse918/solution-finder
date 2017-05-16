package _experimental.main;

import core.mino.Block;
import core.mino.MinoFactory;
import core.srs.Rotate;
import common.tetfu.*;
import common.tetfu.common.ColorConverter;
import common.tetfu.common.ColorType;
import common.tetfu.field.ArrayColoredField;

import java.util.Arrays;
import java.util.List;

import static core.mino.Block.*;

public class Experiment2 {
    public static void main(String[] args) {
//        List<Block> blocks = Arrays.asList(T, I, S);
//        ArrayList<Pieces> reverses = OrderLookup.reverse(blocks, blocks.size() + 1);
//        for (Pieces reverse : reverses) {
//            System.out.println(reverse);
//        }

        List<Block> orders = Arrays.asList(T, T, T);
        String quiz = Tetfu.encodeForQuiz(orders);

        List<TetfuElement> elements = Arrays.asList(
                new TetfuElement(ColorType.T, Rotate.Spawn, 5, 0, quiz),
                new TetfuElement(ColorType.T, Rotate.Right, 3, 1),
                new TetfuElement(ColorType.T, Rotate.Left, 7, 1)
        );

        MinoFactory factory = new MinoFactory();
        ColorConverter converter = new ColorConverter();
        Tetfu tetfu = new Tetfu(factory, converter);
        String encode = tetfu.encode(elements);
        System.out.println("v115@" + encode);
    }
}



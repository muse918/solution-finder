package _implements.parity_based_pack;

import core.field.Field;
import core.field.FieldFactory;
import core.mino.Piece;

import java.util.List;

public class ParityBasedPackMain {
    public static void main(String[] args) {
        Field field = FieldFactory.createField("" +
                "X_______XX" +
                "X_______XX" +
                "X_______XX" +
                "X_______XX"
        );
        int maxClearLine = 4;

        List<Piece> usingPieces = Piece.valueList();

        ParityBasedPackSearcher searcher = new ParityBasedPackSearcher(field, maxClearLine);
        long count = searcher.search(usingPieces).count();
        System.out.println(count);
    }
}

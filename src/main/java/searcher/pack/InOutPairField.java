package searcher.pack;

import core.column_field.ColumnField;
import core.column_field.ColumnSmallField;
import core.field.Field;

import java.util.Arrays;
import java.util.List;

public class InOutPairField {
    public static List<InOutPairField> createInOutPairFields(int width, int height, Field initField) {
        Field field = initField.freeze(height);
        InOutPairField pairField1 = parse(field, width, height);

        field.slideLeft(3);

        InOutPairField pairField2 = parse(field, width, height);

        field.slideLeft(3);
        assert width <= 7;
        for (int y = 0; y < height; y++)
            for (int x = 4; x < width + 3; x++)
                field.setBlock(x, y);

        InOutPairField pairField3 = parse(field, width, height);

        return Arrays.asList(pairField1, pairField2, pairField3);
    }

    private static InOutPairField parse(Field field, int width, int height) {
        ColumnSmallField innerField = new ColumnSmallField();
        ColumnSmallField outerField = new ColumnSmallField();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!field.isEmpty(x, y))
                    innerField.setBlock(x, y, height);
            }
            for (int x = width; x < width + 3; x++) {
                if (!field.isEmpty(x, y))
                    outerField.setBlock(x, y, height);
            }
        }
        return new InOutPairField(innerField, outerField);
    }

    private final ColumnField innerField;
    private final ColumnField outerField;

    public InOutPairField(ColumnField innerField, ColumnField outerField) {
        this.innerField = innerField;
        this.outerField = outerField;
    }

    public ColumnField getOuterField() {
        return outerField;
    }

    public ColumnField getInnerField() {
        return innerField;
    }
}
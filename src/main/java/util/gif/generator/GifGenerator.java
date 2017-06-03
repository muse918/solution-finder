package util.gif.generator;

import common.tetfu.common.ColorType;
import common.tetfu.field.ColoredField;
import core.mino.Block;
import core.srs.Rotate;

import java.awt.image.BufferedImage;
import java.util.List;

public interface GifGenerator {
    void reset();

    void updateField(ColoredField field);

    void updateMino(ColorType colorType, Rotate rotate, int xIndex, int yIndex);

    void updateNext(List<Block> blocks);

    void updateHold(Block block);

    BufferedImage fix();
}
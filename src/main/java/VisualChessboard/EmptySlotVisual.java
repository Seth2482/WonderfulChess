package VisualChessboard;

import java.util.ArrayList;
import java.util.List;

public class EmptySlotVisual extends ChessComponentVisual {

    @Override
    public String toString() {
        return "_";
    }

    @Override
    public void giveValueTo(ChessComponentVisual target) {

    }

    @Override
    public List<ChessboardPointVisual> canMoveTo() {
        return new ArrayList<>();
    }
}

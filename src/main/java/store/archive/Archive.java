package store.archive;

import com.google.gson.Gson;
import model.ChessColor;
import model.ChessComponent;
import view.Chessboard;

import java.util.ArrayList;
import java.util.Date;

public class Archive {
    private Date createdAt;
    private String name;
    private ArrayList<Step> steps;
    private ChessColor currentColor;

    public Archive() {
        //TODO:: 删掉这些代码 实现正确的初始化
        this.steps = new ArrayList<>();
        this.createdAt = new Date();
        this.name = "";
        this.currentColor = ChessColor.WHITE;
    }

    public void stepTrigger(Chessboard chessboard, ChessComponent chess1, ChessComponent chess2) {
        this.steps.add(
                new Step(chessboard, chess1, chess2)
        );
        currentColor = chess1.getChessColor().equals(ChessColor.WHITE) ? ChessColor.BLACK : ChessColor.WHITE;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public void save() {

    }

    public ChessComponent[][] withdraw() {
        if (this.steps.size() > 1) {
            steps.remove(steps.size() - 1);
        }
        Step lastStep = steps.get(steps.size() - 1);

        return lastStep.getChessComponents();
    }

}

package Archive;

import Archive.DataModel.ChessDataModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import Model.ChessColor;
import Model.ChessComponent;
import Archive.DataModel.ChessDataModelDeserializer;
import View.Chessboard;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Archive {
    private Date createdAt;
    private String name;
    private ArrayList<Step> steps;
    private ChessColor currentColor;
    private String path;

    public Archive() {
        createdAt = new Date();
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
        if (isFresh())
            return;

        File file = new File(path);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(this.toString());
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Failed to save archive.");
        }

    }

    public ChessComponent[][] withdraw() {
        if (this.steps.size() > 1) {
            steps.remove(steps.size() - 1);
        }
        Step lastStep = steps.get(steps.size() - 1);

        return lastStep.getChessComponents();
    }

    public void initialize() {
        this.steps = new ArrayList<>();
        this.createdAt = new Date();
        this.currentColor = ChessColor.WHITE;
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ChessDataModel.class, new ChessDataModelDeserializer());
        return gsonBuilder.create();
    }

    public static Archive getArchiveFromPath(String path) {
        try {
            //TODO:: Validate
            BufferedReader in = new BufferedReader(new FileReader(path));
            return getGson().fromJson(in, Archive.class);

        } catch (FileNotFoundException e) {
            // Return Null Archive
            return null;
        }
    }

    public boolean isEmpty() {
        return steps.size() < 1;
    }

    public Step lastStep() {
        if (isEmpty()) return null;
        return steps.get(steps.size() - 1);
    }

    public ChessComponent[][] getChessComponents() {
        if (!isEmpty()) {
            return lastStep().getChessComponents();
        } else {
            return null;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isFresh() {
        return this.path == null;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }
}

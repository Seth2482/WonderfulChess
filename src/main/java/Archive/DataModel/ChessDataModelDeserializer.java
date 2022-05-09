package Archive.DataModel;

import Archive.Exception.InvalidChessColorException;
import Archive.Exception.InvalidChessPositionException;
import Archive.Exception.InvalidChessTypeException;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessDataModelDeserializer implements JsonDeserializer<ChessDataModel> {
    @Override
    public ChessDataModel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();

        // 棋子并非黑白
        String chessColor = object.get("chessColor").getAsString();
        if (!chessColor.equals("BLACK") && !chessColor.equals("WHITE")) {
            throw new InvalidChessColorException("Unexpected chess color:" + object.get("chessColor"));
        }


        // 棋盘并非8x8

        int x = object.get("x").getAsInt(),
                y = object.get("y").getAsInt();
        if (x < 0 || x > 8 || y < 0 || y > 8) {
            throw new InvalidChessPositionException(String.format("Unexpected chess position: x:%d y:%d", x, y));
        }

        switch (object.get("chessType").getAsString()) {
            case "Bishop":
                return new Gson().fromJson(object, BishopDataModel.class);
            case "Empty":
                return new Gson().fromJson(object, EmptyDataModel.class);
            case "King":
                return new Gson().fromJson(object, KingDataModel.class);
            case "Knight":
                return new Gson().fromJson(object, KnightDataModel.class);
            case "Pawn":
                return new Gson().fromJson(object, PawnDataModel.class);
            case "Queen":
                return new Gson().fromJson(object, QueenDataModel.class);
            case "Rook":
                return new Gson().fromJson(object, RookDataModel.class);
            default:
                // 棋子并非六种之一
                throw new InvalidChessTypeException("Unexpected chess type:" + object.get("chessType"));
        }
    }
}

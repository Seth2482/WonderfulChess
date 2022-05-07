package Archive.DataModel;

import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessDataModelDeserializer implements JsonDeserializer<ChessDataModel> {
    @Override
    public ChessDataModel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();

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
                return new Gson().fromJson(object, ChessDataModel.class);
        }
    }
}

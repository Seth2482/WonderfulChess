package store.archive;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import store.archive.dataModels.ChessDataModel;
import store.archive.dataModels.ChessDataModelDeserializer;

public class ArchiveManager {
    public ArchiveManager() {

    }

    public Archive[] ArchiveList() {
        return null;
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ChessDataModel.class, new ChessDataModelDeserializer());
        return gsonBuilder.create();
    }
}

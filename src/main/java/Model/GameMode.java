package Model;

import com.google.gson.annotations.SerializedName;

public enum GameMode {
    @SerializedName("PVP")
    PVP,
    @SerializedName("PVEEasy")
    PVEEasy,
    @SerializedName("PVEHard")
    PVEHard
}

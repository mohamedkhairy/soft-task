package khairy.com.softtask.json;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SSIDvalue {

    @SerializedName("SSID")
    @Expose
    private String sSID;

    public String getSSID() {
        return sSID;
    }

    public void setSSID(String sSID) {
        this.sSID = sSID;
    }
}

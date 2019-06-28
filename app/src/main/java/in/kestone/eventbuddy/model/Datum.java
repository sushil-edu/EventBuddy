
package in.kestone.eventbuddy.model;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("status ")
    private String mStatus;

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}

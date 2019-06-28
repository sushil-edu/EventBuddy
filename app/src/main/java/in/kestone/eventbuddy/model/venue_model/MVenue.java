
package in.kestone.eventbuddy.model.venue_model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MVenue {

    @SerializedName("Message")
    private String mMessage;
    @SerializedName("StatusCode")
    private Long mStatusCode;
    @SerializedName("Data")
    private List<VenueDatum> mVenueData;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(Long statusCode) {
        mStatusCode = statusCode;
    }

    public List<VenueDatum> getVenueData() {
        return mVenueData;
    }

    public void setVenueData(List<VenueDatum> venueData) {
        mVenueData = venueData;
    }

}

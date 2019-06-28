
package in.kestone.eventbuddy.model.agenda_model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Track implements Serializable {

    @SerializedName("details")
    private List<Detail> mDetails;
    @SerializedName("trackname")
    private String mTrackName;

    public List<Detail> getDetails() {
        return mDetails;
    }

    public void setDetails(List<Detail> details) {
        mDetails = details;
    }

    public String getTrackName() {
        return mTrackName;
    }

    public void setTrackName(String trackName) {
        mTrackName = trackName;
    }



}

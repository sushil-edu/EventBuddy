
package in.kestone.eventbuddy.model.qanda_model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Track {

    @SerializedName("EBTM_ID")
    private Long mEBTMID;
    @SerializedName("session")
    private List<Session> mSession;
    @SerializedName("Track_Name")
    private String mTrackName;

    public Long getEBTMID() {
        return mEBTMID;
    }

    public void setEBTMID(Long eBTMID) {
        mEBTMID = eBTMID;
    }

    public List<Session> getSession() {
        return mSession;
    }

    public void setSession(List<Session> session) {
        mSession = session;
    }

    public String getTrackName() {
        return mTrackName;
    }

    public void setTrackName(String trackName) {
        mTrackName = trackName;
    }

}

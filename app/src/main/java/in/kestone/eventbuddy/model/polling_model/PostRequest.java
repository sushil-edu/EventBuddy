
package in.kestone.eventbuddy.model.polling_model;

import com.google.gson.annotations.SerializedName;

public class PostRequest {

    @SerializedName("Delegate_ID")
    private Long mDelegateID;
    @SerializedName("Event_ID")
    private Long mEventID;
    @SerializedName("Response")
    private String mResponse;
    @SerializedName("Session_ID")
    private Long mSessionID;
    @SerializedName("Track_ID")
    private Long mTrackID;

    public Long getDelegateID() {
        return mDelegateID;
    }

    public void setDelegateID(Long delegateID) {
        mDelegateID = delegateID;
    }

    public Long getEventID() {
        return mEventID;
    }

    public void setEventID(Long eventID) {
        mEventID = eventID;
    }

    public String getResponse() {
        return mResponse;
    }

    public void setResponse(String response) {
        mResponse = response;
    }

    public Long getSessionID() {
        return mSessionID;
    }

    public void setSessionID(Long sessionID) {
        mSessionID = sessionID;
    }

    public Long getTrackID() {
        return mTrackID;
    }

    public void setTrackID(Long trackID) {
        mTrackID = trackID;
    }

}

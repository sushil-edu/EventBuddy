
package in.kestone.eventbuddy.model.agenda_model;

import com.google.gson.annotations.SerializedName;

public class RatingRequest {

    @SerializedName("Comment")
    private String mComment;
    @SerializedName("Event_ID")
    private int mEventID;
    @SerializedName("InsertedOn")
    private String mInsertedOn;
    @SerializedName("Rating")
    private String mRating;
    @SerializedName("Session_ID")
    private int mSessionID;
    @SerializedName("Track_ID")
    private int mTrackID;
    @SerializedName("User_ID")
    private int mUserID;

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public int getEventID() {
        return mEventID;
    }

    public void setEventID(int eventID) {
        mEventID = eventID;
    }

    public String getInsertedOn() {
        return mInsertedOn;
    }

    public void setInsertedOn(String insertedOn) {
        mInsertedOn = insertedOn;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        mRating = rating;
    }

    public int getSessionID() {
        return mSessionID;
    }

    public void setSessionID(int sessionID) {
        mSessionID = sessionID;
    }

    public int getTrackID() {
        return mTrackID;
    }

    public void setTrackID(int trackID) {
        mTrackID = trackID;
    }

    public int getUserID() {
        return mUserID;
    }

    public void setUserID(int userID) {
        mUserID = userID;
    }

}

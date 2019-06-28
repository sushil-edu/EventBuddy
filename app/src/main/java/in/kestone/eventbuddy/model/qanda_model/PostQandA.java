
package in.kestone.eventbuddy.model.qanda_model;


import com.google.gson.annotations.SerializedName;

public class PostQandA {

    @SerializedName("Comment")
    private String mComment;
    @SerializedName("Event_ID")
    private Long mEventID;
    @SerializedName("ImageURL")
    private String mImageURL;
    @SerializedName("Inserted_On")
    private String mInsertedOn;
    @SerializedName("User_ID")
    private Long mUserID;

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public Long getEventID() {
        return mEventID;
    }

    public void setEventID(Long eventID) {
        mEventID = eventID;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public void setImageURL(String imageURL) {
        mImageURL = imageURL;
    }

    public String getInsertedOn() {
        return mInsertedOn;
    }

    public void setInsertedOn(String insertedOn) {
        mInsertedOn = insertedOn;
    }

    public Long getUserID() {
        return mUserID;
    }

    public void setUserID(Long userID) {
        mUserID = userID;
    }

}

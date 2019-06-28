
package in.kestone.eventbuddy.model.activity_stream_model;

import com.google.gson.annotations.SerializedName;

public class StreamDatum {

    @SerializedName("Comment")
    private String mComment;
    @SerializedName("Designation")
    private String mDesignation;
    @SerializedName("dt")
    private String mDt;
    @SerializedName("EBAS_ID")
    private Long mEBASID;
    @SerializedName("Event_ID")
    private Long mEventID;
    @SerializedName("First_Name")
    private String mFirstName;
    @SerializedName("Image")
    private String mImage;
    @SerializedName("ImageURL")
    private String mImageURL;
    @SerializedName("Last_Name")
    private String mLastName;
    @SerializedName("Organization")
    private String mOrganization;
    @SerializedName("Profile_Description")
    private String mProfileDescription;
    @SerializedName("User_ID")
    private Long mUserID;
    @SerializedName("UserType")
    private String mUserType;
    @SerializedName("Inserted_On")
    private String mInsertedOn;

    public String getInsertedOn() {
        return mInsertedOn;
    }

    public void setInsertedOn(String mInsertedOn) {
        this.mInsertedOn = mInsertedOn;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public String getDesignation() {
        return mDesignation;
    }

    public void setDesignation(String designation) {
        mDesignation = designation;
    }

    public String getDt() {
        return mDt;
    }

    public void setDt(String dt) {
        mDt = dt;
    }

    public Long getEBASID() {
        return mEBASID;
    }

    public void setEBASID(Long eBASID) {
        mEBASID = eBASID;
    }

    public Long getEventID() {
        return mEventID;
    }

    public void setEventID(Long eventID) {
        mEventID = eventID;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public void setImageURL(String imageURL) {
        mImageURL = imageURL;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getOrganization() {
        return mOrganization;
    }

    public void setOrganization(String organization) {
        mOrganization = organization;
    }

    public String getProfileDescription() {
        return mProfileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        mProfileDescription = profileDescription;
    }

    public Long getUserID() {
        return mUserID;
    }

    public void setUserID(Long userID) {
        mUserID = userID;
    }

    public String getUserType() {
        return mUserType;
    }

    public void setUserType(String userType) {
        mUserType = userType;
    }

}

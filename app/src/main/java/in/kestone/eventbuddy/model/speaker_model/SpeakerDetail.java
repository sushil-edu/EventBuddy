
package in.kestone.eventbuddy.model.speaker_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SpeakerDetail implements Serializable {

    @SerializedName("City_ID")
    private int mCityID;
    @SerializedName("Designation")
    private String mDesignation;
    @SerializedName("EBDM_ID")
    private int mEBDMID;
    @SerializedName("EmailID")
    private String mEmailID;
    @SerializedName("Event_ID")
    private int mEventID;
    @SerializedName("First_Name")
    private String mFirstName;
    @SerializedName("Image")
    private String mImage;
    @SerializedName("IsSpecial")
    private Boolean mIsSpecial;
    @SerializedName("Last_Name")
    private String mLastName;
    @SerializedName("Mobile")
    private String mMobile;
    @SerializedName("Organization")
    private String mOrganization;
    @SerializedName("Password")
    private String mPassword;
    @SerializedName("Profile_Description")
    private String mProfileDescription;
    @SerializedName("Rating")
    private String mRating;
    @SerializedName("User_ID")
    private int mUserID;
    @SerializedName("UserType")
    private String mUserType;

    public int getCityID() {
        return mCityID;
    }

    public void setCityID(int cityID) {
        mCityID = cityID;
    }

    public String getDesignation() {
        return mDesignation;
    }

    public void setDesignation(String designation) {
        mDesignation = designation;
    }

    public int getEBDMID() {
        return mEBDMID;
    }

    public void setEBDMID(int eBDMID) {
        mEBDMID = eBDMID;
    }

    public String getEmailID() {
        return mEmailID;
    }

    public void setEmailID(String emailID) {
        mEmailID = emailID;
    }

    public int getEventID() {
        return mEventID;
    }

    public void setEventID(int eventID) {
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

    public Boolean getIsSpecial() {
        return mIsSpecial;
    }

    public void setIsSpecial(Boolean isSpecial) {
        mIsSpecial = isSpecial;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    public String getOrganization() {
        return mOrganization;
    }

    public void setOrganization(String organization) {
        mOrganization = organization;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getProfileDescription() {
        return mProfileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        mProfileDescription = profileDescription;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        mRating = rating;
    }

    public int getUserID() {
        return mUserID;
    }

    public void setUserID(int userID) {
        mUserID = userID;
    }

    public String getUserType() {
        return mUserType;
    }

    public void setUserType(String userType) {
        mUserType = userType;
    }

}


package in.kestone.eventbuddy.model.user_model;

import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("City_ID")
    private String mCityID;
    @SerializedName("Designation")
    private String mDesignation;
    @SerializedName("EBDM_ID")
    private Long mEBDMID;
    @SerializedName("EmailID")
    private String mEmailID;
    @SerializedName("Event_ID")
    private Long mEventID;
    @SerializedName("First_Name")
    private String mFirstName;
    @SerializedName("ForgotOTP")
    private String mForgotOTP;
    @SerializedName("ForgotOTP_InsertedON")
    private String mForgotOTPInsertedON;
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
    private Object mProfileDescription;
    @SerializedName("Rating")
    private Object mRating;
    @SerializedName("User_ID")
    private int mUserID;
    @SerializedName("UserType")
    private String mUserType;

    public String getCityID() {
        return mCityID;
    }

    public void setCityID(String cityID) {
        mCityID = cityID;
    }

    public String getDesignation() {
        return mDesignation;
    }

    public void setDesignation(String designation) {
        mDesignation = designation;
    }

    public Long getEBDMID() {
        return mEBDMID;
    }

    public void setEBDMID(Long eBDMID) {
        mEBDMID = eBDMID;
    }

    public String getEmailID() {
        return mEmailID;
    }

    public void setEmailID(String emailID) {
        mEmailID = emailID;
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

    public String getForgotOTP() {
        return mForgotOTP;
    }

    public void setForgotOTP(String forgotOTP) {
        mForgotOTP = forgotOTP;
    }

    public String getForgotOTPInsertedON() {
        return mForgotOTPInsertedON;
    }

    public void setForgotOTPInsertedON(String forgotOTPInsertedON) {
        mForgotOTPInsertedON = forgotOTPInsertedON;
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

    public Object getProfileDescription() {
        return mProfileDescription;
    }

    public void setProfileDescription(Object profileDescription) {
        mProfileDescription = profileDescription;
    }

    public Object getRating() {
        return mRating;
    }

    public void setRating(Object rating) {
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


package in.kestone.eventbuddy.model;

import com.google.gson.annotations.SerializedName;

public class EventName {

    @SerializedName("App_Banner_Image")
    private String mAppBannerImage;
    @SerializedName("Client_ID")
    private String mClientID;
    @SerializedName("Event_ID")
    private Long mEventID;
    @SerializedName("EventName")
    private String mEventName;
    @SerializedName("Splash_Screen")
    private String mSplashScreen;
    @SerializedName("Masthead_Image")
    private String mMastheadImage;
    @SerializedName("Background_Image")
    private String mBackgroundImage;

    public String getAppBannerImage() {
        return mAppBannerImage;
    }

    public void setAppBannerImage(String appBannerImage) {
        mAppBannerImage = appBannerImage;
    }

    public String getClientID() {
        return mClientID;
    }

    public void setClientID(String clientID) {
        mClientID = clientID;
    }

    public Long getEventID() {
        return mEventID;
    }

    public void setEventID(Long eventID) {
        mEventID = eventID;
    }

    public String getEventName() {
        return mEventName;
    }

    public void setEventName(String eventName) {
        mEventName = eventName;
    }

    public String getSplashScreen() {
        return mSplashScreen;
    }

    public void setSplashScreen(String mSplashScreen) {
        this.mSplashScreen = mSplashScreen;
    }

    public String getMastheadImage() {
        return mMastheadImage;
    }

    public void setMastheadImage(String mMastheadImage) {
        this.mMastheadImage = mMastheadImage;
    }

    public String getBackgroundImage() {
        return mBackgroundImage;
    }

    public void setBackgroundImage(String mBackgroundImage) {
        this.mBackgroundImage = mBackgroundImage;
    }
}


package in.kestone.eventbuddy.model.app_config_model;

import com.google.gson.annotations.SerializedName;

public class OTP {

    @SerializedName("ButtonLabel")
    private String mButtonLabel;
    @SerializedName("ErrorMessage")
    private String mErrorMessage;
    @SerializedName("ErrorHeader")
    private String mErrorHeader;
    @SerializedName("Visibility")
    private String mVisibility;
    @SerializedName("WelcomeText")
    private String mWelcomeText;

    public String getButtonLabel() {
        return mButtonLabel;
    }

    public void setButtonLabel(String buttonLabel) {
        mButtonLabel = buttonLabel;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        mErrorMessage = errorMessage;
    }

    public String getVisibility() {
        return mVisibility;
    }

    public void setVisibility(String visibility) {
        mVisibility = visibility;
    }

    public String getWelcomeText() {
        return mWelcomeText;
    }

    public void setWelcomeText(String welcomeText) {
        mWelcomeText = welcomeText;
    }

    public String getmErrorHeader() {
        return mErrorHeader;
    }

    public void setmErrorHeader(String mErrorHeader) {
        this.mErrorHeader = mErrorHeader;
    }


}

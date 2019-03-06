
package in.kestone.eventbuddy.model.app_config_model;

import com.google.gson.annotations.SerializedName;

public class Priority {

    @SerializedName("ErrorMessage")
    private String mErrorMessage;
    @SerializedName("ErrorHeader")
    private String mErrorHeader;
    @SerializedName("Label")
    private String mLabel;
    @SerializedName("Type")
    private String mType;
    @SerializedName("Visibility")
    private String mVisibility;
    @SerializedName("WelcomeText")
    private String mWelcomeText;

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        mErrorMessage = errorMessage;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
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

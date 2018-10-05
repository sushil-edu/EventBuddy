
package in.kestone.eventbuddy.model.app_config;

import com.google.gson.annotations.SerializedName;

public class GeoTag {

    @SerializedName("ButtonLabel")
    private String mButtonLabel;
    @SerializedName("ErrorMessage")
    private String mErrorMessage;
    @SerializedName("Hint")
    private String mHint;
    @SerializedName("Label")
    private String mLabel;
    @SerializedName("Type")
    private String mType;
    @SerializedName("Visibility")
    private String mVisibility;
    @SerializedName("WelcomeText")
    private String mWelcomeText;

    @SerializedName("ActivationDateFrom")
    private String mActivationDateFrom;
    @SerializedName("ActivationTimeFrom")
    private String mActivationTimeFrom;

    @SerializedName("ActivationDateTo")
    private String mActivationDateTo;
    @SerializedName("ActivationTimeTo")
    private String mActivationTimeTo;

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

    public String getHint() {
        return mHint;
    }

    public void setHint(String hint) {
        mHint = hint;
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

    public String getActivationDateFrom() {
        return mActivationDateFrom;
    }

    public void setActivationDateFrom(String mActivationDateFrom) {
        this.mActivationDateFrom = mActivationDateFrom;
    }

    public String getActivationTimeFrom() {
        return mActivationTimeFrom;
    }

    public void setActivationTimeFrom(String mActivationTimeFrom) {
        this.mActivationTimeFrom = mActivationTimeFrom;
    }

    public String getActivationDateTo() {
        return mActivationDateTo;
    }

    public void setActivationDateTo(String mActivationDateTo) {
        this.mActivationDateTo = mActivationDateTo;
    }

    public String getActivationTimeTo() {
        return mActivationTimeTo;
    }

    public void setActivationTimeTo(String mActivationTimeTo) {
        this.mActivationTimeTo = mActivationTimeTo;
    }
}

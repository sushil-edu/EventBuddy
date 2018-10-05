
package in.kestone.eventbuddy.model.app_config;

import com.google.gson.annotations.SerializedName;


public class ForgotButton {

    @SerializedName("ErrorMessage")
    private Object mErrorMessage;
    @SerializedName("Hint")
    private String mHint;
    @SerializedName("Label")
    private String mLabel;
    @SerializedName("Type")
    private Object mType;
    @SerializedName("Visibility")
    private String mVisibility;

    public Object getErrorMessage() {
        return mErrorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
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

    public Object getType() {
        return mType;
    }

    public void setType(Object type) {
        mType = type;
    }

    public String getVisibility() {
        return mVisibility;
    }

    public void setVisibility(String visibility) {
        mVisibility = visibility;
    }

}

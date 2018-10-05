
package in.kestone.eventbuddy.model.app_config;

import com.google.gson.annotations.SerializedName;

public class UserName {

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

}

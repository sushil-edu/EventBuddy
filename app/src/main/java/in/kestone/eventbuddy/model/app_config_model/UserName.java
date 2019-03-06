
package in.kestone.eventbuddy.model.app_config_model;

import com.google.gson.annotations.SerializedName;

public class UserName {

    @SerializedName("ErrorHeader")
    private String mErrorHeader;
    @SerializedName("ErrorMessage")
    private String mErrorMessage;
    @SerializedName("Hint")
    private String mHint;
    @SerializedName("Type")
    private String mType;
    @SerializedName("Visibility")
    private String mVisibility;

    public String getErrorHeader() {
        return mErrorHeader;
    }

    public void setErrorHeader(String errorHeader) {
        mErrorHeader = errorHeader;
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

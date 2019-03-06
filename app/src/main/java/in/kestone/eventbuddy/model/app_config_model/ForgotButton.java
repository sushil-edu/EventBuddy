
package in.kestone.eventbuddy.model.app_config_model;

import com.google.gson.annotations.SerializedName;


public class ForgotButton {

    @SerializedName("Label")
    private String mLabel;
    @SerializedName("Visibility")
    private String mVisibility;

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public String getVisibility() {
        return mVisibility;
    }

    public void setVisibility(String visibility) {
        mVisibility = visibility;
    }

}

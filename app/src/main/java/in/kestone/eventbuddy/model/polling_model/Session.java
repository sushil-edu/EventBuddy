
package in.kestone.eventbuddy.model.polling_model;

import com.google.gson.annotations.SerializedName;

public class Session {

    @SerializedName("EBTS_ID")
    private Long mEBTSID;
    @SerializedName("Session_Short_Title_label")
    private String mSessionShortTitleLabel;

    public Long getEBTSID() {
        return mEBTSID;
    }

    public void setEBTSID(Long eBTSID) {
        mEBTSID = eBTSID;
    }

    public String getSessionShortTitleLabel() {
        return mSessionShortTitleLabel;
    }

    public void setSessionShortTitleLabel(String sessionShortTitleLabel) {
        mSessionShortTitleLabel = sessionShortTitleLabel;
    }

}

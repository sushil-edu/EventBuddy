
package in.kestone.eventbuddy.model.qanda_model;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Session {

    @SerializedName("EBTS_ID")
    private Long mEBTSID;
    @SerializedName("Session_Long_Title_label")
    private String mSessionLongTitleLabel;
    @SerializedName("speaker")
    private List<Speaker> mSpeaker;

    public Long getEBTSID() {
        return mEBTSID;
    }

    public void setEBTSID(Long eBTSID) {
        mEBTSID = eBTSID;
    }

    public String getSessionLongTitleLabel() {
        return mSessionLongTitleLabel;
    }

    public void setSessionLongTitleLabel(String sessionLongTitleLabel) {
        mSessionLongTitleLabel = sessionLongTitleLabel;
    }

    public List<Speaker> getSpeaker() {
        return mSpeaker;
    }

    public void setSpeaker(List<Speaker> speaker) {
        mSpeaker = speaker;
    }

}

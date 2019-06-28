
package in.kestone.eventbuddy.model.knowledgebase_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {

    @SerializedName("EBTM_ID")
    private Long mEBTMID;
    @SerializedName("Knowledgebase")
    private List<Knowledgebase> mKnowledgebase;
    @SerializedName("Track_Name")
    private String mTrackName;

    public Long getEBTMID() {
        return mEBTMID;
    }

    public void setEBTMID(Long eBTMID) {
        mEBTMID = eBTMID;
    }

    public List<Knowledgebase> getKnowledgebase() {
        return mKnowledgebase;
    }

    public void setKnowledgebase(List<Knowledgebase> knowledgebase) {
        mKnowledgebase = knowledgebase;
    }

    public String getTrackName() {
        return mTrackName;
    }

    public void setTrackName(String trackName) {
        mTrackName = trackName;
    }

}

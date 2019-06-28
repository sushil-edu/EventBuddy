
package in.kestone.eventbuddy.model.agenda_model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Agenda implements Serializable {

    @SerializedName("displayLabel")
    private String mDisplayLabel;
    @SerializedName("id")
    private int mID;
    @SerializedName("track")
    private List<Track> mTrack;

    @SerializedName("details")
    private List<Detail> mCommonAgendaModel;

    public String getDisplayLabel() {
        return mDisplayLabel;
    }

    public void setDisplayLabel(String displayLabel) {
        mDisplayLabel = displayLabel;
    }

    public int getID() {
        return mID;
    }

    public void setID(int iD) {
        mID = iD;
    }

    public List<Track> getTrack() {
        return mTrack;
    }

    public void setTrack(List<Track> track) {
        mTrack = track;
    }


    public List<Detail> getCommonAgendaModel() {
        return mCommonAgendaModel;
    }

    public void setCommonAgendaModel(List<Detail> mCommonAgendaModel) {
        this.mCommonAgendaModel = mCommonAgendaModel;
    }
}

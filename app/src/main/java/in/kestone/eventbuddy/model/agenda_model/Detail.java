
package in.kestone.eventbuddy.model.agenda_model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

import in.kestone.eventbuddy.model.speaker_model.SpeakerDetail;

public class Detail implements Serializable {

    @SerializedName("date")
    private String mDate;
    @SerializedName("id")
    private int mId;
    @SerializedName("location")
    private String mLocation;
    @SerializedName("long_title")
    private String mLongTitle;
    @SerializedName("myAgendaTitle")
    private String mMyAgendaTitle;
    @SerializedName("myAgendaVisibility")
    private String mMyAgendaVisibility;
    @SerializedName("rating")
    private String mRating;
    @SerializedName("rating_label")
    private String mRatingLabel;
    @SerializedName("rating_placeholder")
    private String mRatingPlaceholder;
    @SerializedName("session_brief")
    private String mSessionBrief;
    @SerializedName("short_title")
    private String mShortTitle;
    @SerializedName("speaker")
    private List<SpeakerDetail> mSpeaker;
    @SerializedName("time")
    private String mTime;
    @SerializedName("track")
    private String mTrack;
    @SerializedName("ismyagenda")
    private String mIsMyagenda;
    @SerializedName( "rateValue" )
    private String rateValue;

    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }

    public String getIsMyagenda() {
        return mIsMyagenda;
    }

    public void setIsMyagenda(String mIsMyagenda) {
        this.mIsMyagenda = mIsMyagenda;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getLongTitle() {
        return mLongTitle;
    }

    public void setLongTitle(String longTitle) {
        mLongTitle = longTitle;
    }

    public String getMyAgendaTitle() {
        return mMyAgendaTitle;
    }

    public void setMyAgendaTitle(String myAgendaTitle) {
        mMyAgendaTitle = myAgendaTitle;
    }

    public String getMyAgendaVisibility() {
        return mMyAgendaVisibility;
    }

    public void setMyAgendaVisibility(String myAgendaVisibility) {
        mMyAgendaVisibility = myAgendaVisibility;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        mRating = rating;
    }

    public String getRatingLabel() {
        return mRatingLabel;
    }

    public void setRatingLabel(String ratingLabel) {
        mRatingLabel = ratingLabel;
    }

    public String getRatingPlaceholder() {
        return mRatingPlaceholder;
    }

    public void setRatingPlaceholder(String ratingPlaceholder) {
        mRatingPlaceholder = ratingPlaceholder;
    }

    public String getSessionBrief() {
        return mSessionBrief;
    }

    public void setSessionBrief(String sessionBrief) {
        mSessionBrief = sessionBrief;
    }

    public String getShortTitle() {
        return mShortTitle;
    }

    public void setShortTitle(String shortTitle) {
        mShortTitle = shortTitle;
    }

    public List<SpeakerDetail> getSpeaker() {
        return mSpeaker;
    }

    public void setSpeaker(List<SpeakerDetail> speaker) {
        mSpeaker = speaker;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getTrack() {
        return mTrack;
    }

    public void setTrack(String track) {
        mTrack = track;
    }

}


package in.kestone.eventbuddy.model.agenda_model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import in.kestone.eventbuddy.model.speaker_model.SpeakerDetail;

public class CommonAgendaMdl {

    @SerializedName("date")
    private String mDate;
    @SerializedName("id")
    private Long mId;
    @SerializedName("ismyagenda")
    private String mIsmyagenda;
    @SerializedName("location")
    private String mLocation;
    @SerializedName("long_title")
    private String mLongTitle;
    @SerializedName("myAgendaTitle")
    private Object mMyAgendaTitle;
    @SerializedName("myAgendaVisibility")
    private String mMyAgendaVisibility;
    @SerializedName("rateValue")
    private String mRateValue;
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

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getIsmyagenda() {
        return mIsmyagenda;
    }

    public void setIsmyagenda(String ismyagenda) {
        mIsmyagenda = ismyagenda;
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

    public Object getMyAgendaTitle() {
        return mMyAgendaTitle;
    }

    public void setMyAgendaTitle(Object myAgendaTitle) {
        mMyAgendaTitle = myAgendaTitle;
    }

    public String getMyAgendaVisibility() {
        return mMyAgendaVisibility;
    }

    public void setMyAgendaVisibility(String myAgendaVisibility) {
        mMyAgendaVisibility = myAgendaVisibility;
    }

    public String getRateValue() {
        return mRateValue;
    }

    public void setRateValue(String rateValue) {
        mRateValue = rateValue;
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

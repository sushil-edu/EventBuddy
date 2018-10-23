package in.kestone.eventbuddy.model.agenda_holder;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Detail {

    @SerializedName("id")
    private int mId;
    @SerializedName("date")
    private String mDate;
    @SerializedName("location")
    private String mLocation;
    @SerializedName("long_title")
    private String mLongTitle;
    @SerializedName("rating_label")
    private String mRatingLabel;
    @SerializedName("rating")
    private Float mRating;
    @SerializedName("session_brief")
    private String mSessionBrief;
    @SerializedName("short_title")
    private String mShortTitle;
    @SerializedName("Speaker")
    private List<Speaker> mSpeaker;
    @SerializedName("time")
    private String mTime;
    @SerializedName("track")
    private String mTrack;

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
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

    public String getRatingLabel() {
        return mRatingLabel;
    }

    public void setRatingLabel(String ratingLabel) {
        mRatingLabel = ratingLabel;
    }

    public Float getRating() {
        return mRating;
    }

    public void setRating(Float rating) {
        mRating = rating;
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

    public List<Speaker> getSpeaker() {
        return mSpeaker;
    }

    public void setSpeaker(List<Speaker> speaker) {
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


package in.kestone.eventbuddy.model.agenda;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Detail {

    @SerializedName("date")
    private String mDate;
    @SerializedName("location")
    private String mLocation;
    @SerializedName("long_title")
    private String mLongTitle;
    @SerializedName("rating_label")
    private String mRatingLabel;
    @SerializedName("rating_required")
    private Long mRatingRequired;
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

    public Long getRatingRequired() {
        return mRatingRequired;
    }

    public void setRatingRequired(Long ratingRequired) {
        mRatingRequired = ratingRequired;
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

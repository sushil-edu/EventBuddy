package in.kestone.eventbuddy.model.qanda_model;


import com.google.gson.annotations.SerializedName;

public class FeedbackModel {

    @SerializedName("EBFMC_ID")
    private Long mEBFMCID;
    @SerializedName("End_Date")
    private String mEndDate;
    @SerializedName("End_Time")
    private String mEndTime;
    @SerializedName("Event_ID")
    private Long mEventID;
    @SerializedName("Feedback_Weblink")
    private String mFeedbackWeblink;
    @SerializedName("Header_Text")
    private String mHeaderText;
    @SerializedName("Start_date")
    private String mStartDate;
    @SerializedName("Start_Time")
    private String mStartTime;
    @SerializedName("Sub_Header_Text")
    private String mSubHeaderText;

    public Long getEBFMCID() {
        return mEBFMCID;
    }

    public void setEBFMCID(Long eBFMCID) {
        mEBFMCID = eBFMCID;
    }

    public String getEndDate() {
        return mEndDate;
    }

    public void setEndDate(String endDate) {
        mEndDate = endDate;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        mEndTime = endTime;
    }

    public Long getEventID() {
        return mEventID;
    }

    public void setEventID(Long eventID) {
        mEventID = eventID;
    }

    public String getFeedbackWeblink() {
        return mFeedbackWeblink;
    }

    public void setFeedbackWeblink(String feedbackWeblink) {
        mFeedbackWeblink = feedbackWeblink;
    }

    public String getHeaderText() {
        return mHeaderText;
    }

    public void setHeaderText(String headerText) {
        mHeaderText = headerText;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String startDate) {
        mStartDate = startDate;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

    public String getSubHeaderText() {
        return mSubHeaderText;
    }

    public void setSubHeaderText(String subHeaderText) {
        mSubHeaderText = subHeaderText;
    }

}

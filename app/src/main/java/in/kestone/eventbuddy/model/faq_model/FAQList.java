
package in.kestone.eventbuddy.model.faq_model;

import com.google.gson.annotations.SerializedName;

public class FAQList {

    @SerializedName("Answer_Text")
    private String mAnswerText;
    @SerializedName("EBFI_ID")
    private Long mEBFIID;
    @SerializedName("Event_ID")
    private Long mEventID;
    @SerializedName("Question_text")
    private String mQuestionText;

    public String getAnswerText() {
        return mAnswerText;
    }

    public void setAnswerText(String answerText) {
        mAnswerText = answerText;
    }

    public Long getEBFIID() {
        return mEBFIID;
    }

    public void setEBFIID(Long eBFIID) {
        mEBFIID = eBFIID;
    }

    public Long getEventID() {
        return mEventID;
    }

    public void setEventID(Long eventID) {
        mEventID = eventID;
    }

    public String getQuestionText() {
        return mQuestionText;
    }

    public void setQuestionText(String questionText) {
        mQuestionText = questionText;
    }

}

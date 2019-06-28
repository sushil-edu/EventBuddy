
package in.kestone.eventbuddy.model.qanda_model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class QandA {

    @SerializedName("Data")
    private List<Track> mData;
    @SerializedName("Message")
    private String mMessage;
    @SerializedName("StatusCode")
    private Long mStatusCode;
    @SerializedName(value="feedback", alternate={"data"})
    private List<FeedbackModel> mFeedback;

    public List<Track> getData() {
        return mData;
    }

    public void setData(List<Track> data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(Long statusCode) {
        mStatusCode = statusCode;
    }

    public List<FeedbackModel> getFeedback() {
        return mFeedback;
    }

    public void setFeedback(List<FeedbackModel> mFeedback) {
        this.mFeedback = mFeedback;
    }
}

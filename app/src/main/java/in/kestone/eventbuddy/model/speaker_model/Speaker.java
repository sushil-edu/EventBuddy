
package in.kestone.eventbuddy.model.speaker_model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Speaker {

    @SerializedName("Data")
    private List<SpeakerDetail> mData;
    @SerializedName("Message")
    private String mMessage;
    @SerializedName("StatusCode")
    private Long mStatusCode;

    public List<SpeakerDetail> getData() {
        return mData;
    }

    public void setData(List<SpeakerDetail> data) {
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

}

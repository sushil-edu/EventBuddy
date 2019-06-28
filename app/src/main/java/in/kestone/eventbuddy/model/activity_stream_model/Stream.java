
package in.kestone.eventbuddy.model.activity_stream_model;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Stream {

    @SerializedName("Message")
    private String mMessage;
    @SerializedName("StatusCode")
    private Long mStatusCode;
    @SerializedName("Data")
    private List<StreamDatum> mStreamData;

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

    public List<StreamDatum> getStreamData() {
        return mStreamData;
    }

    public void setStreamData(List<StreamDatum> streamData) {
        mStreamData = streamData;
    }

}

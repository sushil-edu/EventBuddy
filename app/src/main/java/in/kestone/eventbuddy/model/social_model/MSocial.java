
package in.kestone.eventbuddy.model.social_model;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class MSocial {

    @SerializedName("Data")
    private List<MDatum> mMData;
    @SerializedName("Message")
    private String mMessage;
    @SerializedName("StatusCode")
    private Long mStatusCode;

    public List<MDatum> getMData() {
        return mMData;
    }

    public void setMData(List<MDatum> mData) {
        mMData = mData;
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

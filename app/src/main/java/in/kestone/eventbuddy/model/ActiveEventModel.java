
package in.kestone.eventbuddy.model;


import com.google.gson.annotations.SerializedName;

public class ActiveEventModel {

    @SerializedName("Data")
    private Data mData;
    @SerializedName("Message")
    private String mMessage;
    @SerializedName("StatusCode")
    private Long mStatusCode;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
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

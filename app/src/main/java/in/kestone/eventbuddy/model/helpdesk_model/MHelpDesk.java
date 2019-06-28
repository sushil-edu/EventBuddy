
package in.kestone.eventbuddy.model.helpdesk_model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MHelpDesk {

    @SerializedName("Data")
    private List<Datum> mData;
    @SerializedName("Message")
    private String mMessage;
    @SerializedName("StatusCode")
    private Long mStatusCode;

    public List<Datum> getData() {
        return mData;
    }

    public void setData(List<Datum> data) {
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

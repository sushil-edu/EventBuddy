
package in.kestone.eventbuddy.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Helpdesk {

    @SerializedName("Data")
    private List<HDetail> mHelpDesk;
    @SerializedName("Message")
    private String mMessage;
    @SerializedName("statusCode")
    private String mStatusCode;

    public List<HDetail> getHelpDesk() {
        return mHelpDesk;
    }

    public void setHelpDesk(List<HDetail> helpDesk) {
        mHelpDesk = helpDesk;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(String statusCode) {
        mStatusCode = statusCode;
    }

}

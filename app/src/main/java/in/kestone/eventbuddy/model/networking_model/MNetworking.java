
package in.kestone.eventbuddy.model.networking_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MNetworking implements Serializable {

    @SerializedName("Message")
    private String mMessage;
    @SerializedName("Data")
    private List<NetworkingList> mNetworkingList;
    @SerializedName("StatusCode")
    private Long mStatusCode;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<NetworkingList> getNetworkingList() {
        return mNetworkingList;
    }

    public void setNetworkingList(List<NetworkingList> networkingList) {
        mNetworkingList = networkingList;
    }

    public Long getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(Long statusCode) {
        mStatusCode = statusCode;
    }

}

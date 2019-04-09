
package in.kestone.eventbuddy.model.app_config_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AppConf implements Serializable {

    @SerializedName("AppVersion")
    private float mAppVersion;
    @SerializedName("Data")
    Event mEvent;
    @SerializedName("StatusCode")
    private int mStatusCode;

    @SerializedName("Message")
    private String mMessage;

    public float getAppVersion() {
        return mAppVersion;
    }

    public void setAppVersion(float appVersion) {
        mAppVersion = appVersion;
    }

    public Event getEvent() {
        return mEvent;
    }

    public void setEvent(Event event) {
        mEvent = event;
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(int statusCode) {
        mStatusCode = statusCode;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}


package in.kestone.eventbuddy.model.app_config_model;

import com.google.gson.annotations.SerializedName;

public class AppConf {

    @SerializedName("AppVersion")
    private float mAppVersion;
    @SerializedName("Event")
    Event mEvent;
    @SerializedName("StatusCode")
    private float mStatusCode;

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

    public float getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(float statusCode) {
        mStatusCode = statusCode;
    }

}

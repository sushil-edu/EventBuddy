
package in.kestone.eventbuddy.model.app_config;

import com.google.gson.annotations.SerializedName;

public class AppConf {

    @SerializedName("AppVersion")
    private Long mAppVersion;
    @SerializedName("Event")
    Event mEvent;
    @SerializedName("StatusCode")
    private Long mStatusCode;

    public Long getAppVersion() {
        return mAppVersion;
    }

    public void setAppVersion(Long appVersion) {
        mAppVersion = appVersion;
    }

    public Event getEvent() {
        return mEvent;
    }

    public void setEvent(Event event) {
        mEvent = event;
    }

    public Long getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(Long statusCode) {
        mStatusCode = statusCode;
    }

}

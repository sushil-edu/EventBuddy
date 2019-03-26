
package in.kestone.eventbuddy.model.app_config_model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("EventName")
    private String mEventName;
    @SerializedName("GeoTag")
    private GeoTag mGeoTag;
    @SerializedName("Login")
    private Login mLogin;
    @SerializedName("Menu")
    private List<Menu> mMenu;
    @SerializedName("OTP")
    private OTP mOTP;
    @SerializedName("Priority")
    private Priority mPriority;
    @SerializedName("Networking")
    private ConfNetworking nNetworking;

    public String getEventName() {
        return mEventName;
    }

    public void setEventName(String eventName) {
        mEventName = eventName;
    }

    public GeoTag getGeoTag() {
        return mGeoTag;
    }

    public void setGeoTag(GeoTag geoTag) {
        mGeoTag = geoTag;
    }

    public Login getLogin() {
        return mLogin;
    }

    public void setLogin(Login login) {
        mLogin = login;
    }

    public List<Menu> getMenu() {
        return mMenu;
    }

    public void setMenu(List<Menu> menu) {
        mMenu = menu;
    }

    public OTP getOTP() {
        return mOTP;
    }

    public void setOTP(OTP oTP) {
        mOTP = oTP;
    }

    public Priority getPriority() {
        return mPriority;
    }

    public void setPriority(Priority priority) {
        mPriority = priority;
    }

    public ConfNetworking getnNetworking() {
        return nNetworking;
    }

    public void setnNetworking(ConfNetworking nNetworking) {
        this.nNetworking = nNetworking;
    }
}

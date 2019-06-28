
package in.kestone.eventbuddy.model.app_config_model;

import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("EBNL_ID")
    private Long mEBNLID;
    @SerializedName("Location_Name")
    private String mLocationName;

    public Long getEBNLID() {
        return mEBNLID;
    }

    public void setEBNLID(Long eBNLID) {
        mEBNLID = eBNLID;
    }

    public String getLocationName() {
        return mLocationName;
    }

    public void setLocationName(String locationName) {
        mLocationName = locationName;
    }

}

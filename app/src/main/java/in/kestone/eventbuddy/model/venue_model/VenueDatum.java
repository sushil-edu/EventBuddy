
package in.kestone.eventbuddy.model.venue_model;


import com.google.gson.annotations.SerializedName;


public class VenueDatum {

    @SerializedName("City_ID")
    private Long mCityID;
    @SerializedName("EBVC_ID")
    private Long mEBVCID;
    @SerializedName("Event_ID")
    private Long mEventID;
    @SerializedName("Venue_Latitude")
    private String mVenueLatitude;
    @SerializedName("Venue_Longitude")
    private String mVenueLongitude;
    @SerializedName("Venue_Name")
    private String mVenueName;

    public Long getCityID() {
        return mCityID;
    }

    public void setCityID(Long cityID) {
        mCityID = cityID;
    }

    public Long getEBVCID() {
        return mEBVCID;
    }

    public void setEBVCID(Long eBVCID) {
        mEBVCID = eBVCID;
    }

    public Long getEventID() {
        return mEventID;
    }

    public void setEventID(Long eventID) {
        mEventID = eventID;
    }

    public String getVenueLatitude() {
        return mVenueLatitude;
    }

    public void setVenueLatitude(String venueLatitude) {
        mVenueLatitude = venueLatitude;
    }

    public String getVenueLongitude() {
        return mVenueLongitude;
    }

    public void setVenueLongitude(String venueLongitude) {
        mVenueLongitude = venueLongitude;
    }

    public String getVenueName() {
        return mVenueName;
    }

    public void setVenueName(String venueName) {
        mVenueName = venueName;
    }

}

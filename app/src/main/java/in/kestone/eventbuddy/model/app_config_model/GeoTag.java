
package in.kestone.eventbuddy.model.app_config_model;

import com.google.gson.annotations.SerializedName;

public class GeoTag {

    @SerializedName("ActivationDateFrom")
    private String mActivationDateFrom;
    @SerializedName("ActivationDateTo")
    private String mActivationDateTo;
    @SerializedName("ActivationTimeFrom")
    private String mActivationTimeFrom;
    @SerializedName("ActivationTimeTo")
    private String mActivationTimeTo;
    @SerializedName("ErrorHeader")
    private String mErrorHeader;
    @SerializedName("ErrorMessage")
    private String mErrorMessage;
    @SerializedName("Label")
    private String mLabel;
    @SerializedName("Latitude")
    private Double mLatitude;
    @SerializedName("Longitude")
    private Double mLongitude;
    @SerializedName("Radius")
    private Double mRadius;
    @SerializedName("Visibility")
    private String mVisibility;
    @SerializedName("WelcomeText")
    private String mWelcomeText;

    public String getActivationDateFrom() {
        return mActivationDateFrom;
    }

    public void setActivationDateFrom(String activationDateFrom) {
        mActivationDateFrom = activationDateFrom;
    }

    public String getActivationDateTo() {
        return mActivationDateTo;
    }

    public void setActivationDateTo(String activationDateTo) {
        mActivationDateTo = activationDateTo;
    }

    public String getActivationTimeFrom() {
        return mActivationTimeFrom;
    }

    public void setActivationTimeFrom(String activationTimeFrom) {
        mActivationTimeFrom = activationTimeFrom;
    }

    public String getActivationTimeTo() {
        return mActivationTimeTo;
    }

    public void setActivationTimeTo(String activationTimeTo) {
        mActivationTimeTo = activationTimeTo;
    }

    public String getErrorHeader() {
        return mErrorHeader;
    }

    public void setErrorHeader(String errorHeader) {
        mErrorHeader = errorHeader;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        mErrorMessage = errorMessage;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(Double latitude) {
        mLatitude = latitude;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(Double longitude) {
        mLongitude = longitude;
    }

    public Double getRadius() {
        return mRadius;
    }

    public void setRadius(Double radius) {
        mRadius = radius;
    }

    public String getVisibility() {
        return mVisibility;
    }

    public void setVisibility(String visibility) {
        mVisibility = visibility;
    }

    public String getWelcomeText() {
        return mWelcomeText;
    }

    public void setWelcomeText(String welcomeText) {
        mWelcomeText = welcomeText;
    }

}

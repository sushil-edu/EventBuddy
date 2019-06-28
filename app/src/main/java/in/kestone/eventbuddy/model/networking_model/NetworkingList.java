
package in.kestone.eventbuddy.model.networking_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NetworkingList implements Serializable {

    @SerializedName("Designation")
    private String mDesignation;
    @SerializedName("EBMR_ID")
    private Long mEBMRID;
    @SerializedName("First_Name")
    private String mFirstName;
    @SerializedName("IsApproved")
    private String mIsApproved;
    @SerializedName("Last_Name")
    private String mLastName;
    @SerializedName("Networing_Request_Time")
    private String mNetworingRequestTime;
    @SerializedName("Networking_Location")
    private String mNetworkingLocation;
    @SerializedName("Networking_Request_Date")
    private String mNetworkingRequestDate;
    @SerializedName("Organization")
    private String mOrganization;
    @SerializedName("Request_From_ID")
    private String mRequestFromID;
    @SerializedName("Request_To_ID")
    private String mRequestToID;
    @SerializedName("toDes")
    private String mToDes;
    @SerializedName("toFname")
    private String mToFname;
    @SerializedName("toLname")
    private String mToLname;
    @SerializedName("toORG")
    private String mToORG;
    @SerializedName("toUsertype")
    private String mToUsertype;
    @SerializedName("UserType")
    private String mUserType;
    @SerializedName( "Event_ID" )
    private Long eventID;
    @SerializedName( "Location" )
    private String Location;
    @SerializedName( "ApprovedOn" )
    private String ApprovedOn;

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }


    public String getApprovedOn() {
        return ApprovedOn;
    }

    public void setApprovedOn(String approvedOn) {
        ApprovedOn = approvedOn;
    }

    public String getDesignation() {
        return mDesignation;
    }

    public void setDesignation(String designation) {
        mDesignation = designation;
    }

    public Long getEBMRID() {
        return mEBMRID;
    }

    public void setEBMRID(Long eBMRID) {
        mEBMRID = eBMRID;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getIsApproved() {
        return mIsApproved;
    }

    public void setIsApproved(String isApproved) {
        mIsApproved = isApproved;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getNetworingRequestTime() {
        return mNetworingRequestTime;
    }

    public void setNetworingRequestTime(String networingRequestTime) {
        mNetworingRequestTime = networingRequestTime;
    }

    public String getNetworkingLocation() {
        return mNetworkingLocation;
    }

    public void setNetworkingLocation(String networkingLocation) {
        mNetworkingLocation = networkingLocation;
    }

    public String getNetworkingRequestDate() {
        return mNetworkingRequestDate;
    }

    public void setNetworkingRequestDate(String networkingRequestDate) {
        mNetworkingRequestDate = networkingRequestDate;
    }

    public String getOrganization() {
        return mOrganization;
    }

    public void setOrganization(String organization) {
        mOrganization = organization;
    }

    public String getRequestFromID() {
        return mRequestFromID;
    }

    public void setRequestFromID(String requestFromID) {
        mRequestFromID = requestFromID;
    }

    public String getRequestToID() {
        return mRequestToID;
    }

    public void setRequestToID(String requestToID) {
        mRequestToID = requestToID;
    }

    public String getToDes() {
        return mToDes;
    }

    public void setToDes(String toDes) {
        mToDes = toDes;
    }

    public String getToFname() {
        return mToFname;
    }

    public void setToFname(String toFname) {
        mToFname = toFname;
    }

    public String getToLname() {
        return mToLname;
    }

    public void setToLname(String toLname) {
        mToLname = toLname;
    }

    public String getToORG() {
        return mToORG;
    }

    public void setToORG(String toORG) {
        mToORG = toORG;
    }

    public String getToUsertype() {
        return mToUsertype;
    }

    public void setToUsertype(String toUsertype) {
        mToUsertype = toUsertype;
    }

    public String getUserType() {
        return mUserType;
    }

    public void setUserType(String userType) {
        mUserType = userType;
    }

}

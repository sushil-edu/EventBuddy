
package in.kestone.eventbuddy.model.app_config_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConfNetworking {

    @SerializedName("Delegate_Networking_DateFrom")
    private String mDelegateNetworkingDateFrom;
    @SerializedName("Delegate_Networking_DateTo")
    private String mDelegateNetworkingDateTo;
    @SerializedName("Delegate_Networking_TimeFrom")
    private String mDelegateNetworkingTimeFrom;
    @SerializedName("Delegate_Networking_TimeTo")
    private String mDelegateNetworkingTimeTo;
    @SerializedName("Location")
    private List<Location> mLocation;
    @SerializedName("Networking_Alert_Msg_Header_Within_Delegates")
    private String mNetworkingAlertMsgHeaderWithinDelegates;
    @SerializedName("Networking_Alert_Msg_Header_Within_Speaker")
    private String mNetworkingAlertMsgHeaderWithinSpeaker;
    @SerializedName("Networking_Alert_Msg_With_Speaker")
    private String mNetworkingAlertMsgWithSpeaker;
    @SerializedName("Networking_Alert_Msg_Within_Delegates")
    private String mNetworkingAlertMsgWithinDelegates;
    @SerializedName("Networking_Request_Slot_Duration")
    private String mNetworkingRequestSlotDuration;
    @SerializedName("Speaker_Networking_DateFrom")
    private String mSpeakerNetworkingDateFrom;
    @SerializedName("Speaker_Networking_DateTo")
    private String mSpeakerNetworkingDateTo;
    @SerializedName("Speaker_Networking_TimeFrom")
    private String mSpeakerNetworkingTimeFrom;
    @SerializedName("Speaker_Networking_TimeTo")
    private String mSpeakerNetworkingTimeTo;

    public String getDelegateNetworkingDateFrom() {
        return mDelegateNetworkingDateFrom;
    }

    public void setDelegateNetworkingDateFrom(String delegateNetworkingDateFrom) {
        mDelegateNetworkingDateFrom = delegateNetworkingDateFrom;
    }

    public String getDelegateNetworkingDateTo() {
        return mDelegateNetworkingDateTo;
    }

    public void setDelegateNetworkingDateTo(String delegateNetworkingDateTo) {
        mDelegateNetworkingDateTo = delegateNetworkingDateTo;
    }

    public String getDelegateNetworkingTimeFrom() {
        return mDelegateNetworkingTimeFrom;
    }

    public void setDelegateNetworkingTimeFrom(String delegateNetworkingTimeFrom) {
        mDelegateNetworkingTimeFrom = delegateNetworkingTimeFrom;
    }

    public String getDelegateNetworkingTimeTo() {
        return mDelegateNetworkingTimeTo;
    }

    public void setDelegateNetworkingTimeTo(String delegateNetworkingTimeTo) {
        mDelegateNetworkingTimeTo = delegateNetworkingTimeTo;
    }

    public List<Location> getLocation() {
        return mLocation;
    }

    public void setLocation(List<Location> location) {
        mLocation = location;
    }

    public String getNetworkingAlertMsgHeaderWithinDelegates() {
        return mNetworkingAlertMsgHeaderWithinDelegates;
    }

    public void setNetworkingAlertMsgHeaderWithinDelegates(String networkingAlertMsgHeaderWithinDelegates) {
        mNetworkingAlertMsgHeaderWithinDelegates = networkingAlertMsgHeaderWithinDelegates;
    }

    public String getNetworkingAlertMsgHeaderWithinSpeaker() {
        return mNetworkingAlertMsgHeaderWithinSpeaker;
    }

    public void setNetworkingAlertMsgHeaderWithinSpeaker(String networkingAlertMsgHeaderWithinSpeaker) {
        mNetworkingAlertMsgHeaderWithinSpeaker = networkingAlertMsgHeaderWithinSpeaker;
    }

    public String getNetworkingAlertMsgWithSpeaker() {
        return mNetworkingAlertMsgWithSpeaker;
    }

    public void setNetworkingAlertMsgWithSpeaker(String networkingAlertMsgWithSpeaker) {
        mNetworkingAlertMsgWithSpeaker = networkingAlertMsgWithSpeaker;
    }

    public String getNetworkingAlertMsgWithinDelegates() {
        return mNetworkingAlertMsgWithinDelegates;
    }

    public void setNetworkingAlertMsgWithinDelegates(String networkingAlertMsgWithinDelegates) {
        mNetworkingAlertMsgWithinDelegates = networkingAlertMsgWithinDelegates;
    }

    public String getNetworkingRequestSlotDuration() {
        return mNetworkingRequestSlotDuration;
    }

    public void setNetworkingRequestSlotDuration(String networkingRequestSlotDuration) {
        mNetworkingRequestSlotDuration = networkingRequestSlotDuration;
    }

    public String getSpeakerNetworkingDateFrom() {
        return mSpeakerNetworkingDateFrom;
    }

    public void setSpeakerNetworkingDateFrom(String speakerNetworkingDateFrom) {
        mSpeakerNetworkingDateFrom = speakerNetworkingDateFrom;
    }

    public String getSpeakerNetworkingDateTo() {
        return mSpeakerNetworkingDateTo;
    }

    public void setSpeakerNetworkingDateTo(String speakerNetworkingDateTo) {
        mSpeakerNetworkingDateTo = speakerNetworkingDateTo;
    }

    public String getSpeakerNetworkingTimeFrom() {
        return mSpeakerNetworkingTimeFrom;
    }

    public void setSpeakerNetworkingTimeFrom(String speakerNetworkingTimeFrom) {
        mSpeakerNetworkingTimeFrom = speakerNetworkingTimeFrom;
    }

    public String getSpeakerNetworkingTimeTo() {
        return mSpeakerNetworkingTimeTo;
    }

    public void setSpeakerNetworkingTimeTo(String speakerNetworkingTimeTo) {
        mSpeakerNetworkingTimeTo = speakerNetworkingTimeTo;
    }

}

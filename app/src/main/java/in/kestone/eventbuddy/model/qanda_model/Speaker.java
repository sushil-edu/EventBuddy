
package in.kestone.eventbuddy.model.qanda_model;

import com.google.gson.annotations.SerializedName;

public class Speaker {

    @SerializedName("bio")
    private String mBio;
    @SerializedName("description")
    private Object mDescription;
    @SerializedName("designation")
    private String mDesignation;
    @SerializedName("organization")
    private Object mOrganization;
    @SerializedName("sid")
    private Long mSid;
    @SerializedName("speakerImage")
    private String mSpeakerImage;
    @SerializedName("speakerName")
    private String mSpeakerName;

    public String getBio() {
        return mBio;
    }

    public void setBio(String bio) {
        mBio = bio;
    }

    public Object getDescription() {
        return mDescription;
    }

    public void setDescription(Object description) {
        mDescription = description;
    }

    public String getDesignation() {
        return mDesignation;
    }

    public void setDesignation(String designation) {
        mDesignation = designation;
    }

    public Object getOrganization() {
        return mOrganization;
    }

    public void setOrganization(Object organization) {
        mOrganization = organization;
    }

    public Long getSid() {
        return mSid;
    }

    public void setSid(Long sid) {
        mSid = sid;
    }

    public String getSpeakerImage() {
        return mSpeakerImage;
    }

    public void setSpeakerImage(String speakerImage) {
        mSpeakerImage = speakerImage;
    }

    public String getSpeakerName() {
        return mSpeakerName;
    }

    public void setSpeakerName(String speakerName) {
        mSpeakerName = speakerName;
    }

}

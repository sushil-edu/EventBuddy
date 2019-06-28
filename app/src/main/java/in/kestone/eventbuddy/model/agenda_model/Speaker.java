
package in.kestone.eventbuddy.model.agenda_model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Speaker implements Serializable {

    @SerializedName("bio")
    private String mBio;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("designation")
    private String mDesignation;
    @SerializedName("organization")
    private String mOrganization;
    @SerializedName("sid")
    private int mSid;
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

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDesignation() {
        return mDesignation;
    }

    public void setDesignation(String designation) {
        mDesignation = designation;
    }

    public String getOrganization() {
        return mOrganization;
    }

    public void setOrganization(String organization) {
        mOrganization = organization;
    }

    public int getSid() {
        return mSid;
    }

    public void setSid(int sid) {
        mSid = sid;
    }

    public String getSpeakerImage() {
        return mSpeakerImage;
    }

    public void setSpeakerImage(String  speakerImage) {
        mSpeakerImage = speakerImage;
    }

    public String getSpeakerName() {
        return mSpeakerName;
    }

    public void setSpeakerName(String speakerName) {
        mSpeakerName = speakerName;
    }

}

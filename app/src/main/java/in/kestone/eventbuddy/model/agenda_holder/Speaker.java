
package in.kestone.eventbuddy.model.agenda_holder;

import com.google.gson.annotations.SerializedName;

public class Speaker {

    @SerializedName("Sid")
    private int mSID;
    @SerializedName("Bio")
    private String mBio;
    @SerializedName("Description")
    private String mDescription;
    @SerializedName("Designation")
    private String mDesignation;
    @SerializedName("Organization")
    private String mOrganization;
    @SerializedName("SpeakerImage")
    private String mSpeakerImage;
    @SerializedName("SpeakerName")
    private String mSpeakerName;

    public int getSID() {
        return mSID;
    }

    public void setSID(int mSID) {
        this.mSID = mSID;
    }

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


package in.kestone.eventbuddy.model.helpdesk_model;


import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("Category")
    private String mCategory;
    @SerializedName("Designation")
    private String mDesignation;
    @SerializedName("EBHD_ID")
    private Long mEBHDID;
    @SerializedName("EmailID")
    private String mEmailID;
    @SerializedName("Event_ID")
    private Long mEventID;
    @SerializedName("Inserted_On")
    private String mInsertedOn;
    @SerializedName("Name")
    private String mName;
    @SerializedName("Phoneno")
    private String mPhoneno;

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getDesignation() {
        return mDesignation;
    }

    public void setDesignation(String designation) {
        mDesignation = designation;
    }

    public Long getEBHDID() {
        return mEBHDID;
    }

    public void setEBHDID(Long eBHDID) {
        mEBHDID = eBHDID;
    }

    public String getEmailID() {
        return mEmailID;
    }

    public void setEmailID(String emailID) {
        mEmailID = emailID;
    }

    public Long getEventID() {
        return mEventID;
    }

    public void setEventID(Long eventID) {
        mEventID = eventID;
    }

    public String getInsertedOn() {
        return mInsertedOn;
    }

    public void setInsertedOn(String insertedOn) {
        mInsertedOn = insertedOn;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhoneno() {
        return mPhoneno;
    }

    public void setPhoneno(String phoneno) {
        mPhoneno = phoneno;
    }

}

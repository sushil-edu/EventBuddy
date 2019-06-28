
package in.kestone.eventbuddy.model.social_model;

import com.google.gson.annotations.SerializedName;


public class MDatum {

    @SerializedName("id")
    private Long mId;
    @SerializedName("Required")
    private Boolean mRequired;
    @SerializedName("Title")
    private String mTitle;
    @SerializedName("URL")
    private String mURL;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Boolean getRequired() {
        return mRequired;
    }

    public void setRequired(Boolean required) {
        mRequired = required;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getURL() {
        return mURL;
    }

    public void setURL(String uRL) {
        mURL = uRL;
    }

}

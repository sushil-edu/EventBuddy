
package in.kestone.eventbuddy.model.partners_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Detail implements Serializable {

    @SerializedName("Biography")
    private String mBiography;
    @SerializedName("Email")
    private String mEmail;
    @SerializedName("id")
    private Long mId;
    @SerializedName("IsDisplayContactNo")
    private String mIsDisplayContactNo;
    @SerializedName("Logo")
    private String mLogo;
    @SerializedName("Name")
    private String mName;
    @SerializedName("Phone")
    private String mPhone;
    @SerializedName("WebsiteLink")
    private String mWebsiteLink;
    @SerializedName("Category")
    private String mCategory;


    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getBiography() {
        return mBiography;
    }

    public void setBiography(String biography) {
        mBiography = biography;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getIsDisplayContactNo() {
        return mIsDisplayContactNo;
    }

    public void setIsDisplayContactNo(String isDisplayContactNo) {
        mIsDisplayContactNo = isDisplayContactNo;
    }

    public String getLogo() {
        return mLogo;
    }

    public void setLogo(String logo) {
        mLogo = logo;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getWebsiteLink() {
        return mWebsiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        mWebsiteLink = websiteLink;
    }

}

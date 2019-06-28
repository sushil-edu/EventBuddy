
package in.kestone.eventbuddy.model.knowledgebase_model;


import com.google.gson.annotations.SerializedName;

public class Knowledgebase {

    @SerializedName("Asset_Description")
    private String mAssetDescription;
    @SerializedName("Asset_Icon")
    private String mAssetIcon;
    @SerializedName("Asset_Link")
    private String mAssetLink;

    public String getAssetDescription() {
        return mAssetDescription;
    }

    public void setAssetDescription(String assetDescription) {
        mAssetDescription = assetDescription;
    }

    public String getAssetIcon() {
        return mAssetIcon;
    }

    public void setAssetIcon(String assetIcon) {
        mAssetIcon = assetIcon;
    }

    public String getAssetLink() {
        return mAssetLink;
    }

    public void setAssetLink(String assetLink) {
        mAssetLink = assetLink;
    }

}

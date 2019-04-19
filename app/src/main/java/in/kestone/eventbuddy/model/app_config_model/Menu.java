
package in.kestone.eventbuddy.model.app_config_model;

import com.google.gson.annotations.SerializedName;

public class Menu {

    @SerializedName("menuicon")
    private String mMenuicon;
    @SerializedName("menuid")
    private int mMenuid;
    @SerializedName("menutitle")
    private String mMenutitle;
    @SerializedName("status")
    private int mStatus;
    @SerializedName("displayTitle")
    private String mdisplayTitle;
    @SerializedName("Header")
    private String mHeader;
    @SerializedName("Subheader")
    private String mSubheader;

    public String getMenuicon() {
        return mMenuicon;
    }

    public void setMenuicon(String menuicon) {
        mMenuicon = menuicon;
    }

    public int getMenuid() {
        return mMenuid;
    }

    public void setMenuid(int menuid) {
        mMenuid = menuid;
    }

    public String getMenutitle() {
        return mMenutitle;
    }

    public void setMenutitle(String menutitle) {
        mMenutitle = menutitle;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public String getDisplayTitle() {
        return mdisplayTitle;
    }

    public void setDisplayTitle(String mdisplayTitle) {
        this.mdisplayTitle = mdisplayTitle;
    }

    public String getHeader() {
        return mHeader;
    }

    public void setHeader(String mHeader) {
        this.mHeader = mHeader;
    }

    public String getSubheader() {
        return mSubheader;
    }

    public void setSubheader(String mSubheader) {
        this.mSubheader = mSubheader;
    }
}

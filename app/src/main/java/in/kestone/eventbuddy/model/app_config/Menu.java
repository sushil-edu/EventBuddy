
package in.kestone.eventbuddy.model.app_config;

import com.google.gson.annotations.SerializedName;

public class Menu {

    @SerializedName("menuicon")
    private String mMenuicon;
    @SerializedName("menuid")
    private Long mMenuid;
    @SerializedName("menutitle")
    private String mMenutitle;
    @SerializedName("status")
    private Long mStatus;

    public String getMenuicon() {
        return mMenuicon;
    }

    public void setMenuicon(String menuicon) {
        mMenuicon = menuicon;
    }

    public Long getMenuid() {
        return mMenuid;
    }

    public void setMenuid(Long menuid) {
        mMenuid = menuid;
    }

    public String getMenutitle() {
        return mMenutitle;
    }

    public void setMenutitle(String menutitle) {
        mMenutitle = menutitle;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

}

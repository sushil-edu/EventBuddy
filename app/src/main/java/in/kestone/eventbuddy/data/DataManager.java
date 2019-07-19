package in.kestone.eventbuddy.data;

public class DataManager {
    private SharedPrefsHelper mSharedPrefsHelper;

    public DataManager(SharedPrefsHelper sharedPrefsHelper) {
        mSharedPrefsHelper = sharedPrefsHelper;
    }

    public void clear() {
        mSharedPrefsHelper.clear();
    }

    public void saveDetail(int userId, String name, String email, String designation, String image,
                           String organization, String mobile, String password) {
        mSharedPrefsHelper.putEmail(email);
        mSharedPrefsHelper.putUserId(userId);
        mSharedPrefsHelper.putName(name);
        mSharedPrefsHelper.putDesignation(designation);
        mSharedPrefsHelper.putImage(image);
        mSharedPrefsHelper.putOrganization(organization);
        mSharedPrefsHelper.putMobile(mobile);
        mSharedPrefsHelper.putPassword(password);
    }


    public String getEmailId() {
        return mSharedPrefsHelper.getEmail();
    }

    public int getUserId() {
        return mSharedPrefsHelper.getUserId();
    }

    public String getName() {
        return mSharedPrefsHelper.getName();
    }

    public String getImage() {
        return mSharedPrefsHelper.getImage();
    }

    public String getDesignation() {
        return mSharedPrefsHelper.getDesignation();
    }

    public String getOrganization() {
        return mSharedPrefsHelper.getOrganization();
    }

    public String getMobile() {
        return mSharedPrefsHelper.getMobile();
    }

    public String getPassword() {
        return mSharedPrefsHelper.getPassword();
    }

    public void setLoggedIn() {
        mSharedPrefsHelper.setLoggedInMode(true);
    }

    public Boolean getLoggedInMode() {
        return mSharedPrefsHelper.getLoggedInMode();
    }
}

package in.kestone.eventbuddy.data;

public class DataManager {
    private SharedPrefsHelper mSharedPrefsHelper;

    public DataManager(SharedPrefsHelper sharedPrefsHelper) {
        mSharedPrefsHelper = sharedPrefsHelper;
    }

    public void clear() {
        mSharedPrefsHelper.clear();
    }

    public void saveDetail(int userId, String name, String email,  String designation, String imagePath,
                           String organization, String mobile) {
        mSharedPrefsHelper.putEmail(email);
        mSharedPrefsHelper.putUserId(userId);
        mSharedPrefsHelper.putName(name);
        mSharedPrefsHelper.putDesignation(designation);
        mSharedPrefsHelper.putImagePath(imagePath);
        mSharedPrefsHelper.putOrganization(organization);
        mSharedPrefsHelper.putMobile(mobile);
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
    public String getImagePath() {
        return mSharedPrefsHelper.getImagePath();
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


    public void setLoggedIn() {
        mSharedPrefsHelper.setLoggedInMode(true);
    }

    public Boolean getLoggedInMode() {
        return mSharedPrefsHelper.getLoggedInMode();
    }
}

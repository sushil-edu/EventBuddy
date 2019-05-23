package in.kestone.eventbuddy.data;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPrefsHelper {
    private static final String MY_PREFS = "MY_PREFS";

    private static final String EMAIL = "EMAIL";
    private static final String USERID = "USERID";
    private static final String NAME = "NAME";
    private static final String IMAGE = "IMAGE";
    private static final String DESIGNATION = "DESIGNATION";
    private static final String ORGANIZATION = "ORGANIZATION";
    private static final String MOBILE = "MOBILE";
    private static final String PASSWORD = "PASSWORD";

    SharedPreferences mSharedPreferences;

    public SharedPrefsHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences( MY_PREFS, MODE_PRIVATE );
    }

    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }

    public void putEmail(String email) {
        mSharedPreferences.edit().putString( EMAIL, email ).apply();
    }

    public String getEmail() {
        return mSharedPreferences.getString( EMAIL, null );
    }

    public int getUserId() {
        return mSharedPreferences.getInt( USERID, 0 );
    }

    public void putUserId(int uID) {
        mSharedPreferences.edit().putInt( USERID, uID ).apply();
    }

    public void putName(String name) {
        mSharedPreferences.edit().putString( NAME, name ).apply();
    }

    public String getName() {
        return mSharedPreferences.getString( NAME, null );
    }

    public void putImage(String path) {
        mSharedPreferences.edit().putString( IMAGE, path ).apply();
    }

    public String getImage() {
        return mSharedPreferences.getString( IMAGE, null );
    }

    public void putDesignation(String path) {
        mSharedPreferences.edit().putString( DESIGNATION, path ).apply();
    }

    public String getDesignation() {
        return mSharedPreferences.getString( DESIGNATION, null );
    }

    public void putOrganization(String organization) {
        mSharedPreferences.edit().putString( ORGANIZATION, organization ).apply();
    }

    public String getOrganization() {
        return mSharedPreferences.getString( ORGANIZATION, null );
    }

    public void putMobile(String mobile) {
        mSharedPreferences.edit().putString( MOBILE, mobile ).apply();
    }

    public String getMobile() {
        return mSharedPreferences.getString( MOBILE, null );
    }

    public boolean getLoggedInMode() {
        return mSharedPreferences.getBoolean( "IS_LOGGED_IN", false );
    }

    public void setLoggedInMode(boolean loggedIn) {
        mSharedPreferences.edit().putBoolean( "IS_LOGGED_IN", loggedIn ).apply();
    }

    public void putPassword(String password) {
        mSharedPreferences.edit().putString( PASSWORD, password ).apply();
    }

    public String getPassword() {
        return mSharedPreferences.getString( PASSWORD, null );
    }
}

package in.kestone.eventbuddy.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import in.kestone.eventbuddy.model.app_config_model.AppConf;

import static android.content.Context.MODE_PRIVATE;

public class LocalStorage {

    public static void saveConfiguration(AppConf appConf, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences( CommonUtils.AppConfigurationPrev, MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat( "version", appConf.getAppVersion());
        String gson = new Gson().toJson( appConf );
        editor.putString( "AppConfiguration", gson );
        editor.apply();
    }

    public static String getConfiguration(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CommonUtils.AppConfigurationPrev, Context.MODE_PRIVATE);
        return sharedPreferences.getString("AppConfiguration", "");
    }

    public static float getConfigVersion(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(CommonUtils.AppConfigurationPrev, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat("version", 0);
    }

    public static void saveImagePath(String path, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences( CommonUtils.AppConfigurationPrev, MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString( "Path", path );
        editor.apply();
    }
    public static String getImagePath(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(CommonUtils.AppConfigurationPrev, Context.MODE_PRIVATE);
        return sharedPreferences.getString("Path", "");
    }

    public static int getEventID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CommonUtils.AppConfigurationPrev, Context.MODE_PRIVATE);
        return (int) sharedPreferences.getLong("EventID", 0);
    }

    public static void saveEventID(Long eventID, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(CommonUtils.AppConfigurationPrev, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong( "EventID",  eventID );
        editor.apply();
    }

    public static String getSplashBackground(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CommonUtils.AppConfigurationPrev, Context.MODE_PRIVATE);
        return sharedPreferences.getString("ImageURL", "");
    }

    public static void saveSplashBackground(String imageURL, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(CommonUtils.AppConfigurationPrev, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString( "ImageURL",  imageURL );
        editor.apply();
    }

    public static String getBackground(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CommonUtils.AppConfigurationPrev, Context.MODE_PRIVATE);
        return sharedPreferences.getString("ImageURL", "");
    }

    public static void saveBackground(String imageURL, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(CommonUtils.AppConfigurationPrev, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString( "ImageURL",  imageURL );
        editor.apply();
    }

    public static String getMasterHead(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CommonUtils.AppConfigurationPrev, Context.MODE_PRIVATE);
        return sharedPreferences.getString("ImageURL", "");
    }

    public static void saveMasterHead(String imageURL, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(CommonUtils.AppConfigurationPrev, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString( "ImageURL",  imageURL );
        editor.apply();
    }

    public static void clearData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(CommonUtils.AppConfigurationPrev, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }
}

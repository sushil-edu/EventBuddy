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
}

package in.kestone.eventbuddy;

import android.app.Application;

import in.kestone.eventbuddy.data.DataManager;
import in.kestone.eventbuddy.data.SharedPrefsHelper;

public class MvpApp extends Application {

    DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper(getApplicationContext());
        dataManager = new DataManager(sharedPrefsHelper);

    }

    public DataManager getDataManager() {
        return dataManager;
    }


}

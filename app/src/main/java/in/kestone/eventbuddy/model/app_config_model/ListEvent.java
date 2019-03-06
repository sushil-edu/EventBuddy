package in.kestone.eventbuddy.model.app_config_model;

public class ListEvent {
    static AppConf appConf;

    public static AppConf getAppConf() {
        return appConf;
    }

    public static void setAppConf(AppConf appConf) {
        ListEvent.appConf = appConf;
    }
}

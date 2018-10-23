package in.kestone.eventbuddy.view.splash;


import in.kestone.eventbuddy.data.DataManager;
import in.kestone.eventbuddy.view.base.BasePresenter;

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {

    SplashPresenter(DataManager dataManager) {
        super( dataManager );
    }

    @Override
    public void decideNextActivity() {
        if (getDataManager().getLoggedInMode()) {
            getMvpView().openMainActivity();
        } else {
            getMvpView().openLoginActivity();
        }
    }
}


package in.kestone.eventbuddy.view.base;


public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

}

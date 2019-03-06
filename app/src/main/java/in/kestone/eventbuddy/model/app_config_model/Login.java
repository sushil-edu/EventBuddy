
package in.kestone.eventbuddy.model.app_config_model;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("Button")
    private Button mButton;
    @SerializedName("ForgotButton")
    private ForgotButton mForgotButton;
    @SerializedName("Password")
    private Password mPassword;
    @SerializedName("UserName")
    private UserName mUserName;
    @SerializedName("WelcomeText")
    private String mWelcomeText;

    public Button getButton() {
        return mButton;
    }

    public void setButton(Button button) {
        mButton = button;
    }

    public ForgotButton getForgotButton() {
        return mForgotButton;
    }

    public void setForgotButton(ForgotButton forgotButton) {
        mForgotButton = forgotButton;
    }

    public Password getPassword() {
        return mPassword;
    }

    public void setPassword(Password password) {
        mPassword = password;
    }

    public UserName getUserName() {
        return mUserName;
    }

    public void setUserName(UserName userName) {
        mUserName = userName;
    }

    public String getWelcomeText() {
        return mWelcomeText;
    }

    public void setWelcomeText(String welcomeText) {
        mWelcomeText = welcomeText;
    }

}

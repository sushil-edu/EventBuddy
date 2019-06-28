
package in.kestone.eventbuddy.model.user_model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("Data")
    private List<Profile> mData;
    @SerializedName("Message")
    private String mMessage;
    @SerializedName("StatusCode")
    private int mStatusCode;
    @SerializedName( "Imagepath" )
    private String imagePath;

    public List<Profile> getData() {
        return mData;
    }

    public void setData(List<Profile> data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(int statusCode) {
        mStatusCode = statusCode;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}


package in.kestone.eventbuddy.model.agenda_model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ModelAgenda implements Serializable {

    @SerializedName("Data")
    private List<Agenda> mAgenda;
    @SerializedName("StatusCode")
    private int mStatusCode;
    @SerializedName("Message")
    private String mMessage;

    public List<Agenda> getAgenda() {
        return mAgenda;
    }

    public void setAgenda(List<Agenda> agenda) {
        mAgenda = agenda;
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(int statusCode) {
        mStatusCode = statusCode;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}

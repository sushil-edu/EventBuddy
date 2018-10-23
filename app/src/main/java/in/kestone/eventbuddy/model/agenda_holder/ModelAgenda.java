
package in.kestone.eventbuddy.model.agenda_holder;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ModelAgenda {

    @SerializedName("Agenda")
    private List<Agenda> mAgenda;
    @SerializedName("StatusCode")
    private String mStatusCode;

    public List<Agenda> getAgenda() {
        return mAgenda;
    }

    public void setAgenda(List<Agenda> agenda) {
        mAgenda = agenda;
    }

    public String getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(String statusCode) {
        mStatusCode = statusCode;
    }

}

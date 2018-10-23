
package in.kestone.eventbuddy.model.agenda_holder;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Agenda {

    @SerializedName("Date")
    private String mDate;
    @SerializedName("details")
    private List<Detail> mDetails;

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public List<Detail> getDetails() {
        return mDetails;
    }

    public void setDetails(List<Detail> details) {
        mDetails = details;
    }

}

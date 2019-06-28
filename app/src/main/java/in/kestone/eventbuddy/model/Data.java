package in.kestone.eventbuddy.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("EventName")
    private List<EventName> mEventName;

    public List<EventName> getEventName() {
        return mEventName;
    }

    public void setEventName(List<EventName> eventName) {
        mEventName = eventName;
    }

}

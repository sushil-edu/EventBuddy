
package in.kestone.eventbuddy.model.faq_model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MFAQ {

    @SerializedName("Data")
    private List<FAQList> mFQAList;
    @SerializedName("Message")
    private String mMessage;
    @SerializedName("StatusCode")
    private Long mStatusCode;

    public List<FAQList> getFQAList() {
        return mFQAList;
    }

    public void setFQAList(List<FAQList> fQAList) {
        mFQAList = fQAList;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(Long statusCode) {
        mStatusCode = statusCode;
    }

}

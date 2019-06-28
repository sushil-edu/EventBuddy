
package in.kestone.eventbuddy.model.partners_model;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Datum {

    @SerializedName("category")
    private String mCategory;
    @SerializedName("List")
    private List<Detail> mDetail;

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public List<Detail> getDetail() {
        return mDetail;
    }

    public void setDetail(List<Detail> detail) {
        mDetail = detail;
    }

}

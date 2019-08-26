package in.kestone.eventbuddy.fragment;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.model.faq_model.FAQList;
import in.kestone.eventbuddy.model.faq_model.MFAQ;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FAQFragment extends Fragment {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerViewFAQ)
    RecyclerView faqRV;
    View view;
    private ArrayList<FAQList> faqList = new ArrayList<>();
    private FAQAdapter faqAdapter;

    public FAQFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_faq, container, false);
        ButterKnife.bind(this, view);
        getFAQ();
        Progress.showProgress(getContext());
        return view;
    }

    public void getFAQ() {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<MFAQ> call = apiInterface.getFAQ(LocalStorage.getEventID(getActivity()));
        call.enqueue(new Callback<MFAQ>() {
            @Override
            public void onResponse(Call<MFAQ> call, Response<MFAQ> response) {
                if (response.code() == 200) {
                    if (response.body().getStatusCode() == 200 && response.body().getFQAList().size() > 0) {

                        faqList.addAll(response.body().getFQAList());
                        faqAdapter = new FAQAdapter(getActivity(), faqList);
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        faqRV.setLayoutManager(mLayoutManager);
//                        recyclerView.addItemDecoration(new SpacesItemDecoration(2,20, true));
                        faqRV.setHasFixedSize(true);
                        faqRV.setAdapter(faqAdapter);
                        faqAdapter.notifyDataSetChanged();

                    } else {
                        CustomDialog.showInvalidPopUp(getActivity(), CONSTANTS.ERROR, response.body().getMessage());
                    }
                } else {
                    CustomDialog.showInvalidPopUp(getActivity(), CONSTANTS.ERROR, response.message());
                }


                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<MFAQ> call, Throwable t) {
                CustomDialog.showInvalidPopUp(getActivity(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR);
                Progress.closeProgress();
            }

        });
    }
}

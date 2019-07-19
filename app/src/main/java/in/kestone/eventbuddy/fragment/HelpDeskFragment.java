package in.kestone.eventbuddy.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import in.kestone.eventbuddy.model.helpdesk_model.Datum;
import in.kestone.eventbuddy.model.helpdesk_model.MHelpDesk;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpDeskFragment extends Fragment {

    @BindView(R.id.recyclerViewHelpDesk)
    RecyclerView recyclerViewHelpDesk;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    HelpDeskAdapter helpDeskAdapter;
    View view;
    ArrayList<Datum> listHelpDesk = new ArrayList<>();

    public HelpDeskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_help_desk, container, false);
        ButterKnife.bind(this, view);

        getHelpDesk();
        Progress.showProgress(getActivity());
        recyclerViewHelpDesk.setLayoutManager(new GridLayoutManager(getContext(), 1));
//        recyclerView.addItemDecoration(new SpacesItemDecoration(2,20, true));
        recyclerViewHelpDesk.setHasFixedSize(true);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            getHelpDesk();
            Progress.showProgress(getActivity());
            swipeRefreshLayout.setRefreshing(false);
        });

        return view;
    }

    public void getHelpDesk() {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<MHelpDesk> call = apiInterface.helpDesk(LocalStorage.getEventID(getActivity()));
        call.enqueue(new Callback<MHelpDesk>() {
            @Override
            public void onResponse(Call<MHelpDesk> call, Response<MHelpDesk> response) {
                if (response.code() == 200) {
                    listHelpDesk.clear();
                    if (response.body().getStatusCode() == 200 && response.body().getData().size() > 0) {
                        listHelpDesk.addAll(response.body().getData());
                        helpDeskAdapter = new HelpDeskAdapter(getActivity(), listHelpDesk);
                        recyclerViewHelpDesk.setAdapter(helpDeskAdapter);
                        helpDeskAdapter.notifyDataSetChanged();
                    } else {
                        CustomDialog.showInvalidPopUp(getActivity(), CONSTANTS.ERROR, response.body().getMessage());
                    }
                } else {
                    CustomDialog.showInvalidPopUp(getActivity(), CONSTANTS.ERROR, response.message());
                }
                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<MHelpDesk> call, Throwable t) {
                CustomDialog.showInvalidPopUp(getActivity(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR);
                Progress.closeProgress();
            }
        });
    }

    private class HelpDeskAdapter extends RecyclerView.Adapter<HelpDeskAdapter.ViewHolder> {

        Activity activity;
        ArrayList<Datum> detailArrayList;

        HelpDeskAdapter(FragmentActivity activity, ArrayList<Datum> detailArrayList) {
            this.activity = activity;
            this.detailArrayList = detailArrayList;
//            this.detailsCallback = detailsCallback;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view =
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.helpdesk_cell, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Datum helpDesk = listHelpDesk.get(position);
            holder.title.setText(helpDesk.getCategory());
            holder.name.setText(helpDesk.getName());
            holder.email.setText(helpDesk.getEmailID());
            holder.phone.setText(helpDesk.getPhoneno());
        }

        @Override
        public int getItemCount() {
            return detailArrayList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView title, email, phone, name, designation;

            ViewHolder(View view) {
                super(view);

                title = view.findViewById(R.id.titleTv);
                email = view.findViewById(R.id.emailTv);
                phone = view.findViewById(R.id.contactTv);
                name = view.findViewById(R.id.nameTv);
                designation = view.findViewById(R.id.designationTv);
            }
        }
    }
}

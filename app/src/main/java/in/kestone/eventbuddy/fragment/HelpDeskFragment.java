package in.kestone.eventbuddy.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Eventlistener.PartnerDetailsCallback;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.SpacesItemDecoration;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.model.Helpdesk;
import in.kestone.eventbuddy.model.helpdesk_model.Datum;
import in.kestone.eventbuddy.model.helpdesk_model.MHelpDesk;
import in.kestone.eventbuddy.view.partners.PartnerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpDeskFragment extends Fragment {

    @BindView( R.id.recyclerViewHelpDesk )
    RecyclerView recyclerViewHelpDesk;
    HelpDeskAdapter helpDeskAdapter;
    View view;
    ArrayList<Datum> listHelpDesk = new ArrayList<>(  );

//    @BindView( R.id.txtEmail )
////    TextView email;
//    @BindView( R.id.txtPhone )
//    TextView phone;
//    @BindView( R.id.txtTitle)
//    TextView title;

    public HelpDeskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_help_desk, container, false );
        ButterKnife.bind( this, view );

        getHelpDesk();
//

        recyclerViewHelpDesk.setLayoutManager( new GridLayoutManager( getContext(), 1 ) );
//        recyclerView.addItemDecoration(new SpacesItemDecoration(2,20, true));
        recyclerViewHelpDesk.setHasFixedSize( true );


//     title.setText( "HelpDesk" );
//     email.setText( "Email  : test@email.com" );
//     phone.setText( "Mobile : 9876543210" );
        return view;
    }

    private class HelpDeskAdapter extends RecyclerView.Adapter<HelpDeskAdapter.ViewHolder> {

        Activity activity;
        ArrayList<Datum> detailArrayList;
        PartnerDetailsCallback detailsCallback;

        public HelpDeskAdapter(FragmentActivity activity, ArrayList<Datum> detailArrayList) {
            this.activity = activity;
            this.detailArrayList = detailArrayList;
//            this.detailsCallback = detailsCallback;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.helpdesk_cell, parent, false );

            return new ViewHolder( view );
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//            holder.partnerName.setText( detailArrayList.get( position ).getName() );
//            holder.rootLayout.setOnClickListener( new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    detailsCallback.onDetailClickCallback( detailArrayList.get( position ) );
//                }
//            } );
            Datum helpDesk = listHelpDesk.get( position );
            holder.title.setText( helpDesk.getName() );
            holder.email.setText( helpDesk.getEmailID() );
            holder.phone.setText( helpDesk.getPhoneno() );
        }

        @Override
        public int getItemCount() {
            return detailArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView title, email, phone, name, designation;


            public ViewHolder(View view) {
                super( view );

                title = view.findViewById( R.id.titleTv );
                email = view.findViewById( R.id.emailTv );
                phone = view.findViewById( R.id.contactTv );
                name = view.findViewById( R.id.nameTv );
                designation = view.findViewById( R.id.designationTv );
            }
        }
    }
    public void getHelpDesk(){
        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<MHelpDesk> call = apiInterface.helpDesk( CONSTANTS.EVENTID );
        call.enqueue( new Callback<MHelpDesk>() {
            @Override
            public void onResponse(Call<MHelpDesk> call, Response<MHelpDesk> response) {
                if(response.code()==200) {
                    if (response.body().getStatusCode() == 200 && response.body().getData().size() > 0) {
                        listHelpDesk.addAll( response.body().getData() );
                        helpDeskAdapter = new HelpDeskAdapter( getActivity(), listHelpDesk );
                        recyclerViewHelpDesk.setAdapter( helpDeskAdapter );
                        helpDeskAdapter.notifyDataSetChanged();
                    } else {
                        CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.body().getMessage() );
                    }
                }


            }

            @Override
            public void onFailure(Call<MHelpDesk> call, Throwable t) {
                CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
            }
        } );
    }
}

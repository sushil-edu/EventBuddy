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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Eventlistener.PartnerDetailsCallback;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.Helpdesk;
import in.kestone.eventbuddy.model.partners_model.List;
import in.kestone.eventbuddy.view.partners.PartnerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpDeskFragment extends Fragment {

    String strJson="{\n" +
            "  \"statusCode\":\"200\",\n" +
            "  \"helpDesk\":[\n" +
            "    {\n" +
            "      \"type\":\"Help Desk\",\n" +
            "      \"email\":\"email@email.com\",\n" +
            "      \"phone\":\"9876543210\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\":\"Help Desk2\",\n" +
            "      \"email\":\"email@email.com\",\n" +
            "      \"phone\":\"1234567890\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"Message\":\"success or error message\"\n" +
            "}";

//    @BindView( R.id.recyclerViewHelpDesk )
//    RecyclerView recyclerViewHelpDesk;
//    HelpDeskAdapter helpDeskAdapter;
    View view;
    ArrayList<Helpdesk> listHelpDesk = new ArrayList<>(  );

    @BindView( R.id.txtEmail )
    TextView email;
    @BindView( R.id.txtPhone )
    TextView phone;
    @BindView( R.id.txtTitle)
    TextView title;

    public HelpDeskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.helpdesk_cell, container, false );
        ButterKnife.bind( this, view );

//        listHelpDesk.addAll( strJson )
//
//        helpDeskAdapter = new HelpDeskAdapter( getActivity(), catDetailArrayList , this);
//
//        recyclerViewHelpDesk.setLayoutManager( new GridLayoutManager( getContext(), 2 ) );
//        recyclerView.addItemDecoration(new SpacesItemDecoration(2,20, true));
//        recyclerViewHelpDesk.setHasFixedSize( true );
//        recyclerViewHelpDesk.setAdapter( helpDeskAdapter );
//        helpDeskAdapter.notifyDataSetChanged();

     title.setText( "HelpDesk" );
     email.setText( "Email  : test@email.com" );
     phone.setText( "Mobile : 9876543210" );
        return view;
    }

    private class HelpDeskAdapter extends RecyclerView.Adapter<HelpDeskAdapter.ViewHolder> {

        Activity activity;
        ArrayList<List> detailArrayList;
        PartnerDetailsCallback detailsCallback;

        public HelpDeskAdapter(FragmentActivity activity, ArrayList<in.kestone.eventbuddy.model.partners_model.List> detailArrayList, PartnerDetailsCallback detailsCallback) {
            this.activity = activity;
            this.detailArrayList = detailArrayList;
            this.detailsCallback = detailsCallback;

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
        }

        @Override
        public int getItemCount() {
            return detailArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView title, email, phone;


            public ViewHolder(View view) {
                super( view );

                title = view.findViewById( R.id.txtTitle );
                email = view.findViewById( R.id.txtEmail);
                phone = view.findViewById( R.id.txtPhone );
            }
        }
    }
}

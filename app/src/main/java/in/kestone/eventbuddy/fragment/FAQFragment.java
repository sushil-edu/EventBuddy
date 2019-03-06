package in.kestone.eventbuddy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.ReadJson;
import in.kestone.eventbuddy.model.faq_model.FAQList;
import in.kestone.eventbuddy.model.faq_model.MFAQ;

/**
 * A simple {@link Fragment} subclass.
 */
public class FAQFragment extends Fragment {

    @BindView(R.id.title)
    TextView title;
    @BindView( R.id.recyclerViewFAQ )
    RecyclerView faqRV;
    View view;
    MFAQ mfaq;

    private ArrayList<FAQList> faqList = new ArrayList<>();
    private FAQAdapter faqAdapter;

    public FAQFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_faq, container, false );
        ButterKnife.bind( this, view );
        getFAQ();
        return view;
    }

    private void getFAQ() {
        mfaq = new Gson().fromJson( ReadJson.loadJSONFromAsset( getActivity(), "faq.json" ),
                MFAQ.class );
        if (mfaq.getStatusCode().equalsIgnoreCase( "200" )) {
            title.setText( mfaq.getFAQ().get( 0 ).getHeader() );
            faqList.addAll( mfaq.getFAQ().get( 0 ).getFAQList());
            faqAdapter = new FAQAdapter( getActivity(), faqList);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager( getContext());
            mLayoutManager.setOrientation( LinearLayoutManager.VERTICAL);
            faqRV.setLayoutManager( mLayoutManager );
//        recyclerView.addItemDecoration(new SpacesItemDecoration(2,20, true));
            faqRV.setHasFixedSize( true );
            faqRV.setAdapter( faqAdapter );
            faqAdapter.notifyDataSetChanged();

        } else {
            Log.e( "Status", String.valueOf( mfaq.getStatusCode() ) );
        }
    }

}

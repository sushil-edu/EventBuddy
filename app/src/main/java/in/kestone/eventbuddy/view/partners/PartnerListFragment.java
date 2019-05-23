package in.kestone.eventbuddy.view.partners;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.kestone.eventbuddy.Eventlistener.PartnerDetailsCallback;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.partners_model.Detail;

@SuppressLint("ValidFragment")
public class PartnerListFragment extends Fragment implements PartnerDetailsCallback {
    RecyclerView recyclerView;
    PartnerAdapter adapter;
    View view;
    int pos, size;
    ArrayList<Detail> catDetailArrayList = new ArrayList<>();
    String type;


    public PartnerListFragment(int pos, List<Detail> catDetailArrayList, String type) {
        this.catDetailArrayList = (ArrayList<Detail>) catDetailArrayList;
        this.pos = pos;
        this.type = type;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_partner_list, container, false );
        adapter = new PartnerAdapter( getActivity(), catDetailArrayList , this);
        recyclerView = view.findViewById( R.id.recyclerViewPartner );
        recyclerView.setLayoutManager( new GridLayoutManager( getContext(), 2 ) );
//        recyclerView.addItemDecoration(new SpacesItemDecoration(2,20, true));
        recyclerView.setHasFixedSize( true );
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);
        recyclerView.setLayoutAnimation(animation);
        recyclerView.setAdapter( adapter );
        adapter.notifyDataSetChanged();

        return view;
    }


    @Override
    public void onDetailClickCallback(Detail list) {
        Intent intent = new Intent( getActivity(), PartnerDetails.class );
        Bundle bundle = new Bundle(  );
        bundle.putSerializable( "details",  list );
        bundle.putString( "type", type );
        intent.putExtras(  bundle );
        startActivity( intent );
    }

}

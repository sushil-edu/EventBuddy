package in.kestone.eventbuddy.fragment;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.model.app_config_model.ListEvent;
import in.kestone.eventbuddy.view.main.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class Priority extends Fragment {

    @BindView(R.id.layout_continue)
    LinearLayout layout_continue;
    @BindView( R.id.tv_welcome_text )
    TextView welcome_text;
    @BindView( R.id.tvBtnLabel)
    TextView tvBtnLabel;
    @BindView( R.id.image_background )
    ImageView imageBackGround;
    View view;

    public Priority() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_priority, container, false );
        initialiseView();
        return view;
    }

    private void initialiseView() {
        ButterKnife.bind( this, view );

        if(LocalStorage.getEventID( getActivity() )!=0){
            Picasso.get().load(  CONSTANTS.betaimagepath.concat( LocalStorage.getBackground( getActivity() ) ))
                    .into( imageBackGround );

        }
        welcome_text.setText( ListEvent.getAppConf().getEvent().getPriority().getWelcomeText() );
        tvBtnLabel.setText( ListEvent.getAppConf().getEvent().getPriority().getLabel() );
        layout_continue.setOnClickListener(view -> {

            startActivity( new Intent( getActivity(), MainActivity.class ) );
            getActivity().finish();

        });
    }

}

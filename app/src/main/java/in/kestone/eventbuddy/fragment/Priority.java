package in.kestone.eventbuddy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.app_config.ListEvent;
import in.kestone.eventbuddy.view.main.MainActivity;
import in.kestone.eventbuddy.widgets.CustomTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Priority extends Fragment {

    @BindView(R.id.layout_continue)
    LinearLayout layout_continue;
    @BindView( R.id.tv_welcome_text )
    CustomTextView welcome_text;

    View view;

    public Priority() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_priority, container, false );
        initialiseView();
        return view;
    }

    private void initialiseView() {
        ButterKnife.bind( this, view );

        welcome_text.setText( ListEvent.getAppConf().getEvent().getPriority().getWelcomeText() );
        layout_continue.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity( new Intent( getActivity(), MainActivity.class ) );
                getActivity().finish();

            }
        } );
    }

}

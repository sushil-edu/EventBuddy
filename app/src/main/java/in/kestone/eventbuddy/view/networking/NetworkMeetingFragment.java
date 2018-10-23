package in.kestone.eventbuddy.view.networking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.R;

public class NetworkMeetingFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.mScheduledTv)
    TextView mScheduledTv;
    @BindView(R.id.mApproveTv)
    TextView mApproveTv;
    @BindView(R.id.mPendingTv)
    TextView mPendingTv;

    public NetworkMeetingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_my_meeting, container, false );
        ButterKnife.bind( this, view );

        mScheduledTv.setOnClickListener( this );
        mApproveTv.setOnClickListener( this );
        mPendingTv.setOnClickListener( this );

        //default fragment selected
        {
            mScheduledTv.setTextColor( getResources().getColor( R.color.colorPrimary ) );
            mApproveTv.setTextColor( getResources().getColor( R.color.grey ) );
            mPendingTv.setTextColor( getResources().getColor( R.color.grey ) );
            getChildFragmentManager().beginTransaction()
                    .replace( R.id.container, new MyScheduled() )
                    .commit();
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mScheduledTv:
                mScheduledTv.setTextColor( getResources().getColor( R.color.colorPrimary ) );
                mApproveTv.setTextColor( getResources().getColor( R.color.grey ) );
                mPendingTv.setTextColor( getResources().getColor( R.color.grey ) );
                getChildFragmentManager().beginTransaction()
                        .replace( R.id.container, new MyScheduled() )
                        .commit();

                break;

            case R.id.mApproveTv:
                mApproveTv.setTextColor( getResources().getColor( R.color.colorPrimary ) );
                mPendingTv.setTextColor( getResources().getColor( R.color.grey ) );
                mScheduledTv.setTextColor( getResources().getColor( R.color.grey ) );
                getChildFragmentManager().beginTransaction()
                        .replace( R.id.container, new MyScheduled() )
                        .commit();

                break;

            case R.id.mPendingTv:
                mPendingTv.setTextColor( getResources().getColor( R.color.colorPrimary ) );
                mScheduledTv.setTextColor( getResources().getColor( R.color.grey ) );
                mApproveTv.setTextColor( getResources().getColor( R.color.grey ) );
                getChildFragmentManager().beginTransaction()
                        .replace( R.id.container, new MyScheduled() )
                        .commit();

                break;
        }
    }
}

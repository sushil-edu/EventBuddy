package in.kestone.eventbuddy.view.networking;

import android.app.Dialog;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.app_config_model.Location;

class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyHolder> {


    private ArrayList<Location> userList;
    private SelectedSpeaker selected;
    private Dialog dialog;


    LocationAdapter(SelectedSpeaker selectedSpeaker, ArrayList<Location> userList, Dialog dialog) {
        this.userList = userList;
        this.dialog = dialog;
        this.selected = selectedSpeaker;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.alert_user_cell, parent, false );
        return new MyHolder( view );
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final Location location = userList.get( position );
        Log.e( "Name ", location.getLocationName() );
        holder.nameTv.setText( location.getLocationName() );
        holder.nameTv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected.onSelect( location.getLocationName(), location.getEBNLID(), "Location" );
                dialog.dismiss();
            }
        } );

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView nameTv;

        public MyHolder(View itemView) {
            super( itemView );
            nameTv = itemView.findViewById( R.id.nameTv );
//            nameTv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Speaker speaker= userList.get(getAdapterPosition());
//                    selected.onSelect( speaker.getSpeakerName());
//                    //NetworkScheduleFragment.toMailId = userData.getEmailID() + "";
//                    dialog.dismiss();
//                }
//            });

        }
    }
}

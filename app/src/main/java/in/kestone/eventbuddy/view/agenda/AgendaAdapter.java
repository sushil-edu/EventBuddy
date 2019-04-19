package in.kestone.eventbuddy.view.agenda;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.data.SharedPrefsHelper;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.http.CallUtils;
import in.kestone.eventbuddy.model.agenda_model.Detail;
import in.kestone.eventbuddy.model.speaker_model.SpeakerDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.ViewHolder> {

    private float rate = 0;
    private float rat;
    private Context context;
    private Activity activity;
    private List<Detail> agendaList;
    private ArrayList<SpeakerDetail> speakerList;
    private String displayLabel;

    public AgendaAdapter(Activity activity, List<Detail> detailArrayList, String displayLabel) {
        this.context = activity;
        this.agendaList = detailArrayList;
        this.activity = (Activity) context;
        this.displayLabel = displayLabel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.agenda_cell, parent, false );

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Detail agendaData = agendaList.get( position );

        holder.titleTv.setText( agendaData.getLongTitle() );
        holder.timeTv.setText( agendaData.getTime() );
        holder.locationTv.setText( "Location: " + agendaData.getLocation() );
        holder.headingTv.setText( agendaData.getShortTitle() );

        //speaker list
        speakerList = new ArrayList<>();
        speakerList.clear();
        speakerList = (ArrayList<SpeakerDetail>) agendaData.getSpeaker();
        if (speakerList.size() > 0) {
            holder.nestedReyclerView.setVisibility( View.VISIBLE );
            SpeakerAdapter spkAdapter = new SpeakerAdapter( activity, speakerList );
            holder.nestedReyclerView.setAdapter( spkAdapter );
        } else {
            holder.nestedReyclerView.setVisibility( View.GONE );
        }
        holder.titleTv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog.showDetail( context, agendaData.getShortTitle(), agendaData.getLongTitle(), agendaData.getSessionBrief() );
            }
        } );

        //rating
        if (agendaData.getRating().equalsIgnoreCase( "True" )) {
            holder.layout_rating.setVisibility( View.VISIBLE );

            holder.rateTv.setText( agendaData.getRatingLabel() );
            holder.layout_rating.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rate = dialogRating( context, agendaData.getRatingLabel(), agendaData.getId(), agendaData.getRatingPlaceholder() );
                    holder.avgRatingBar.setRating( rate );
                }
            } );
        } else {
            holder.layout_rating.setVisibility( View.GONE );
        }

        if (agendaData.getIsMyagenda().equalsIgnoreCase( "0" ) && agendaData.getIsMyagenda() != null) {
            holder.addIv.setVisibility( View.VISIBLE );
        } else {
            holder.addIv.setVisibility( View.GONE );
        }

//        //+MyMeeting

        if (displayLabel.equalsIgnoreCase( CONSTANTS.MYAGENDA )) {
            holder.addIv.setText( "- " + CONSTANTS.MYAGENDA );
            holder.addIv.setTextColor( context.getResources().getColor( R.color.textColorRed ) );
            holder.addIv.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HashMap<String, Long> request = new HashMap<>();
                    request.put( "Event_ID", CONSTANTS.EVENTID );
                    request.put( "Delegate_ID", (long) new SharedPrefsHelper( context ).getUserId() );
                    request.put( "Session_ID", (long) agendaData.getId() );
                    deleteMyAgenda( request, position );
                    Progress.showProgress( context );
                }
            } );
        } else {
            holder.addIv.setText( "+ " + CONSTANTS.MYAGENDA );
            holder.addIv.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HashMap<String, Long> request = new HashMap<>();
                    request.put( "Event_ID", CONSTANTS.EVENTID );
                    request.put( "Delegate_ID", (long) new SharedPrefsHelper( context ).getUserId() );
                    request.put( "Session_ID", (long) agendaData.getId() );
                    addMyAgenda( request );
                    Progress.showProgress( context );
                }
            } );
        }


    }

    private void deleteMyAgenda(HashMap<String, Long> request, int pos) {
        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<JsonObject> call = apiInterface.deleteMyAgenda( request );
        CallUtils.enqueueWithRetry( call, 2, new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    JsonObject jsonObject = response.body();
                    if (Long.parseLong( String.valueOf( jsonObject.get( "StatusCode" ) ) ) == 200) {
                        myAgenda();
                        if (jsonObject.get( "Data" ).getAsJsonArray().size() > 0) {
                            removeAt( pos );
                        }
                        CustomDialog.showValidPopUp( context, CONSTANTS.SUCCESS, jsonObject.get( "Message" ).toString().replace( "\"","" ) );
                    } else {
                        CustomDialog.showInvalidPopUp( context, CONSTANTS.ERROR, jsonObject.get( "Message" ).toString().replace( "\"","" ) );

                    }
                } else {
                    CustomDialog.showInvalidPopUp( context, CONSTANTS.ERROR, response.message() );
                }
                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                CustomDialog.showInvalidPopUp( context, CONSTANTS.ERROR, t.getMessage() );
                Progress.closeProgress();
            }
        } );
    }

    @Override
    public int getItemCount() {
        Log.d( "sender", "Broadcasting message" + agendaList.size() );
        return agendaList.size();
    }

    // Send an Intent with an action named "event-buddy". The Intent sent should
// be received by the ReceiverActivity.
    private void myAgenda() {
        Log.d( "sender", "Broadcasting message" );
        Intent intent = new Intent( "event-buddy" );
        // You can also include some extra data.
        intent.putExtra( "message", CONSTANTS.MYAGENDA );
        LocalBroadcastManager.getInstance( context ).sendBroadcast( intent );
    }

    private float dialogRating(final Context context, String title, final long agendaID, String placeHolder) {
        final Dialog rate = new Dialog( context );
        rate.requestWindowFeature( Window.FEATURE_NO_TITLE );
        rate.setCancelable( true );
        rate.setContentView( R.layout.dialog_rating );
        LinearLayout root = rate.findViewById( R.id.layout_root );
        TextView tv_title = rate.findViewById( R.id.tv_title );
        EditText comment = rate.findViewById( R.id.tv_comment );
        comment.setHint( placeHolder );
        tv_title.setText( title );
        rat = 0;
        final RatingBar ratingBar = rate.findViewById( R.id.avgRatingBar );
        ratingBar.setRating( 0 );
        TextView cancel = rate.findViewById( R.id.cancel );
        TextView save = rate.findViewById( R.id.save );
        save.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate.dismiss();
                JSONObject object = new JSONObject();
                try {
                    object.put( "userId", new SharedPrefsHelper( context ).getUserId() );
                    object.put( "agendaId", agendaID );
                    object.put( "rate", ratingBar.getRating() );
                    Log.e( "Rate ", String.valueOf( object ) );

                    rat = ratingBar.getRating();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } );
        cancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rate.dismiss();
//
            }
        } );

        ratingBar.setOnRatingBarChangeListener( new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
            }
        } );

        rate.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        rate.show();

        Vibrator v = (Vibrator) context.getSystemService( Context.VIBRATOR_SERVICE );
        // Vibrate for 500 milliseconds
        v.vibrate( 150 );
        Animation shake = AnimationUtils.loadAnimation( context, R.anim.shake );
        root.setAnimation( shake );
        return rat;
    }

    public void addMyAgenda(HashMap<String, Long> request) {
        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<JsonObject> call = apiInterface.addMyAgenda( request );
        CallUtils.enqueueWithRetry( call, 2, new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    JsonObject jsonObject = response.body();
                    if (Long.parseLong( String.valueOf( jsonObject.get( "StatusCode" ) ) ) == 200) {
                        myAgenda();
                        CustomDialog.showValidPopUp( context, CONSTANTS.SUCCESS, jsonObject.get( "Message" ).toString().replace( "\"","" ) );
                    } else {
                        CustomDialog.showInvalidPopUp( context, CONSTANTS.ERROR, jsonObject.get( "Message" ).toString() );

                    }
                } else {
                    CustomDialog.showInvalidPopUp( context, CONSTANTS.ERROR, response.message() );
                }
                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                CustomDialog.showInvalidPopUp( context, CONSTANTS.ERROR, t.getMessage() );
                Progress.closeProgress();
            }
        } );
    }

    public void removeAt(int position) {
        agendaList.remove( position );
        notifyItemRemoved( position );
        notifyItemRangeChanged( position, agendaList.size() );
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView addIv, titleTv, timeTv, rateTv, locationTv, headingTv;
        private RatingBar avgRatingBar;
        private CardView card;
        private RecyclerView nestedReyclerView;
        private LinearLayout layout_rating;

        public ViewHolder(View itemView) {
            super( itemView );
            addIv = itemView.findViewById( R.id.addIv );
            titleTv = itemView.findViewById( R.id.titleTv );
            timeTv = itemView.findViewById( R.id.timeTv );
            headingTv = itemView.findViewById( R.id.headingTv );
            avgRatingBar = itemView.findViewById( R.id.avgRatingBar );
            card = itemView.findViewById( R.id.card );
            rateTv = itemView.findViewById( R.id.rateTv );
            nestedReyclerView = itemView.findViewById( R.id.nestedReyclerView );
            layout_rating = itemView.findViewById( R.id.layout_rating );
            locationTv = itemView.findViewById( R.id.locationTv );

            LinearLayoutManager mLayoutManager = new LinearLayoutManager( context );
            mLayoutManager.setOrientation( LinearLayoutManager.HORIZONTAL );
            nestedReyclerView.setLayoutManager( mLayoutManager );
            nestedReyclerView.setHasFixedSize( true );
        }
    }
}

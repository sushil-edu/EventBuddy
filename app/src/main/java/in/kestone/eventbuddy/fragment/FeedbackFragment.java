package in.kestone.eventbuddy.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.ReadJson;
import in.kestone.eventbuddy.feedbackDB.FeedbackDbClient;
import in.kestone.eventbuddy.feedbackDB.SaveFeedback;
import in.kestone.eventbuddy.model.feedback_model.Datum;
import in.kestone.eventbuddy.model.feedback_model.FeedbackMdl;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment {

    View view;
    //    @BindView(R.id.mWebView)
//    WebView webView;
    String url;
    String dateFrom, dateTo, timeFrom, timeTo;
    @BindView(R.id.rvFeedbackRadio)
    RecyclerView rvFeedbackRadio;
    @BindView(R.id.rvFeedbackCheckbox)
    RecyclerView rvFeedbackCheckbox;
    @BindView(R.id.rvFeedbackTextView)
    RecyclerView rvFeedbackTextView;

    public FeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feedback, container, false);
//        feedback();
        ButterKnife.bind(this, view);
        deleteAll();


        setFeedbackToView();
        return view;
    }

    private void setFeedbackToView() {
        rvFeedbackRadio.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFeedbackRadio.setHasFixedSize(true);
        rvFeedbackRadio.setItemViewCacheSize(30);
        rvFeedbackRadio.setAdapter(new FeedbackAdapter(getActivity(), feedback(), "radio"));

//        rvFeedbackCheckbox.setLayoutManager(new LinearLayoutManager(getActivity()));
//        rvFeedbackCheckbox.setHasFixedSize(true);
//        rvFeedbackCheckbox.setItemViewCacheSize(30);
//        rvFeedbackCheckbox.setAdapter(new FeedbackAdapter(getActivity(), feedback(),"checkbox"));
//
//        rvFeedbackTextView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        rvFeedbackTextView.setHasFixedSize(true);
//        rvFeedbackTextView.setItemViewCacheSize(30);
//        rvFeedbackTextView.setAdapter(new FeedbackAdapter(getActivity(), feedback(),"textbox"));

    }

    private ArrayList<Datum> feedback() {
        ArrayList<Datum> feedbackMdlArrayList = new ArrayList<>();
        String feedbackJson = ReadJson.loadJSONFromAsset(getActivity(), "ebfeedback.json");
        FeedbackMdl mdl = new Gson().fromJson(feedbackJson, FeedbackMdl.class);
        feedbackMdlArrayList.addAll(mdl.getData());
        return feedbackMdlArrayList;
    }

//    public void feedback() {
//
//        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
//        Call<JsonObject> call = apiInterface.feedBack( LocalStorage.getEventID( getActivity() ) );
//        call.enqueue( new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//
//                if (response.code() == 200) {
//                    JsonObject jsonObject = response.body();
//                    if (Long.parseLong( String.valueOf( jsonObject.get( "StatusCode" ) ) ) == 200) {
//                        if (jsonObject.get( "Data" ).getAsJsonArray().size() > 0) {
//
//                            dateFrom = jsonObject.get( "Data" ).getAsJsonArray
//                                    ().get( 0 ).getAsJsonObject().get( "Start_date" ).toString().replace( "\"", "" );
//                            dateTo = jsonObject.get( "Data" ).getAsJsonArray
//                                    ().get( 0 ).getAsJsonObject().get( "End_Date" ).toString().replace( "\"", "" );
//                            timeFrom = jsonObject.get( "Data" ).getAsJsonArray
//                                    ().get( 0 ).getAsJsonObject().get( "Start_Time" ).toString().replace( "\"", "" );
//                            timeTo = jsonObject.get( "Data" ).getAsJsonArray
//                                    ().get( 0 ).getAsJsonObject().get( "End_Time" ).toString().replace( "\"", "" );
//
//                            if (CompareDateTime.compareDate( dateFrom, dateTo )) {
//                                if (CompareDateTime.compareTime( timeFrom, timeTo )) {
//                                    url = jsonObject.get( "Data" ).getAsJsonArray
//                                            ().get( 0 ).getAsJsonObject().get( "Feedback_Weblink" ).toString().replace( "\"", "" );
//                                    webView.setWebViewClient( new MyWebViewClient() );
//                                    loadWebview( url );
//                                } else {
//                                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, "Feedback disable" );
//                                }
//                            } else {
//                                CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, "Feedback disable" );
//                            }
//
//                        }
//                    } else {
//                        CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, jsonObject.get( "Message" ).toString() );
//
//                    }
//                } else {
//                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.message() );
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Progress.closeProgress();
//                CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
//            }
//        } );
//
//    }
//
//    @SuppressLint("SetJavaScriptEnabled")
//    private void loadWebview(String url) {
//        webView.getSettings().setJavaScriptEnabled( true );
//        webView.setWebViewClient( new MyWebViewClient() );
//        webView.loadUrl( url );
//        webView.requestFocus();
//    }
//
//    private class MyWebViewClient extends WebViewClient {
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl( url );
//
//            return true;
//        }
//
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            Progress.closeProgress();
//        }
//    }

    private void deleteAll() {

        ArrayList<SaveFeedback> list = new ArrayList<>();
        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                FeedbackDbClient.getInstance(getActivity()).getDatabase()
                        .feedbackDao()
                        .deleteAll();
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);

            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

}

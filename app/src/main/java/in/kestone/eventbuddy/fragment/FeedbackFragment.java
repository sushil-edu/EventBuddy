package in.kestone.eventbuddy.fragment;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.CompareDateTime;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment {

    View view;
    @BindView(R.id.mWebView)
    WebView webView;
    String url;
    String dateFrom, dateTo, timeFrom, timeTo;

    public FeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_feedback, container, false );
        feedback();
        ButterKnife.bind( this, view );
        return view;
    }

    public void feedback() {

        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<JsonObject> call = apiInterface.feedBack( LocalStorage.getEventID( getActivity() ) );
        call.enqueue( new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.code() == 200) {
                    JsonObject jsonObject = response.body();
                    if (Long.parseLong( String.valueOf( jsonObject.get( "StatusCode" ) ) ) == 200) {
                        if (jsonObject.get( "Data" ).getAsJsonArray().size() > 0) {

                            dateFrom = jsonObject.get( "Data" ).getAsJsonArray
                                    ().get( 0 ).getAsJsonObject().get( "Start_date" ).toString().replace( "\"", "" );
                            dateTo = jsonObject.get( "Data" ).getAsJsonArray
                                    ().get( 0 ).getAsJsonObject().get( "End_Date" ).toString().replace( "\"", "" );
                            timeFrom = jsonObject.get( "Data" ).getAsJsonArray
                                    ().get( 0 ).getAsJsonObject().get( "Start_Time" ).toString().replace( "\"", "" );
                            timeTo = jsonObject.get( "Data" ).getAsJsonArray
                                    ().get( 0 ).getAsJsonObject().get( "End_Time" ).toString().replace( "\"", "" );

                            if (CompareDateTime.compareDate( dateFrom, dateTo )) {
                                if (CompareDateTime.compareTime( timeFrom, timeTo )) {
                                    url = jsonObject.get( "Data" ).getAsJsonArray
                                            ().get( 0 ).getAsJsonObject().get( "Feedback_Weblink" ).toString().replace( "\"", "" );
                                    webView.setWebViewClient( new MyWebViewClient() );
                                    loadWebview( url );
                                } else {
                                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, "Feedback disable" );
                                }
                            } else {
                                CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, "Feedback disable" );
                            }

                        }
                    } else {
                        CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, jsonObject.get( "Message" ).toString() );

                    }
                } else {
                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.message() );
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Progress.closeProgress();
                CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
            }
        } );

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void loadWebview(String url) {
        webView.getSettings().setJavaScriptEnabled( true );
        webView.setWebViewClient( new MyWebViewClient() );
        webView.loadUrl( url );
        webView.requestFocus();
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl( url );

            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Progress.closeProgress();
        }
    }

}

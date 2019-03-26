package in.kestone.eventbuddy.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
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

    public FeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_feedback, container, false );
        getPolling();
        ButterKnife.bind( this, view );
        return view;
    }

    public void getPolling() {

        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<JsonObject> call = apiInterface.feedBack( CONSTANTS.EVENTID );
        call.enqueue( new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.code()==200) {
                    JsonObject jsonObject = response.body();
                    if (Long.parseLong( String.valueOf( jsonObject.get( "StatusCode" ) ) ) == 200
                            && response.code() == 200) {
                        if (jsonObject.get( "Data" ).getAsJsonArray().size() > 0) {
                            url = jsonObject.get( "Data" ).getAsJsonArray
                                    ().get( 0 ).getAsJsonObject().get( "Feedback_Weblink" ).toString().replace( "\"","" );
                            webView.setWebViewClient( new MyWebViewClient() );
                            loadWebview( url );
                        }
                    } else {
                        CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.message() );

                    }
                }
                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Progress.closeProgress();
                CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, t.getMessage() );
            }
        } );

    }

    private void loadWebview(String url) {
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
        }
    }

}

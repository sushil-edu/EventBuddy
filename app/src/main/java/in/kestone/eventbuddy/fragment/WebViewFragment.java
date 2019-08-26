package in.kestone.eventbuddy.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

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
import in.kestone.eventbuddy.Eventlistener.FragmentErrorListener;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment extends Fragment {

    @BindView(R.id.mWebView)
    WebView mWebView;

    APIInterface apiInterface;
    FragmentErrorListener errorListener;
    View view;
    String module = null, url=null;
    Bundle bundle;

    public WebViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_web_view, container, false );
        ButterKnife.bind( this, view );


        initialiseView();
        return view;
    }

    private void initialiseView() {
        apiInterface = APIClient.getClient().create( APIInterface.class );
        bundle = this.getArguments();
        module = bundle.getString( "module" );
        errorListener = (FragmentErrorListener) getActivity();

        loadView( module );
    }

    private void loadView(String module) {
        switch (module) {
            case CONSTANTS.TANDC:
                getTANDC();
                Progress.showProgress( getActivity() );
                break;
        }
    }


    private void openURL(String url) {
        mWebView.getSettings().setJavaScriptEnabled( true );
        mWebView.loadUrl( url );
        mWebView.requestFocus();
    }

    private void getTANDC() {
        Call<JsonObject> call = apiInterface.termsAndCondition( LocalStorage.getEventID( getActivity() ) );
        call.enqueue( new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code()==200) {
                    JsonObject jsonObject = response.body();
                    if (Long.parseLong( String.valueOf( jsonObject.get( "StatusCode" ) ) ) == 200) {
                        if (jsonObject.get( "Data" ).getAsJsonArray().size() > 0) {
                            url = jsonObject.get( "Data" ).getAsJsonArray
                                    ().get( 0 ).getAsJsonObject().get( "Webpage_Link" ).toString().replace( "\"","" );
                            mWebView.setWebViewClient( new MyWebViewClient() );
                            openURL( url );
                        }
                    } else {
                        CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, jsonObject.get( "Message" ).toString() );
                        errorListener.onError( true );

                    }
                } else {
                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.message() );
                }
                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
                Progress.closeProgress();
            }
        } );
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl( url );
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Progress.showProgress( getActivity() );
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Progress.closeProgress();
        }
    }

}

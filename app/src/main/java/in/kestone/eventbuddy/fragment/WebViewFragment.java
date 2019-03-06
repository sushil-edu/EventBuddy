package in.kestone.eventbuddy.fragment;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.Eventlistener.FragmentErrorListener;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.view.stream.ActivityStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment extends Fragment {

    @BindView(R.id.mWebView)
    WebView mWebView;

    FragmentErrorListener errorListener;
    View view;
    String module = null, tcJSON="{\"statusCode\":\"200\",\"termAndCondition\":[{\"ModuleLabel\":" +
            "\"Terms and Conditions\",\"Header\":\"Term And Condition Header\",\"SubHeading\":" +
            "\"Term And Condition Sub Header\",\"url\":\"https://kestone.in/privacy-policy.html\"}]," +
            "\"Message\":\"success or error message\"}";
    Bundle bundle;
    CustomDialog customDialog;

    public WebViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_web_view, container, false );
        ButterKnife.bind(this, view );

        initialiseView();
        return view;
    }

    private void initialiseView() {
        bundle = this.getArguments();
        module = bundle.getString( "module" );
        errorListener = (FragmentErrorListener)getActivity();

        customDialog = new CustomDialog();

        loadView(module);
    }

    private void loadView(String module) {
        switch (module){
            case "Terms And Conditions":
                try {
                    JSONObject jsonObject = new JSONObject( tcJSON );
                    if(jsonObject.getString( "statusCode" ).equals( "200" )){
                        JSONArray jsonArray = new JSONArray( jsonObject.getString( "termAndCondition" ) );
                        JSONObject innerJsonObject = jsonArray.getJSONObject( 0 );
                        mWebView.setWebViewClient(new MyWebViewClient());
                        openURL(innerJsonObject.getString( "url" ));
                    }else {
                        customDialog.showInvalidPopUp( getActivity(),"Error", jsonObject.getString( "Message" ) );
                        errorListener.onError(true);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void openURL(String url) {
        mWebView.loadUrl(url);
        mWebView.requestFocus();
    }


    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Progress.showProgress( getActivity() );
        }
        @Override
        public void onPageFinished(WebView view, String url)
        {
            Progress.closeProgress();
        }
    }

}

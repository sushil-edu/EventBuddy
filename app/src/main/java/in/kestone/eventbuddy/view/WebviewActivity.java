package in.kestone.eventbuddy.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.CommonUtils;
import in.kestone.eventbuddy.common.LocalStorage;

public class WebviewActivity extends AppCompatActivity {
    WebView mWebview;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        String url = getIntent().getStringExtra( "url" );
        Log.i( "File Path : ", url );

        final Activity activity = this;
        mWebview = new WebView( activity );


        mWebview.getSettings().setJavaScriptEnabled( true ); // enable javascript

        mWebview.setWebViewClient( new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                CustomDialog.showInvalidPopUp( activity, CONSTANTS.ERROR, description );
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError( view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString() );
            }
        } );

        if (CommonUtils.isValidUrl( url )) {
            mWebview.loadUrl( url );
        } else {
            String uri = String.valueOf( Uri.parse( LocalStorage.getImagePath( activity ).concat( url)) );
            mWebview.loadUrl( "http://drive.google.com/viewerng/viewer?embedded=true&url=".concat( uri ));
        }
        setContentView( mWebview );
    }
}

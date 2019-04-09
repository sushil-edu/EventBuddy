package in.kestone.eventbuddy.view.social;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.R;

public class SocialListFragment extends Fragment {
    @BindView(R.id.webView)
    WebView webView;
    View view;
    String url;

    public SocialListFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_social_list, container, false );
        ButterKnife.bind( this, view );

        url = getArguments().getString( "url" );

        webView.getSettings().setJavaScriptEnabled( true );
        webView.setWebViewClient( new MyWebViewClient() );
        webView.loadUrl( url );
        webView.requestFocus();
        return view;
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

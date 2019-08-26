package in.kestone.eventbuddy.view.social;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.R;

public class SocialListFragment extends Fragment {
    @BindView(R.id.webView)
    WebView webView;
    private View view;
    private String url;

    public SocialListFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_social_list, container, false );
        ButterKnife.bind( this, view );

        url = getArguments().getString( "url" );
//        url="https://twitter.com/KestoneIMS?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor";
//        webView.getSettings().setJavaScriptEnabled( true );
        webView.setWebViewClient( new MyWebViewClient() );
//        webView.loadUrl( url );
//        webView.requestFocus();

        PackageManager pm = getActivity().getPackageManager();
        startActivity(newFacebookIntent(pm, url));


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

    private static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
//            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.twitter.android", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
//                uri = Uri.parse(url);
            }else {
//                Log.e("Twitter ", "Not install");
            }
        } catch (PackageManager.NameNotFoundException ignored) {

        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }


}

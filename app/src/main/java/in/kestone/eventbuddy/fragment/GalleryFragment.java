package in.kestone.eventbuddy.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import in.kestone.eventbuddy.R;

public class GalleryFragment extends Fragment {


    View v;

    public GalleryFragment() {

    }
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        super.onCreateView( inflater, container, savedInstanceState );
        v = inflater.inflate( R.layout.activity_gallery, container, false );

        return v;
    }

}

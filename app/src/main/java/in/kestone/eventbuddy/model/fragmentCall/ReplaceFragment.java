package in.kestone.eventbuddy.model.fragmentCall;

import android.content.Context;

public class ReplaceFragment {
    Context context;

    public ReplaceFragment(Context context) {
        this.context=context;
//        context.getSupportFragmentManager().beginTransaction()
//                .replace( R.id.container, new ActivityStream())
//                .commit();
//        mTitleTv.setText("Activity Stream");
    }
}

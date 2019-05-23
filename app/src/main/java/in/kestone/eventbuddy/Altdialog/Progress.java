package in.kestone.eventbuddy.Altdialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;

import in.kestone.eventbuddy.R;

public class Progress {
    private static ProgressDialog progress;

    static public void showProgress(Context context) {
        progress = new ProgressDialog( context );
        try {
            progress.show();
        } catch (WindowManager.BadTokenException e) {
        }
        /*progress.setMessage("");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);*/
        progress.setCancelable( false );
        progress.getWindow()
                .setBackgroundDrawable( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );
        progress.setContentView( R.layout.progress_bar );

    }

    static public void closeProgress() {
        progress.dismiss();
    }
}

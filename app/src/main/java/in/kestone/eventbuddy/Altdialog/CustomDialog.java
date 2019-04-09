package in.kestone.eventbuddy.Altdialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.widgets.TouchImageView;

public class CustomDialog {
    public static int flag = 0;
    static boolean result = false;


    public static void showInvalidPopUp(final Context context, String title, String body) {

        final Dialog dialog = new Dialog( context );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.dialog_incorrect_credentials );
        TextView titleTv = dialog.findViewById( R.id.titleTv );
        titleTv.setText( title );
        TextView bodyTv = dialog.findViewById( R.id.bodyTv );
        bodyTv.setText( body );
        LinearLayout root = dialog.findViewById( R.id.layout_root );
        TextView yesTv = dialog.findViewById( R.id.yes );
        yesTv.setText( "Retry" );
        yesTv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                flag = 1;
            }
        } );


        dialog.getWindow().setLayout( WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT );
        dialog.show();

        Vibrator v = (Vibrator) context.getSystemService( Context.VIBRATOR_SERVICE );
        // Vibrate for 500 milliseconds
        v.vibrate( 150 );
        Animation shake = AnimationUtils.loadAnimation( context, R.anim.shake );
        root.setAnimation( shake );

    }

    public static void showValidPopUp(final Context context, String title, String body) {
        final Dialog dialog = new Dialog( context );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.dialog_correct_credentials );
        TextView titleTv = dialog.findViewById( R.id.titleTv );
        titleTv.setText( title );
        TextView bodyTv = dialog.findViewById( R.id.bodyTv );
        bodyTv.setText( body );
        TextView yesTv = dialog.findViewById( R.id.yes );
        yesTv.setText( "Ok" );
        yesTv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        } );


        dialog.getWindow().setLayout( WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT );
        dialog.show();
    }

    public static int retry() {
        return flag;
    }


    @SuppressLint("JavascriptInterface")
    public static void imagePreview(final Context context, String url) {
        final Dialog dialog = new Dialog( context );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.dialog_image_preview );
        TouchImageView imagePreviewIV;
        ImageView closeIV;
        imagePreviewIV = dialog.findViewById( R.id.imagePreviewIV );
//        imagePreviewIV.setMaxZoom( 4f );
        closeIV = dialog.findViewById( R.id.closeIV );
        Glide.with( context )
                .load( url )
                .placeholder( R.drawable.def_image )
                .into( imagePreviewIV );
        closeIV.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        } );
//        imagePreviewIV.setOnTouchImageViewListener( new TouchImageViewNew.OnTouchImageViewListener() {
//            @Override
//            public void onMove() {
//                RectF rect = imagePreviewIV.getZoomedRect();
//                float currentZoom = imagePreviewIV.getCurrentZoom();
//                boolean  isZoomed = imagePreviewIV.isZoomed();
//                //Log.e("sfsdfdsf", ""+currentZoom+","+isZoomed);
//                //Do whater ever stuff u want
//            }
//        } );

        dialog.getWindow().setLayout( WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT );
        dialog.show();
    }

    public static void invalidPopUp(final Activity activity, String title, String body) {

        final Dialog dialog = new Dialog( activity );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.dialog_incorrect_credentials );
        TextView titleTv = dialog.findViewById( R.id.titleTv );
        titleTv.setText( title );
        TextView bodyTv = dialog.findViewById( R.id.bodyTv );
        bodyTv.setText( body );
        LinearLayout root = dialog.findViewById( R.id.layout_root );
        TextView yesTv = dialog.findViewById( R.id.yes );
        yesTv.setText( "Retry" );
        yesTv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                activity.finish();
                flag = 1;
            }
        } );


        dialog.getWindow().setLayout( WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT );
        dialog.show();

        Vibrator v = (Vibrator) activity.getSystemService( Context.VIBRATOR_SERVICE );
        // Vibrate for 500 milliseconds
        v.vibrate( 150 );
        Animation shake = AnimationUtils.loadAnimation( activity, R.anim.shake );
        root.setAnimation( shake );

    }

}

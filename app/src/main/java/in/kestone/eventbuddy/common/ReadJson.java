package in.kestone.eventbuddy.common;

import android.app.Activity;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import in.kestone.eventbuddy.model.agenda.AgendaList;
import in.kestone.eventbuddy.model.agenda.ModelAgenda;
import in.kestone.eventbuddy.model.app_config.AppConf;
import in.kestone.eventbuddy.model.app_config.ListEvent;

public class ReadJson {
    public String loadJSONFromAsset(Activity activity, String fileName) {
        String json = null;
        try {
            InputStream is = activity.getAssets().open( fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read( buffer );
            is.close();
            json = new String( buffer, "UTF-8" );
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }
    //    https://drive.google.com/open?id=112m28j8-S9yS6f50gYJQlrUcB8LcH1Sz

//    private void downLoadFile(final Context context, String Url, final String fileName) {
//        openPdf(fileName);
////        showProgress(context);
////        File file = new File( Environment.getExternalStorageDirectory() + "/Android/data/eventbuddy");
////        if (!file.exists())
////            file.mkdirs();
////
////        AndroidNetworking.download(Url, file.getPath(), fileName)
////                .setTag("downloadTest")
////                .setPriority( Priority.HIGH)
////                .build()
////                .setDownloadProgressListener(new DownloadProgressListener() {
////                    @Override
////                    public void onProgress(long bytesDownloaded, long totalBytes) {
////                        Log.e("Progress", bytesDownloaded + "");
////                    }
////                })
////                .startDownload(new DownloadListener() {
////                    @Override
////                    public void onDownloadComplete() {
////                        Log.e("Complete", "Complete");
////                        progressDialog.dismiss();
////                        Toast.makeText(context, "Download Complete", Toast.LENGTH_SHORT).show();
////
////                        openPdf(fileName);
////                    }
////
////                    @Override
////                    public void onError(ANError error) {
////                        Log.e("Fail", "Fail");
////                        progressDialog.dismiss();
////                        Toast.makeText(context, "Download Failed", Toast.LENGTH_SHORT).show();
////                    }
////                });
//    }
//private String openConfig() {
//    File file = new File( Environment.getExternalStorageDirectory() + "/Android/data/eventbuddy/app_conf.json" );
//    Uri path = Uri.fromFile( file );
//
//    String ret = "";
//    // Log.e("File path ", path.toString());
//
//    try {
//        InputStream inputStream = getContentResolver().openInputStream( path );
//
//        if (inputStream != null) {
//            InputStreamReader inputStreamReader = new InputStreamReader( inputStream );
//            BufferedReader bufferedReader = new BufferedReader( inputStreamReader );
//            String receiveString = "";
//            StringBuilder stringBuilder = new StringBuilder();
//
//            while ((receiveString = bufferedReader.readLine()) != null) {
//                stringBuilder.append( receiveString );
//            }
//
//            inputStream.close();
//            ret = stringBuilder.toString();
//            //   Log.e("JSON FIle ", ret);
//        }
//    } catch (FileNotFoundException e) {
//        Log.e( "login activity", "File not found: " + e.toString() );
//    } catch (IOException e) {
//        Log.e( "login activity", "Can not read file: " + e.toString() );
//    }
//
//    return ret;
//}

}

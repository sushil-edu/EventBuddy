package in.kestone.eventbuddy.Service;

import android.app.IntentService;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

public class MyIntentService extends IntentService {

    public static final String CUSTOM_ACTION = "YOUR_CUSTOM_ACTION";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent arg0) {
        Intent intent = new Intent(CUSTOM_ACTION);
//        intent.putExtra("DATE", new Date().toString());
        Log.d(MyIntentService.class.getSimpleName(), "sending broadcast");

        // send local broadcast
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}

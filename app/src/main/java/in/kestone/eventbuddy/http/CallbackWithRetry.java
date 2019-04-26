package in.kestone.eventbuddy.http;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CallbackWithRetry<T> implements Callback<T> {

    private static final String TAG = CallbackWithRetry.class.getSimpleName();
    private final Call<T> call;
    private int totalRetries = 3;
    private int retryCount = 0;

    public CallbackWithRetry(Call<T> call, int totalRetries) {
        this.call = call;
        this.totalRetries = totalRetries;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (!CallUtils.isCallSuccess( response ))
            if (retryCount++ < totalRetries) {
                Log.v( TAG, "Retrying API Call -  (" + retryCount + " / " + totalRetries + ")" );
                retry();
            } else
                onFinalResponse( call, response );
        else
            onFinalResponse( call, response );
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.e( TAG, t.getMessage() );
        if (retryCount++ < totalRetries) {
            Log.v( TAG, "Retrying API Call -  (" + retryCount + " / " + totalRetries + ")" );
            retry();
        } else
            onFinalFailure( call, t );
    }

    public void onFinalResponse(Call<T> call, Response<T> response) {

    }

    public void onFinalFailure(Call<T> call, Throwable t) {
    }

    private void retry() {
        call.clone().enqueue( this );
    }
}

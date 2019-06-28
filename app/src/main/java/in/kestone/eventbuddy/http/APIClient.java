package in.kestone.eventbuddy.http;

import android.app.Application;
import android.content.Context;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;
    private static String BASEURL="http://eventsbuddy.in/MobileApp/api/";
    private static APIClient apiClientInstance;

    public static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel( HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .retryOnConnectionFailure( true )
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory( GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}

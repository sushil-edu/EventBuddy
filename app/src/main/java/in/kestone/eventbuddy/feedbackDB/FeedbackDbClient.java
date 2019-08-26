package in.kestone.eventbuddy.feedbackDB;

import android.content.Context;

import androidx.room.Room;

public class FeedbackDbClient {
    private static FeedbackDbClient mInstance;
    private Context context;
    private FeedbackDb feedbackDb;

    public FeedbackDbClient(Context context) {
        this.context = context;
        feedbackDb = Room.databaseBuilder(context, FeedbackDb.class, "FeedbackDB").build();
    }

    public static synchronized FeedbackDbClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new FeedbackDbClient(mCtx);
        }
        return mInstance;
    }

    public FeedbackDb getDatabase() {
        return feedbackDb;
    }
}

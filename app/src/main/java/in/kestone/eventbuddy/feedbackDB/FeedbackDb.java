package in.kestone.eventbuddy.feedbackDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {SaveFeedback.class}, version = 1)
public abstract class FeedbackDb extends RoomDatabase {
    public abstract FeedbackDao feedbackDao();
}

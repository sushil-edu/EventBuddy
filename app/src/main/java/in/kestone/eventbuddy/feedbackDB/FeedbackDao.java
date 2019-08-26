package in.kestone.eventbuddy.feedbackDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface FeedbackDao {
    @Query("SELECT * FROM tbl_feedback")
    List<SaveFeedback> getSavedFeedback();

    @Query("DELETE FROM tbl_feedback")
    void deleteAll();
    @Insert
    void insert(SaveFeedback saveFeedback);

    @Delete
    void delete(SaveFeedback saveFeedback);

    @Query("UPDATE tbl_feedback SET answer = :ans where questionID = :qId")
    void update(String ans, String qId);
}

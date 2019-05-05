package app.axxess.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import app.axxess.modals.Comment;
@Dao
public interface CommentDao {
    @Insert
    void insert(Comment comment);

    @Query("SELECT * from comment_table where id =  :id")
    LiveData<List<Comment>> getAllCommentsOfImage(String id);
}

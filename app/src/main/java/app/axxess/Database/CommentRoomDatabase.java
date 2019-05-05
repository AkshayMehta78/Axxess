package app.axxess.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import app.axxess.modals.Comment;

@Database(entities = {Comment.class}, version = 1)
public abstract class CommentRoomDatabase extends RoomDatabase {

    public abstract CommentDao commentDao();

    private static CommentRoomDatabase INSTANCE;

    public static CommentRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CommentRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CommentRoomDatabase.class, "comment_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}

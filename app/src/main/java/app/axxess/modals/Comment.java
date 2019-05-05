package app.axxess.modals;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "comment_table")
public class Comment {

    @PrimaryKey(autoGenerate = true)
    public int commentId;

    @NotNull
    public String id;

    @NotNull
    public String comment;



}

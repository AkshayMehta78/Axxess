package app.axxess.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import app.axxess.modals.Comment;

public class CommentRepository {

    private CommentDao mCommentDao;

    public CommentRepository(Context context) {
        CommentRoomDatabase db = CommentRoomDatabase.getDatabase(context);
        mCommentDao = db.commentDao();
    }

    public LiveData<List<Comment>> fetchAllCommentsById(String id) {
        return mCommentDao.getAllCommentsOfImage(id);
    }


    public void insertComment(Comment comment) {
        new insertAsyncTask(mCommentDao).execute(comment);
    }

    private static class insertAsyncTask extends AsyncTask<Comment, Void, Void> {

        private CommentDao mAsyncTaskDao;

        insertAsyncTask(CommentDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Comment... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
package app.axxess.ViewModal;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import app.axxess.Database.CommentRepository;
import app.axxess.modals.Comment;

public class CommentViewModel extends AndroidViewModel {

    private CommentRepository mRepository;

    public CommentViewModel (Application application) {
        super(application);
        mRepository = new CommentRepository(application);
    }

    public LiveData<List<Comment>> fetchAllCommentsById(String id) {
        return mRepository.fetchAllCommentsById(id);
    }

    public void insert(Comment comment) {
        mRepository.insertComment(comment);
    }
}

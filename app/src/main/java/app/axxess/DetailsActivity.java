package app.axxess;

import android.arch.lifecycle.ViewModelProviders;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.axxess.Utility.Utility;
import app.axxess.ViewModal.CommentViewModel;
import app.axxess.modals.Comment;
import app.axxess.modals.ImageModal;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageModal.ImageItem mImageItem;
    private ImageView mDetailedImageView;
    private CommentViewModel mCommentViewModel;
    private EditText mCommentEditText;
    private AppCompatButton mPostCommentButton;
    private LinearLayout mCommentsLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mCommentViewModel = ViewModelProviders.of(this).get(CommentViewModel.class);

        initViews();

        getDataFromIntent();
        setToolbarTitle();

        renderMultiMedia();

        fetchAllComments();

    }

    private void fetchAllComments() {
        mCommentViewModel.fetchAllCommentsById(mImageItem.id).observe(this, words -> {
                // TODO: update the list
            mCommentsLayout.removeAllViews();
            for(Comment comment : words){
                TextView tv = new TextView(this);
                tv.setText(comment.comment);
                tv.setPadding(40,10,10,10);
                tv.setTextSize(20.0f);
                mCommentsLayout.addView(tv);
            }
        });

    }

    /**
     * Render Image using Picasso
     * TODO: Can be moved to central place
     */
    private void renderMultiMedia() {
        List<ImageModal.Links> links = mImageItem.images;

        if(links!=null && links.size() > 0){
            String imagePath = links.size() == 3 ? links.get(2).link : links.size() == 2 ? links.get(1).link: links.get(0).link;
            Log.e("imagePath",imagePath);
            if(!imagePath.isEmpty() && !imagePath.endsWith(".mp4")){
                mDetailedImageView.setVisibility(View.VISIBLE);
                Picasso.get().load(imagePath).error(R.drawable.ic_launcher_background).priority(Picasso.Priority.HIGH).into(mDetailedImageView);
            }else{
                mDetailedImageView.setImageResource(R.drawable.no_video);
            }
        }
    }

    /**
     * Initialize all views required
     */
    private void initViews() {
        mDetailedImageView = findViewById(R.id.detailedImageView);
        mCommentEditText = findViewById(R.id.commentEditText);
        mPostCommentButton = findViewById(R.id.postCommentButton);
        mCommentsLayout = findViewById(R.id.commentsLayout);
        mPostCommentButton.setOnClickListener(this);
    }

    /**
     * Dynamically set title
     */
    private void setToolbarTitle() {
        getSupportActionBar().setTitle(mImageItem !=null ? mImageItem.title : "");
    }

    /**
     * Get ImageItem data from Bundle
     */
    private void getDataFromIntent() {
        if(getIntent()!=null){
            Bundle data = getIntent().getExtras();
            mImageItem = data.getParcelable(Constants.INTENT_KEYS.IMAGE_ITEM);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.postCommentButton){
            String comment = mCommentEditText.getText().toString().trim();
            if(!comment.isEmpty()){
                Comment item = new Comment();
                item.id = mImageItem.id;
                item.comment = comment;
                mCommentViewModel.insert(item);
                Utility.showToast(this,"Comment Added");
                mCommentEditText.setText("");
                Utility.hideKeyboard(this);
            }
        }
    }
}

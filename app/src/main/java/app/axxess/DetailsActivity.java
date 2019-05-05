package app.axxess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.axxess.modals.ImageModal;

public class DetailsActivity extends AppCompatActivity {

    private ImageModal.ImageItem mImageItem;
    private ImageView mDetailedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initViews();

        getDataFromIntent();
        setToolbarTitle();

        renderImage();
    }

    private void renderImage() {
        List<ImageModal.Links> links = mImageItem.images;

        if(links!=null && links.size() > 0){
            String imagePath = links.size() == 3 ? links.get(2).link : links.size() == 2 ? links.get(1).link: links.get(0).link;
            Log.e("imagePath",imagePath);
            if(!imagePath.isEmpty()){
                Picasso.get().load(imagePath).error(R.drawable.ic_launcher_background).priority(Picasso.Priority.HIGH).into(mDetailedImageView);
            }
        }
    }

    private void initViews() {
        mDetailedImageView = findViewById(R.id.detailedImageView);
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(mImageItem !=null ? mImageItem.title : "");
    }

    private void getDataFromIntent() {
        if(getIntent()!=null){
            Bundle data = getIntent().getExtras();
            mImageItem = data.getParcelable(Constants.INTENT_KEYS.IMAGE_ITEM);
        }
    }
}

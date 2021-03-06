package app.axxess.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.axxess.Constants;
import app.axxess.DetailsActivity;
import app.axxess.HomeActivity;
import app.axxess.R;
import app.axxess.modals.ImageModal;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> implements Constants.INTENT_KEYS{

    private Activity mActivity;
    private List<ImageModal.ImageItem> mImageItemList;

    public ImageListAdapter(Activity activity, List<ImageModal.ImageItem> imageItemList){
        mActivity = activity;
        mImageItemList = imageItemList;
    }

    @NonNull
    @Override
    public ImageListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_image_row,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageListAdapter.ViewHolder viewHolder, int position) {
        viewHolder.position = position;
        ImageModal.ImageItem item = mImageItemList.get(position);
        List<ImageModal.Links>  links = item.images;

        if(links!=null && links.size() > 0){
            ImageModal.Links imageLink = links.size() == 3 ? links.get(2) : links.size() == 2 ? links.get(1) : links.get(0);
            if(!imageLink.link.isEmpty() && imageLink.type.contains("image")){
                Picasso.get().load(imageLink.link).resize(200,0).error(R.drawable.ic_launcher_background)
                        .centerCrop().priority(Picasso.Priority.HIGH).into(viewHolder.resultImageView);
            }
        }

    }

    @Override
    public int getItemCount() {
        return mImageItemList.size();
    }

    public void updateList(List<ImageModal.ImageItem> imageItemList) {
        mImageItemList.clear();
        mImageItemList.addAll(imageItemList);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView resultImageView;
        int position;
        private Animation mBounceAnimation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            resultImageView = itemView.findViewById(R.id.imageView);
            resultImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v == resultImageView){
                ((HomeActivity)mActivity).clearFocus();
                Intent intent = new Intent(mActivity,DetailsActivity.class);
                intent.putExtra(IMAGE_ITEM,mImageItemList.get(position));
                mActivity.startActivity(intent);            }
        }
    }
}

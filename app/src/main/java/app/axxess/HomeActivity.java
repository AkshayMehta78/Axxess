package app.axxess;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import app.axxess.Network.APIManager;
import app.axxess.Network.SearchAPIInterface;
import app.axxess.Utility.SimpleDividerItemDecoration;
import app.axxess.Utility.Utility;
import app.axxess.adapters.ImageListAdapter;
import app.axxess.modals.ImageModal;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity implements SearchAPIInterface {

    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private ImageListAdapter mImageListAdapter;
    private APIManager mApiManager;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mApiManager = new APIManager(this);

        initViews();

        RxSearchObservable.fromView(mSearchView)
                .debounce(Constants.DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
                .filter(s-> !s.isEmpty())
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> mApiManager.handleAPICall(query), throwable -> Log.e("Result", throwable.getMessage(), throwable));

        registerForListener();

    }


    /**
     * Init all views
     */
    private void initViews() {
        mSearchView = findViewById(R.id.searchView);
        mRecyclerView = findViewById(R.id.recyclerView);
    }


    /**
     * Method to clear focus on Input
     */
    public void clearFocus() {
        mSearchView.clearFocus();
    }



    /**
     * Handle Recyclerview touch listener
     */
    @SuppressLint("ClickableViewAccessibility")
    private void registerForListener() {
        mRecyclerView.setOnTouchListener((v, event) -> {
            Utility.hideKeyboard(HomeActivity.this);
            return false;
        });
    }





    /**
     * Render recyclerview  - if adapter is null, then set else notifydatasetchanged
     * @param imageItemList
     */
    private void renderRecyclerViewData(List<ImageModal.ImageItem> imageItemList) {
        if (imageItemList.size() <= 0) {
            findViewById(R.id.noResultLayout).setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            findViewById(R.id.noResultLayout).setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
                if (mImageListAdapter == null){
                    mImageListAdapter = new ImageListAdapter(this, imageItemList);
                    mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
                    mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(10, 3));
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setItemViewCacheSize(20);
                    mRecyclerView.setDrawingCacheEnabled(true);
                    mRecyclerView.setAdapter(mImageListAdapter);
                }else{
                    mImageListAdapter.updateList(imageItemList);
                }
        }
    }

    /**
     * Handle when API callback sends Search Result
     * @param imageItemList
     */
    @Override
    public void onSearchResultSucess(List<ImageModal.ImageItem> imageItemList) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            renderRecyclerViewData(imageItemList.stream().filter(imageItem -> imageItem.images!=null).collect(Collectors.toList()));
        }else{
            renderRecyclerViewData(imageItemList);
        }
    }
}

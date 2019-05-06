package app.axxess;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import app.axxess.Network.GetDataService;
import app.axxess.Network.RetrofitNetworkInstance;
import app.axxess.Utility.SimpleDividerItemDecoration;
import app.axxess.Utility.Utility;
import app.axxess.adapters.ImageListAdapter;
import app.axxess.modals.ImageModal;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private GetDataService mGetDataService;
    private ImageListAdapter mImageListAdapter;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mGetDataService = RetrofitNetworkInstance.getRetrofitInstance().create(GetDataService.class);

        initViews();

        RxSearchObservable.fromView(mSearchView)
                .debounce(250, TimeUnit.MILLISECONDS)
                .filter(s-> !s.isEmpty())
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> handleAPICall(query), throwable -> Log.e("Result", throwable.getMessage(), throwable));

        registerForListener();

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
     * Handle API call when triggered from Observable
     * @param query
     */
    private void handleAPICall(String query) {
        Log.e("query",query);
        Call<ImageModal> response = null;
        try {
            response = mGetDataService.searchQueryResults(URLEncoder.encode(query,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.enqueue(new Callback<ImageModal>() {
            @Override
            public void onResponse(Call<ImageModal> call, Response<ImageModal> response) {
                ImageModal modal = response.body();
                List<ImageModal.ImageItem> imageItemList = modal.data;
                Log.e("imageItemList",imageItemList.size()+"");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    renderRecyclerViewData(imageItemList.stream().filter(imageItem -> imageItem.images!=null).collect(Collectors.toList()));
                }else{
                    renderRecyclerViewData(imageItemList);
                }
            }

            @Override
            public void onFailure(Call<ImageModal> call, Throwable t) {
                Log.e("failure", t.toString());
            }
        });

    }


    /**
     * Render recyclerview  - if adapter is null, then set else notifydatasetchanged
     * @param imageItemList
     */
    private void renderRecyclerViewData(List<ImageModal.ImageItem> imageItemList) {
        if(mImageListAdapter == null){
            mImageListAdapter = new ImageListAdapter(this,imageItemList);
            mRecyclerView.setLayoutManager(new GridLayoutManager(this,3,LinearLayoutManager.VERTICAL,false));
            mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(10,3));
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setItemViewCacheSize(20);
            mRecyclerView.setDrawingCacheEnabled(true);
            mRecyclerView.setAdapter(mImageListAdapter);
        } else{
            mImageListAdapter.updateList(imageItemList);
        }
    }



    /**
     * Init all views
     */
    private void initViews() {
        mSearchView = findViewById(R.id.searchView);
        mRecyclerView = findViewById(R.id.recyclerView);
    }

    public void clearFocus() {
        mSearchView.clearFocus();
    }
}

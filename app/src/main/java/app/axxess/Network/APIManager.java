package app.axxess.Network;

import android.app.Activity;
import android.os.Build;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

import app.axxess.modals.ImageModal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIManager {

    private static final String TAG = APIManager.class.getSimpleName();
    SearchAPIInterface mSearchAPIInterface;
    private GetDataService mGetDataService;


    public APIManager(SearchAPIInterface searchAPIInterface){
        mSearchAPIInterface = searchAPIInterface;
        mGetDataService = RetrofitNetworkInstance.getRetrofitInstance().create(GetDataService.class);
    }



    /**
     * Handle API call when triggered from Observable
     * @param query
     */
    public void handleAPICall(String query) {
        Call<ImageModal> response = null;
        try {
            response = mGetDataService.searchQueryResults(URLEncoder.encode(query,"UTF-8"));
            response.enqueue(new Callback<ImageModal>() {
                @Override
                public void onResponse(Call<ImageModal> call, Response<ImageModal> response) {
                    ImageModal modal = response.body();
                    List<ImageModal.ImageItem> imageItemList = modal.data;
                    mSearchAPIInterface.onSearchResultSucess(imageItemList);

                }
                @Override
                public void onFailure(Call<ImageModal> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}

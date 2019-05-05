package app.axxess.Network;

import org.json.JSONObject;

import app.axxess.modals.ImageModal;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GetDataService {

    @Headers("Authorization:Client-ID 137cda6b5008a7c")
    @GET("3/gallery/search/1/viral/")
    Call<ImageModal> searchQueryResults(@Query("q") String query);
}

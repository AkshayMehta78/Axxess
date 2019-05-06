package app.axxess.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitNetworkInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.imgur.com/";

    /**
     * Retrofit Service Singleton Instance
     * @return
     */
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
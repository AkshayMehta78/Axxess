package app.axxess.Network;

import android.util.Log;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.List;

import app.axxess.modals.ImageModal;
import io.reactivex.Observable;

public class APIService {

    public static Observable<List<ImageModal>> fetchSearchResult(String query) {
        Log.e("api",query);
        return Rx2AndroidNetworking.get("https://api.imgur.com/3/gallery/search/1?q="+query)
                .addHeaders("Authorization","Client-ID 137cda6b5008a7c")
                .build()
                .getObjectListObservable(ImageModal.class);
    }
}

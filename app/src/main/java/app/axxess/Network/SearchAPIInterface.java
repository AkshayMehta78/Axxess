package app.axxess.Network;

import java.util.List;

import app.axxess.modals.ImageModal;

public interface SearchAPIInterface {
    void onSearchResultSucess(List<ImageModal.ImageItem> result);
}

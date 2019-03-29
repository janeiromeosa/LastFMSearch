package com.example.lastfmMVVMJava.ui.albumDetails;

public interface AlbumDetailsContract {
    interface View {
        void showPlayCount(String playCount);
        void showListeners(String listeners);
        void showProgress();
        void hideProgress();
        void showError(String message);
    }
    interface Presenter {
        void getAlbumDetails(String albumName, String artistName);
        void stop();
    }
}

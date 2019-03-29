package com.example.lastfmMVVMJava.ui.albumDetails;

import com.example.lastfmMVVMJava.data.albumDetails.Album;
import com.example.lastfmMVVMJava.repo.DataSource;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AlbumDetailsPresenter implements AlbumDetailsContract.Presenter {

    private final AlbumDetailsContract.View view;
    private final DataSource lastFMRepository;
    private final CompositeDisposable compositeDisposable;

    public AlbumDetailsPresenter(AlbumDetailsContract.View view, DataSource lastFMRepository) {
        this.view = view;
        this.lastFMRepository = lastFMRepository;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getAlbumDetails(String albumName, String artistName) {
        if (albumName != null && artistName != null) {
            compositeDisposable.add(lastFMRepository.getAlbumDetails(albumName, artistName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(consumer -> view.showProgress())
                    .doOnEvent((success, failure) -> view.hideProgress())
                    .subscribe(this::handleSuccess, this::handleFailure));
        } else {
            view.showError("Invalid Album");
        }
    }

    @Override
    public void stop() {
        compositeDisposable.clear();
    }

    private void handleSuccess(Album album) {
        if (album != null) {
            view.showPlayCount(album.getPlaycount());
            view.showListeners(album.getListeners());
        }
    }

    private void handleFailure(Throwable throwable) {
        if (throwable.getMessage() != null) {
            view.showError(throwable.getMessage());
        }
    }
}

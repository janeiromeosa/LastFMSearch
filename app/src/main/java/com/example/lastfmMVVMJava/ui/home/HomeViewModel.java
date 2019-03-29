package com.example.lastfmMVVMJava.ui.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import com.example.lastfmMVVMJava.data.albumResults.Album;
import com.example.lastfmMVVMJava.repo.DataSource;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {
    private final ObservableBoolean progressObservable;

    private final MutableLiveData<List<Album>> albumsObservable;

    private final DataSource lastFMRepository;

    private final CompositeDisposable compositeDisposable;

    public HomeViewModel(DataSource lastFMRepository) {
        this.lastFMRepository = lastFMRepository;
        albumsObservable = new MutableLiveData<>();
        progressObservable = new ObservableBoolean(false);
        compositeDisposable = new CompositeDisposable();
    }

    public ObservableBoolean getProgressObservable() {
        return progressObservable;
    }

    public LiveData<List<Album>> getAlbumsObservable() {
        return albumsObservable;
    }

    public void performSearch(String albumName) {
        compositeDisposable.add(lastFMRepository.getAlbumSearchResults(albumName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(disposable -> progressObservable.set(true))
            .doOnEvent((success, failure) -> progressObservable.set(false))
            .subscribe(albumsObservable::setValue));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}

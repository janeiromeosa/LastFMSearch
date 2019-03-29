package com.example.lastfmMVVMJava.ui.albumDetails.di;

import com.example.lastfmMVVMJava.di.Repository;
import com.example.lastfmMVVMJava.repo.DataSource;
import com.example.lastfmMVVMJava.ui.albumDetails.AlbumDetailsContract;
import com.example.lastfmMVVMJava.ui.albumDetails.AlbumDetailsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class AlbumDetailsModule {
    private final AlbumDetailsContract.View view;

    public AlbumDetailsModule(AlbumDetailsContract.View view) {
        this.view = view;
    }

    @Provides
    @AlbumDetailsScope
    public AlbumDetailsContract.Presenter provideAlbumDetailsPresenter(@Repository DataSource lastFMRepository) {
        return new AlbumDetailsPresenter(view, lastFMRepository);
    }
}

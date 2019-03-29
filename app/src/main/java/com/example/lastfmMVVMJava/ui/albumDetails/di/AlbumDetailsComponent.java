package com.example.lastfmMVVMJava.ui.albumDetails.di;

import com.example.lastfmMVVMJava.di.AppComponent;
import com.example.lastfmMVVMJava.ui.albumDetails.AlbumDetailsActivity;

import dagger.Component;

@Component(modules = AlbumDetailsModule.class, dependencies = AppComponent.class)
@AlbumDetailsScope
public interface AlbumDetailsComponent {
    void inject(AlbumDetailsActivity albumDetailsActivity);
}

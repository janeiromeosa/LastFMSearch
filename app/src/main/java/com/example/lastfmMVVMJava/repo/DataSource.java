package com.example.lastfmMVVMJava.repo;

import com.example.lastfmMVVMJava.data.albumResults.Album;

import java.util.List;

import io.reactivex.Single;

public interface DataSource {
    Single<List<Album>> getAlbumSearchResults(String albumSearchQuery);
}

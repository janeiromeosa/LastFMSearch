package com.example.lastfmMVVMJava.repo;

import com.example.lastfmMVVMJava.data.albumResults.Album;

import java.util.List;

import io.reactivex.Single;

public class LastFMRepository implements DataSource {

    private final DataSource remoteDataSource;

    public LastFMRepository(DataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Single<List<Album>> getAlbumSearchResults(String albumSearchQuery) {
        return remoteDataSource.getAlbumSearchResults(albumSearchQuery);
    }
}

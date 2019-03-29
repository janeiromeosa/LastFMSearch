package com.example.lastfmMVVMJava.repo;

import com.example.lastfmMVVMJava.Constants;
import com.example.lastfmMVVMJava.data.albumDetails.AlbumDetails;
import com.example.lastfmMVVMJava.data.albumResults.Album;
import com.example.lastfmMVVMJava.data.albumResults.AlbumResultsResponse;
import com.example.lastfmMVVMJava.net.LastFMService;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class RemoteDataSource implements DataSource {

    private final LastFMService lastFMService;

    public RemoteDataSource(LastFMService lastFMService) {
        this.lastFMService = lastFMService;
    }

    @Override
    public Single<List<Album>> getAlbumSearchResults(String albumSearchQuery) {
        return lastFMService.getAlbumResults(Constants.QUERY_ALBUM_SEARCH,
                albumSearchQuery,
                Constants.API_KEY,
                Constants.RESULT_FORMAT)
                .map(result -> result.getResults().getAlbummatches().getAlbum());
    }

    @Override
    public Single<com.example.lastfmMVVMJava.data.albumDetails.Album> getAlbumDetails(String albumName, String artistName) {
        return lastFMService.getAlbumDetails(Constants.QUERY_ALBUM_INFO,
                albumName,
                artistName,
                Constants.API_KEY,
                Constants.RESULT_FORMAT)
                .map(AlbumDetails::getAlbum);
    }
}

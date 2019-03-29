package com.example.lastfmMVVMJava.net;

import com.example.lastfmMVVMJava.data.albumResults.AlbumResultsResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LastFMService {
    @GET(".")
    Single<AlbumResultsResponse> getAlbumResults(@Query("method") String methodName,
                                                     @Query("album") String albumQuery,
                                                     @Query("api_key")String apiKey,
                                                     @Query("format")String resultFormat);
}

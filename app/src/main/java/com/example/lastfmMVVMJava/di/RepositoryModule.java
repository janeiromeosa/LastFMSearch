package com.example.lastfmMVVMJava.di;

import com.example.lastfmMVVMJava.repo.DataSource;
import com.example.lastfmMVVMJava.repo.LastFMRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    @Repository
    public DataSource provideRepository(@Remote DataSource remoteDataSource) {
        return new LastFMRepository(remoteDataSource);
    }
}

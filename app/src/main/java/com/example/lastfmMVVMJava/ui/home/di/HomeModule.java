package com.example.lastfmMVVMJava.ui.home.di;

import android.arch.lifecycle.ViewModelProviders;

import com.example.lastfmMVVMJava.di.Repository;
import com.example.lastfmMVVMJava.repo.DataSource;
import com.example.lastfmMVVMJava.ui.home.HomeActivity;
import com.example.lastfmMVVMJava.ui.home.HomeViewModel;
import com.example.lastfmMVVMJava.ui.home.HomeViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    private final HomeActivity homeActivity;

    public HomeModule(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Provides
    @HomeScope
    public HomeViewModelFactory provideHomeViewModelFactory(@Repository DataSource lastFMRepository) {
        return new HomeViewModelFactory(lastFMRepository);
    }

    @Provides
    @HomeScope
    public HomeViewModel provideHomeViewModel(HomeViewModelFactory homeViewModelFactory) {
        return ViewModelProviders.of(homeActivity, homeViewModelFactory)
                .get(HomeViewModel.class);
    }
}

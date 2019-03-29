package com.example.lastfmMVVMJava.ui.home;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.lastfmMVVMJava.repo.DataSource;

public class HomeViewModelFactory implements ViewModelProvider.Factory {

    private final DataSource lastFMRepository;

    public HomeViewModelFactory(DataSource lastFMRepository) {
        this.lastFMRepository = lastFMRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(lastFMRepository);
        }
        throw new IllegalArgumentException("modelClass has to be of type "
                + HomeViewModel.class.getSimpleName());
    }
}

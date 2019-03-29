package com.example.lastfmMVVMJava.di;

import android.app.Application;

import com.example.lastfmMVVMJava.repo.DataSource;
import com.example.lastfmMVVMJava.ui.home.HomeActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = { NetworkModule.class, RepositoryModule.class })
@Singleton
public interface AppComponent {

    @Repository
    DataSource repository();

    @Component.Builder
    interface Builder {
        AppComponent build();
        @BindsInstance Builder application(Application application);
    }
}

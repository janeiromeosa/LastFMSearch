package com.example.lastfmMVVMJava.ui.home.di;


import com.example.lastfmMVVMJava.di.AppComponent;
import com.example.lastfmMVVMJava.ui.home.HomeActivity;

import dagger.Component;

@Component(modules = HomeModule.class, dependencies = AppComponent.class)
@HomeScope
public interface HomeComponent {
    void inject(HomeActivity homeActivity);
}

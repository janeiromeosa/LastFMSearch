package com.example.lastfmMVVMJava.ui.home;

import android.app.SearchManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;

import com.example.lastfmMVVMJava.MyApplication;
import com.example.lastfmMVVMJava.R;
import com.example.lastfmMVVMJava.databinding.ActivityMainBinding;
import com.example.lastfmMVVMJava.ui.home.di.DaggerHomeComponent;
import com.example.lastfmMVVMJava.ui.home.di.HomeModule;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity {

    @Inject
    HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_main);
        DaggerHomeComponent.builder()
                .appComponent(((MyApplication)getApplication()).getAppComponent())
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);

        binding.setProgressVisibility(homeViewModel.getProgressObservable());

        AlbumsAdapter albumsAdapter = new AlbumsAdapter();
        binding.rvResults.setLayoutManager(new LinearLayoutManager(this));
        binding.rvResults.setAdapter(albumsAdapter);

        homeViewModel.getAlbumsObservable().observe(this, albumsAdapter::setData);

        homeViewModel.performSearch("Test");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        return true;
    }
}

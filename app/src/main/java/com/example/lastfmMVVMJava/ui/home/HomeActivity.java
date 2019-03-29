package com.example.lastfmMVVMJava.ui.home;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.widget.Toast;

import com.example.lastfmMVVMJava.Constants;
import com.example.lastfmMVVMJava.MyApplication;
import com.example.lastfmMVVMJava.R;
import com.example.lastfmMVVMJava.data.albumResults.Album;
import com.example.lastfmMVVMJava.databinding.ActivityMainBinding;
import com.example.lastfmMVVMJava.ui.albumDetails.AlbumDetailsActivity;
import com.example.lastfmMVVMJava.ui.home.di.DaggerHomeComponent;
import com.example.lastfmMVVMJava.ui.home.di.HomeModule;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity implements OnAlbumSelectedListener {

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

        AlbumsAdapter albumsAdapter = new AlbumsAdapter(this);
        binding.rvResults.setLayoutManager(new LinearLayoutManager(this));
        binding.rvResults.setAdapter(albumsAdapter);

        homeViewModel.getAlbumsObservable().observe(this, albumsAdapter::setData);



    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_SEARCH)) {
            homeViewModel.performSearch(intent.getStringExtra(SearchManager.QUERY));
        }
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

    @Override
    public void onAlbumSelected(Album album) {
        Intent intent = new Intent(this, AlbumDetailsActivity.class);
        intent.putExtra(Constants.KEY_ALBUM_NAME, album.getName());
        intent.putExtra(Constants.KEY_ARTIST_NAME, album.getArtist());
        startActivity(intent);
    }
}

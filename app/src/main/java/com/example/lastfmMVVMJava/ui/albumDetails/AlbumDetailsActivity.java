package com.example.lastfmMVVMJava.ui.albumDetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lastfmMVVMJava.Constants;
import com.example.lastfmMVVMJava.MyApplication;
import com.example.lastfmMVVMJava.R;
import com.example.lastfmMVVMJava.ui.albumDetails.di.AlbumDetailsModule;
import com.example.lastfmMVVMJava.ui.albumDetails.di.DaggerAlbumDetailsComponent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumDetailsActivity extends AppCompatActivity implements AlbumDetailsContract.View {

    @Inject
    AlbumDetailsContract.Presenter presenter;

    @BindView(R.id.tvPlayCount)
    TextView tvPlayCount;

    @BindView(R.id.tvListeners)
    TextView tvListeners;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);
        ButterKnife.bind(this);
        DaggerAlbumDetailsComponent.builder()
                .appComponent(((MyApplication)getApplication()).getAppComponent())
                .albumDetailsModule(new AlbumDetailsModule(this))
                .build()
                .inject(this);

        String albumName = getIntent().getStringExtra(Constants.KEY_ALBUM_NAME);
        String artistName = getIntent().getStringExtra(Constants.KEY_ARTIST_NAME);

        presenter.getAlbumDetails(albumName, artistName);
    }

    @Override
    public void showPlayCount(String playCount) {
        tvPlayCount.setText(playCount);
    }

    @Override
    public void showListeners(String listeners) {
        tvListeners.setText(listeners);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

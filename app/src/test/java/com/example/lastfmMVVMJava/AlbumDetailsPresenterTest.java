package com.example.lastfmMVVMJava;

import android.support.annotation.NonNull;

import com.example.lastfmMVVMJava.data.albumDetails.Album;
import com.example.lastfmMVVMJava.repo.DataSource;
import com.example.lastfmMVVMJava.ui.albumDetails.AlbumDetailsContract;
import com.example.lastfmMVVMJava.ui.albumDetails.AlbumDetailsPresenter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.SocketException;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AlbumDetailsPresenterTest {

    @Mock
    private AlbumDetailsContract.View view;

    @Mock
    private DataSource repository;

    private AlbumDetailsContract.Presenter presenter;

    private Album album = new Album();

    @BeforeClass
    public static void setUpRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run, false);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }

    @Before
    public void setup() {
        album.setPlaycount("1000");
        album.setListeners("100");

        presenter = new AlbumDetailsPresenter(view, repository);
    }

    @Test
    public void test_repository_hasValidData() {
        //Given
        when(repository.getAlbumDetails(anyString(), anyString()))
                .thenReturn(Single.just(album));
        //When
        presenter.getAlbumDetails("Test", "Test");
        //Then
        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showProgress();
        inOrder.verify(view).hideProgress();
        inOrder.verify(view).showPlayCount("1000");
        inOrder.verify(view).showListeners("100");
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void test_repository_hasError() {
        //Given
        when(repository.getAlbumDetails(anyString(), anyString()))
                .thenReturn(Single.error(new SocketException("Some Error")));

        //When
        presenter.getAlbumDetails("Test", "Test");

        //Then
        verify(view).showProgress();
        verify(view).hideProgress();
        verify(view).showError("Some Error");
        verifyNoMoreInteractions(view);
    }

    @Test
    public void test_album_isNull() {
        //Given

        //When
        presenter.getAlbumDetails(null, "Test");

        //Then
        verify(repository, times(0)).getAlbumDetails(anyString(), anyString());
        verify(view).showError("Invalid Album");
    }
}

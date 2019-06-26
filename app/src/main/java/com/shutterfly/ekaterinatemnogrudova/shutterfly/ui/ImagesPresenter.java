package com.shutterfly.ekaterinatemnogrudova.shutterfly.ui;

import android.util.Log;
import com.shutterfly.ekaterinatemnogrudova.shutterfly.models.ImagesResponse;
import com.shutterfly.ekaterinatemnogrudova.shutterfly.pixabayApi.PixabayService;
import com.shutterfly.ekaterinatemnogrudova.shutterfly.utils.IScheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import static com.shutterfly.ekaterinatemnogrudova.shutterfly.utils.Constants.API_KEY;
import static com.shutterfly.ekaterinatemnogrudova.shutterfly.utils.Constants.DEFAULT_PER_PAGES;

public class ImagesPresenter implements ImagesContract.Presenter {
    private ImagesContract.View mView;
    private Disposable mDisposable;
    private IScheduler mScheduler;
    private PixabayService mPixabayService;

    public ImagesPresenter(ImagesContract.View view, PixabayService pixabayService, IScheduler scheduler) {
        mView = view;
        mView.setPresenter(this);
        mScheduler = scheduler;
        mPixabayService = pixabayService;
    }

    @Override
    public void unsubscribe() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    public void getImages(String query, String page) {
        mDisposable = mPixabayService.getImages(API_KEY, query, page, DEFAULT_PER_PAGES)
                    .subscribeOn(mScheduler.io())
                    .observeOn(mScheduler.ui()).subscribeWith(new DisposableObserver<ImagesResponse>() {
            @Override
            public void onNext(ImagesResponse response) {
                mView.getImagesSuccess(response.getHits());
            }

            @Override
            public void onError(Throwable e) {
                Log.d("NETWORK ERROR", "getImages() loading error");
            }

            @Override
            public void onComplete() {
            }
        });
    }
}

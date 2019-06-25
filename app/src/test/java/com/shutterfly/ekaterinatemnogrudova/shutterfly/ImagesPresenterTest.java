package com.shutterfly.ekaterinatemnogrudova.shutterfly;

import com.shutterfly.ekaterinatemnogrudova.shutterfly.models.Image;
import com.shutterfly.ekaterinatemnogrudova.shutterfly.models.ImagesResponse;
import com.shutterfly.ekaterinatemnogrudova.shutterfly.pixabayApi.PixabayService;
import com.shutterfly.ekaterinatemnogrudova.shutterfly.ui.ImagesContract;
import com.shutterfly.ekaterinatemnogrudova.shutterfly.ui.ImagesPresenter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static com.shutterfly.ekaterinatemnogrudova.shutterfly.utils.Constants.API_KEY;
import static com.shutterfly.ekaterinatemnogrudova.shutterfly.utils.Constants.DEFAULT_PER_PAGES;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ImagesPresenterTest {
    private ImagesPresenter presenter;
    private TestSchedulerProvider testSchedulerProvider;
    private TestScheduler testScheduler;
    @Mock
    private ImagesContract.View mView;
    @Mock
    private PixabayService pixabayService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);// required for the "@Mock" annotations
        testScheduler = new TestScheduler();
        testSchedulerProvider = new TestSchedulerProvider(testScheduler);
        presenter  = new ImagesPresenter(mView, pixabayService, testSchedulerProvider);
    }

    @Test
    public void handleGetImagesResponse_Success(){
        ImagesResponse mockedResponse = new ImagesResponse();
        List<Image> articles = new ArrayList<>();
        Image image = new Image();
        image.setId("4290922");
        image.setPreviewUrl("https://cdn.pixabay.com/photo/2019/06/22/06/31/sky-4290922_150.jpg");
        articles.add(image);
        mockedResponse.setHits(articles);
        Mockito.when(pixabayService.getImages(API_KEY, "", "", DEFAULT_PER_PAGES))
                .thenReturn(Observable.just(mockedResponse));
        presenter.getImages("", "");
        testScheduler.triggerActions();
        verify(mView).getImagesSuccess(mockedResponse.getHits());
    }
}
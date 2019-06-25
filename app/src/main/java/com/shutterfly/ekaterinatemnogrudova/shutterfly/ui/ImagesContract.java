package com.shutterfly.ekaterinatemnogrudova.shutterfly.ui;

import com.shutterfly.ekaterinatemnogrudova.shutterfly.models.Image;
import com.shutterfly.ekaterinatemnogrudova.shutterfly.utils.BaseView;
import java.util.List;

public class ImagesContract {
    public interface View extends BaseView<Presenter> {
        void getImagesSuccess(List<Image> result);
    }

    interface Presenter  {
        void getImages(String query, String page);
        void unsubscribe();
    }
}

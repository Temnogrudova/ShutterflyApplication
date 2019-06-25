package com.shutterfly.ekaterinatemnogrudova.shutterfly.pixabayApi;

import com.shutterfly.ekaterinatemnogrudova.shutterfly.models.ImagesResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PixabayApi {
    @GET("api")
    Observable<ImagesResponse> getImages(@Query("key") String key, @Query("q") String query, @Query("page") String page, @Query("per_page") String per_page);
}
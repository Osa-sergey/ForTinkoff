package com.serg.ovchinnikov.Api;

import com.serg.ovchinnikov.pojo.Gifs;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GifApi {

    @GET("/{section}/{page}?json=true")
    public Call<Gifs> getGifList(@Path("section") String section, @Path("page") int page);

}

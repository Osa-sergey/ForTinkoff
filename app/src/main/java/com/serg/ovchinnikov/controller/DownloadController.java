package com.serg.ovchinnikov.controller;

import android.util.Log;

import com.bumptech.glide.Glide;
import com.serg.ovchinnikov.Api.GifApi;
import com.serg.ovchinnikov.CategoryGifFragment;
import com.serg.ovchinnikov.pojo.Gif;
import com.serg.ovchinnikov.pojo.Gifs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadController {

    private GifApi api;

    public DownloadController(GifApi api){
        this.api = api;
    }

    public void gifUpdate(CategoryGifFragment section, int category) {
        if(section.getGifsBefore()<section.getGifsArr().size()){
            //отрисовываем gif с подписью
            drawGif(section);
        }
        // для плавности будем начинать подгрузку новых картинок заранее
        if(section.getGifsBefore()+3>section.getGifsArr().size()){
            // грузим из интернета новую страницу
            Call<Gifs> gifsCall = load(section,category);
            gifsCall.enqueue(new Callback<Gifs>() {
                @Override
                public void onResponse(Call<Gifs> call, Response<Gifs> response) {
                    if(response.isSuccessful()) {
                        section.increasePageCounter();
                        section.appendGifsArr(response.body().getResult());
                    }
                }

                @Override
                public void onFailure(Call<Gifs> call, Throwable t) {

                }
            });
        }
    }

    // Грузим первый набор фото
    public void firstLoad(CategoryGifFragment section, int category) {
        // грузим из интернета новую страницу
        Call<Gifs> gifsCall = load(section,category);
        gifsCall.enqueue(new Callback<Gifs>() {
            @Override
            public void onResponse(Call<Gifs> call, Response<Gifs> response) {
                if(response.isSuccessful()) {
                    section.increasePageCounter();
                    section.appendGifsArr(response.body().getResult());
                    //отрисовываем gif с подписью
                    drawGif(section);
                }
            }

            @Override
            public void onFailure(Call<Gifs> call, Throwable t) {

            }
        });
    }

    private void drawGif(CategoryGifFragment section){
        Glide.with(section.getContext())
                .asGif()
                .load(section.getGifsArr().get(section.getGifsBefore()).getGifURL())
                .into(section.getGifView());

        section.getTitleView()
                .setText(section.getGifsArr().get(section.getGifsBefore()).getDescription());
    }

    private Call<Gifs> load(CategoryGifFragment section, int category){
        int pageNumber = section.getPageCounter();
        Call<Gifs> gifsCall;
        switch (category){
            case 0:
                gifsCall = api.getGifList("latest",pageNumber);
                break;
            case 1:
                gifsCall = api.getGifList("hot",pageNumber);
                break;
            case 2:
                gifsCall = api.getGifList("top",pageNumber);
                break;
            default:
                gifsCall = api.getGifList("err",pageNumber);
        }
        return gifsCall;
    }
}

package com.serg.ovchinnikov.controller;

import android.content.res.Resources;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.serg.ovchinnikov.Api.GifApi;
import com.serg.ovchinnikov.CategoryGifFragment;
import com.serg.ovchinnikov.R;
import com.serg.ovchinnikov.pojo.Gif;
import com.serg.ovchinnikov.pojo.Gifs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadController {

    private GifApi api;
    private Resources res;
    private String pcgName;

    public DownloadController(GifApi api, Resources res,String pcgName){
        this.api = api;
        this.res = res;
        this.pcgName = pcgName;
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
        //Если у нас не загрузился из интернета json или в нем не было элементов (категория пустая)
        if (section.getGifsArr().size() == 0){
            //Чтобы у нас не увеличивался счетчик страниц
            section.setPageCounterZero();

            //блокируем кнопку вперед
            section.blockBtnForward();
            //настройка внешнего вида
            section.getTitleView()
                    .setText(res.getText(R.string.empty_category));
            section.getTitleView().setTextColor(res.getColor(R.color.white));
            section.getGifView().setImageResource(res.getIdentifier("ic_empty","drawable",pcgName));
            section.getCardView().setBackgroundColor(res.getColor(R.color.gray));
            return;
        }
        //разблочим кнопку вперед
        section.unBlockBtnForward();

        Log.i("drawGif","url: "+section.getGifsArr().get(section.getGifsBefore()).getGifURL());
        section.getCardView().setBackgroundColor(res.getColor(R.color.white));
        section.getTitleView().setTextColor(res.getColor(R.color.black));
        Glide.with(section.getContext())
                    .asGif()
                    .load(section.getGifsArr().get(section.getGifsBefore()).getGifURL())
                    .into(section.getGifView());

            section.getTitleView()
                    .setText(section.getGifsArr().get(section.getGifsBefore()).getDescription());
    }

    private Call<Gifs> load(CategoryGifFragment section, int category){
        int pageNumber = section.getPageCounter();
        Log.i("load_page", "номер загружаемой страницы: " + pageNumber + " категория: " + category);
        Call<Gifs> gifsCall;
        switch (category){
            case 0:
                gifsCall = api.getGifList("latest",pageNumber);
                break;
            case 1:
                gifsCall = api.getGifList("top",pageNumber);
                break;
            case 2:
                gifsCall = api.getGifList("hot",pageNumber);
                break;
            default:
                gifsCall = api.getGifList("err",pageNumber);
        }
        return gifsCall;
    }
}

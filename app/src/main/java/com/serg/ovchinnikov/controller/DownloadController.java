package com.serg.ovchinnikov.controller;

import android.content.res.Resources;;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.serg.ovchinnikov.Api.GifApi;
import com.serg.ovchinnikov.CategoryGifFragment;
import com.serg.ovchinnikov.R;
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
                        Log.i("preloadJson","success");
                        section.increasePageCounter();
                        section.appendGifsArr(response.body().getResult());
                    }else {
                        Toast.makeText(section.getActivity().getApplicationContext(),res.getText(R.string.server_connection_problem),Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Gifs> call, Throwable t) {
                    Toast.makeText(section.getActivity().getApplicationContext(),res.getText(R.string.internet_connection_problem),Toast.LENGTH_LONG).show();
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
                    Log.i("firstLoadJson","success");
                    section.increasePageCounter();
                    section.appendGifsArr(response.body().getResult());
                    //отрисовываем gif с подписью
                    drawGif(section);
                }else {
                    Toast.makeText(section.getActivity().getApplicationContext(),res.getText(R.string.server_connection_problem),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Gifs> call, Throwable t) {
                Toast.makeText(section.getActivity().getApplicationContext(),res.getText(R.string.internet_connection_problem),Toast.LENGTH_LONG).show();
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

        section.getTitleView().setTextColor(res.getColor(R.color.black));
        section.getCardView().setBackgroundColor(res.getColor(R.color.white));

        Log.i("drawGif","url: "+section.getGifsArr().get(section.getGifsBefore()).getGifURL());

        Glide.with(section.getContext())
                    .asGif()
                    .load(section.getGifsArr().get(section.getGifsBefore()).getGifURL())
                    .placeholder(R.drawable.ic_downloading)
                    .transition(DrawableTransitionOptions.withCrossFade(300))
                    .error(R.drawable.ic_download_error)
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

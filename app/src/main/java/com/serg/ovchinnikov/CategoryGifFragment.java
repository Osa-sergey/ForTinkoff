package com.serg.ovchinnikov;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.serg.ovchinnikov.pojo.Gif;

import java.util.ArrayList;

public class CategoryGifFragment extends Fragment {

    private int gifsBefore = 0;
    private int pageCounter = 0;
    private ImageView gif;
    private TextView title;
    private ArrayList<Gif> gifs;
    private MaterialCardView card;

    public CategoryGifFragment(){
        gifs = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_gif_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gif = view.findViewById(R.id.gif);
        title = view.findViewById(R.id.title);
        card = view.findViewById(R.id.card);
    }

    public int getGifsBefore() {
        return gifsBefore;
    }

    public void increaseGifsBefore(){
        this.gifsBefore++;
    }

    public void decreaseGifsBefore(){
        this.gifsBefore--;
    }

    public ArrayList<Gif> getGifsArr() {
        return gifs;
    }

    public void appendGifsArr(ArrayList<Gif> gifs) {
        this.gifs.addAll(gifs);
    }

    public int getPageCounter() {
        return pageCounter;
    }

    public void increasePageCounter() {
        this.pageCounter++;
    }

    public void setPageCounterZero(){
        this.pageCounter = 0;
    }

    public ImageView getGifView() {
        return gif;
    }

    public TextView getTitleView() {
        return title;
    }

    public MaterialCardView getCardView() {
        return card;
    }

    public void blockBtnForward() {
        MaterialButton btn =((MainActivity)getActivity()).getNext();
        btn.setClickable(false);
        btn.setBackgroundColor(getResources().getColor(R.color.gray));
    }

    public void unBlockBtnForward() {
        MaterialButton btn =((MainActivity)getActivity()).getNext();
        btn.setClickable(true);
        btn.setBackgroundColor(getResources().getColor(R.color.black));
    }
}

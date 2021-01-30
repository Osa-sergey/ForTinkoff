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

public class CategoryGifFragment extends Fragment {

    public final int type;
    private int gifsBefore = -1;
    private ImageView gif;
    private TextView title;

    public CategoryGifFragment(int type){
        this.type = type;
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
    }

    public void setTitleText(String str) {
        this.title.setText(str);
    }

    public int getGifsBefore() {
        return gifsBefore;
    }

    public void setGifsBefore(int gifsBefore) {
        this.gifsBefore = gifsBefore;
    }

    public void increaseGifsBefore(){
        this.gifsBefore++;
    }

    public void decreaseGifsBefore(){
        this.gifsBefore--;
    }
}

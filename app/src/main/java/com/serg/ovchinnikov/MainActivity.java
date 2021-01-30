
package com.serg.ovchinnikov;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends FragmentActivity {

    private ViewPager2 viewPager;
    private StateAdapter adapter;
    private MaterialButton prev, next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text = findViewById(R.id.text);
        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/7604.ttf");
        text.setTypeface(font);

        prev = findViewById(R.id.back);
        next = findViewById(R.id.forward);

        adapter = new StateAdapter(this);
        viewPager = findViewById(R.id.pager);

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        Resources res = getResources();

        new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText(res.getText(R.string.fr1));
                        break;
                    case 1:
                        tab.setText(res.getText(R.string.fr2));
                        break;
                    case 2:
                        tab.setText(res.getText(R.string.fr3));
                        break;
                    default:
                        tab.setText(res.getText(R.string.frn));
                }
            }
        }).attach();

        // Изначально кнопка неактивна
        prev.setClickable(false);
        prev.setBackgroundColor(getResources().getColor(R.color.gray));

        prev.setOnClickListener(view -> {
            CategoryGifFragment page = (CategoryGifFragment)
                    adapter.getFragment(viewPager.getCurrentItem());
            if(page != null) {
                page.decreaseGifsBefore();
                //Если gif до не осталось, то делаем неактивной кнопку назад
                Log.i("btn_prev","номер: "+page.type+" кол-во: "+page.getGifsBefore());
                if(page.getGifsBefore() == -1){
                    prev.setClickable(false);
                    prev.setBackgroundColor(getResources().getColor(R.color.gray));
                }


            }
        });

        next.setOnClickListener(view -> {
           CategoryGifFragment page = (CategoryGifFragment)
                   adapter.getFragment(viewPager.getCurrentItem());
           if(page != null) {
               page.increaseGifsBefore();
               //Если у нас есть хоть одна gif до, то делаем кнопку назад активной
               Log.i("btn_next","номер: "+page.type+" кол-во: "+page.getGifsBefore());
               if(page.getGifsBefore() > -1){
                   prev.setClickable(true);
                   prev.setBackgroundColor(getResources().getColor(R.color.black));
               }


           }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                CategoryGifFragment page = (CategoryGifFragment)
                        adapter.getFragment(viewPager.getCurrentItem());
                // При переходе в другую категорию проверяем надо ли блокировать кнопку назад
                Log.i("change_page","номер: "+page.type+" кол-во: "+page.getGifsBefore());
                if(page.getGifsBefore() == -1){
                    prev.setClickable(false);
                    prev.setBackgroundColor(getResources().getColor(R.color.gray));
                }else{
                    prev.setClickable(true);
                    prev.setBackgroundColor(getResources().getColor(R.color.black));
                }
            }
        });
    }
}
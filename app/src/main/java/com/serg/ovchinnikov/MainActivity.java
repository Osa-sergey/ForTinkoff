package com.serg.ovchinnikov;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends FragmentActivity {

    private ViewPager2 viewPager;
    private StateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}
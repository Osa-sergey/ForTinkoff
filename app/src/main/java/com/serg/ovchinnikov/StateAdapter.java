package com.serg.ovchinnikov;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.HashMap;

public class StateAdapter extends FragmentStateAdapter {

    //чтобы потом вызывать метод у конкретного фрагмента на обновление gif
    private HashMap<Integer,Fragment> fragments;

    public StateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments = new HashMap<>();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new CategoryGifFragment(position);
        fragments.put(position,fragment);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public Fragment getFragment(int pos){
        return fragments.get(pos);
    }
}

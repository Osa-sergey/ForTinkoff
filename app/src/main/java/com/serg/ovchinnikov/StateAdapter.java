package com.serg.ovchinnikov;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class StateAdapter extends FragmentStateAdapter {

    public StateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new CategoryGifFragment(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

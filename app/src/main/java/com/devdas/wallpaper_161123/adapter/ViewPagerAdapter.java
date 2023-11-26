package com.devdas.wallpaper_161123.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.devdas.wallpaper_161123.activity.MainActivity;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return MainFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return MainActivity.category.length;
    }
}

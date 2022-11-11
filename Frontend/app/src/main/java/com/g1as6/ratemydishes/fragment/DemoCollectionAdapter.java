package com.g1as6.ratemydishes.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class DemoCollectionAdapter extends FragmentStateAdapter {
    private int count;

    public DemoCollectionAdapter(FragmentActivity fragment, int count) {
        super(fragment);
        this.count = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment = new DemoObjectFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public int getItemCount() {
        return count;
    }
}

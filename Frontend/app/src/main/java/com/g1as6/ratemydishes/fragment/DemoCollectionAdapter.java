package com.g1as6.ratemydishes.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.g1as6.ratemydishes.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class DemoCollectionAdapter extends FragmentStateAdapter {
    // Brug, this interface makes this so much harder
    private JSONArray dataSend;
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
        View view = fragment.getView();

        args.putInt(DemoObjectFragment.ARG_OBJECT, position);
        fragment.setArguments(args);
        ((DemoObjectFragment) fragment).json = dataSend;

        return fragment;
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public void setDataSend(JSONArray dataSend) {
        this.dataSend = dataSend;
    }
}

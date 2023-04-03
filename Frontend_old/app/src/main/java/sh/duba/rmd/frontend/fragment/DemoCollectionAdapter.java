package sh.duba.rmd.frontend.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.json.JSONArray;

public class DemoCollectionAdapter extends FragmentStateAdapter {
    // Brug, this interface makes this so much harder
    private JSONArray menus;
    private String menuSlug;
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

        args.putInt(DemoObjectFragment.ARG_OBJECT, position);
        args.putString("menus", menus.toString());
        args.putString("slug", menuSlug);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public void setMenus(JSONArray menus) {
        this.menus = menus;
    }

    public void setMenuSlug(String slug) { this.menuSlug = slug; }
}

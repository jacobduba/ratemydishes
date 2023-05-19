package sh.duba.rmd.frontend.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.g1as6.ratemydishes.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// Instances of this class are fragments representing a single
// object in our collection.
public class DemoObjectFragment extends Fragment {
    public static final String ARG_OBJECT = "object";
    public static JSONArray menus;

    private int position;

    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        position = args.getInt(ARG_OBJECT);

        View view = inflater.inflate(R.layout.menu_fragment, container, false);
        try {
            menus = reconstructJSON(args.getString("menus"));
            JSONObject tmp = menus.getJSONObject(position);
            JSONArray section = tmp.getJSONArray("Section");

            mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
            mLayoutManager = new LinearLayoutManager(getActivity());
            mAdapter = new CustomAdapter(section);
            mAdapter.setMenuSlug(args.getString("slug"));

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(mLayoutManager);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        JSONObject tmp = null;
        try {
            tmp = menus.getJSONObject(position);
            JSONArray section = tmp.getJSONArray("Section");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONArray reconstructJSON(String in) throws JSONException {
        return new JSONArray(in);
    }
}

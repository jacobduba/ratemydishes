package com.g1as6.ratemydishes.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.g1as6.ratemydishes.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// Instances of this class are fragments representing a single
// object in our collection.
public class DemoObjectFragment extends Fragment {
    public static final String ARG_OBJECT = "object";
    public static JSONArray menus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        try {
            menus = reconstructJSON(args.getString("menus"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return inflater.inflate(R.layout.menu_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        int position = args.getInt(ARG_OBJECT);
        JSONObject tmp = null;
        try {
            tmp = menus.getJSONObject(position);
            JSONArray section = tmp.getJSONArray("Section");

            ((TextView) view.findViewById(R.id.fragText2)).setText(section.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ((TextView) view.findViewById(R.id.fragText)).setText(Integer.toString(args.getInt(ARG_OBJECT)));

    }

    private JSONArray reconstructJSON(String in) throws JSONException {
        return new JSONArray(in);
    }
}

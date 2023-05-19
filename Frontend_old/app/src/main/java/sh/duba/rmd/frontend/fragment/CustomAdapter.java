package sh.duba.rmd.frontend.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.g1as6.ratemydishes.R;

import sh.duba.rmd.frontend.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    private JSONArray localDataSet;
    private String menuSlug;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ConstraintLayout layout;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView = (TextView) view.findViewById(R.id.secTitle);
            layout = (ConstraintLayout) view.findViewById(R.id.foodParent);
        }

        public TextView getTitle() {
            return textView;
        }
        public ConstraintLayout getLayout() { return layout; }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet JSONArray containing the data to populate views to be used
     * by RecyclerView.
     */
    public CustomAdapter(JSONArray dataSet) throws JSONException {
        // Damn I really should have complained about how the JSON is received
        JSONObject tmp = dataSet.getJSONObject(0);
        localDataSet = tmp.getJSONArray("array");
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.food_section, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        try {
            JSONObject section = localDataSet.getJSONObject(position);
            JSONArray kw = section.getJSONArray("Menu-Display");

            // For the first release, only display entrees
            // This can be something configured later
            JSONObject shit = kw.getJSONObject(1);
            JSONArray foods = shit.getJSONArray("Food-Type");

            viewHolder.getTitle().setText(kw.getString(0));

            populateViewHolder(viewHolder, foods);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length();
    }

    // Populate a viewholder with buttons for each food
    private void populateViewHolder(ViewHolder view, JSONArray foods) throws JSONException {
        int last = view.getTitle().getId();
        for(int i = 1; i < foods.length(); i++) {
            // Definitions
            final ConstraintLayout layout = view.getLayout();
            final ConstraintSet set = new ConstraintSet();
            final Button btn = new Button(view.getLayout().getContext());
            DisplayMetrics displayMetrics = new DisplayMetrics();

            ((Activity) layout.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            JSONObject foodFake = (JSONObject) foods.getJSONObject(i);
            JSONArray foodReal = (JSONArray) foodFake.getJSONArray("Food-Item");
            JSONObject foodName = foodReal.getJSONObject(0);

            btn.setId(View.generateViewId());
            btn.setText(foodName.getString("name"));
            btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            btn.setTextSize(24);
            btn.setWidth(displayMetrics.widthPixels);
            btn.setBackgroundColor(0xFFF4F4D4);
            btn.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    Context context = v.getContext();
                    Intent intent = new Intent(context, Review.class);

                    intent.putExtra("food", foodReal.toString());
                    intent.putExtra("slug", menuSlug);
                    context.startActivity(intent);
                }
            });

            set.constrainHeight(btn.getId(), ConstraintSet.WRAP_CONTENT);
            set.constrainWidth(btn.getId(), ConstraintSet.WRAP_CONTENT);

            layout.addView(btn,0);

            set.clone(layout);
            set.connect(btn.getId(), ConstraintSet.TOP, last, ConstraintSet.BOTTOM);
            set.applyTo(layout);

            last = btn.getId();
        }
    }

    public void setMenuSlug(String s) { this.menuSlug = s; }
}
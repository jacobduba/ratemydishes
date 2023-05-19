package sh.duba.rmd.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import sh.duba.rmd.frontend.app.AppController;
import sh.duba.rmd.frontend.app.AppVars;
import sh.duba.rmd.frontend.fragment.DemoCollectionAdapter;

import com.g1as6.ratemydishes.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuList extends AppCompatActivity {

    private ImageButton backToDining;
    private String url = AppVars.API_URL + "/menu/get-menu/";
    private TabLayout tabs;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String u = url;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_list);

        backToDining = findViewById(R.id.backToDining);
        tabs= findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.pager);
        AppCompatActivity app = this;

        backToDining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MenuList.this, WelcomePage.class);
                startActivity(intent);
            }
        });

        Bundle extras = getIntent().getExtras();
        JSONObject slug = reconstructJSON(extras.getString("slug"));

        try {
            String s = slug.getString("slug");

            u = u + s;
        } catch (JSONException e) { /* Bro this has literally never failed */   }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, u, slug, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray menus = response.getJSONArray("menu");

                            DemoCollectionAdapter demoCollectionAdapter = new DemoCollectionAdapter(app, menus.length());
                            viewPager.setAdapter(demoCollectionAdapter);

                            new TabLayoutMediator(tabs, viewPager, (TabLayout.Tab tab, int position) -> {
                            }).attach();

                            demoCollectionAdapter.setMenus(menus);
                            demoCollectionAdapter.setMenuSlug(extras.getString("slug"));
                            for (int i = 0; i < menus.length(); i++){
                                JSONObject individualMenu =  menus.getJSONObject(i);
                                JSONArray section = individualMenu.getJSONArray("Section");
                                String title = section.getJSONObject(0).getString("title");

                                tabs.getTabAt(i).setText(title);

                                demoCollectionAdapter.createFragment(i);
                            }

                        } catch (JSONException e){ }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        System.out.print("Sad Test");
                    }
                }) {
        };

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "tag_json_obj");
    }

    public JSONObject reconstructJSON(String s){
        try{
            JSONObject slug = new JSONObject();
            slug.put("slug", s);

            return slug;
        }catch (JSONException e){
            return null;
        }
    }
}

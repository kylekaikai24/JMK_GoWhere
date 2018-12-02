package com.example.jmk2018.jmk_gowhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.algolia.instantsearch.helpers.InstantSearch;
import com.algolia.instantsearch.helpers.Searcher;
import com.algolia.instantsearch.ui.views.Hits;
import com.algolia.instantsearch.utils.ItemClickSupport;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchItemActivity extends AppCompatActivity {

    private static final String ALGOLIA_APP_ID = "SAA6306HQC";
    private static final String ALGOLIA_SEARCH_API_KEY = "9291eea64bd5e57769451aa5f72f560f";
    private static final String ALGOLIA_INDEX_NAME = "Database";

    private Searcher searcher;
    private Hits hits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);

        searcher = Searcher.create(ALGOLIA_APP_ID, ALGOLIA_SEARCH_API_KEY, ALGOLIA_INDEX_NAME);
        InstantSearch helper = new InstantSearch(this, searcher);
        helper.search();

        hits = findViewById(R.id.hits);
        hits.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, int position, View v) {
                JSONObject hit = hits.get(position);

                String name = null;// Or "foo" if you want the "foo" attribute
                String location = null;
                String category = null;
                String imageUrl = null;
                String phoneNumber = null;
                String link = null;
                String address= null;
                Double latitude = null;
                Double longitude = null;

                try {
                    name = hit.getString("name");
                    location = hit.getString("location");
                    category = hit.getString("category");
                    imageUrl = hit.getString("imageUrl");
                    phoneNumber = hit.getString("phone");
                    link = hit.getString("link");
                    address = hit.getString("address");
                    latitude = hit.getDouble("latitude");
                    longitude = hit.getDouble("longitude");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent hitsTabbedIntent = new Intent (SearchItemActivity.this, CardViewTabbed.class);
                hitsTabbedIntent.putExtra("Name",name);
                hitsTabbedIntent.putExtra("Location",location);
                hitsTabbedIntent.putExtra("Category",category);
                hitsTabbedIntent.putExtra("ImageUrl",imageUrl);
                hitsTabbedIntent.putExtra("Phone Number",phoneNumber);
                hitsTabbedIntent.putExtra("Link",link);
                hitsTabbedIntent.putExtra("Address",address);
                hitsTabbedIntent.putExtra("Latitude",latitude);
                hitsTabbedIntent.putExtra("Longitude",longitude);
                startActivity(hitsTabbedIntent);
            }
        });




    }

    @Override
    protected void onDestroy() {
        searcher.destroy();
        super.onDestroy();
    }


}

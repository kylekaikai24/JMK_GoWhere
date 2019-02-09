package com.example.jmk2018.jmk_gowhere;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.Update;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.algolia.instantsearch.helpers.InstantSearch;
import com.algolia.instantsearch.helpers.Searcher;
import com.algolia.instantsearch.model.SearchResults;
import com.algolia.instantsearch.ui.views.Hits;
import com.algolia.instantsearch.ui.views.SearchBox;
import com.algolia.instantsearch.utils.ItemClickSupport;
import com.algolia.search.saas.Query;
import com.firebase.ui.auth.data.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import io.paperdb.Paper;
import io.reactivex.Flowable;


public class SearchItemActivity extends AppCompatActivity {

    private static final String ALGOLIA_APP_ID = "SAA6306HQC";
    private static final String ALGOLIA_SEARCH_API_KEY = "9291eea64bd5e57769451aa5f72f560f";
    private static final String ALGOLIA_INDEX_NAME = "Database";

    private Searcher searcher;
    private Hits hits;
    private SearchBox searchBox;
    private ConstraintLayout searchWithoutHits;
    private FloatingActionButton fab;
    private TextView record1;
    private TextView record2;
    private TextView record3;

    //private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);

        final String TAG = "hihi";

        Paper.init(this);

        searchBox = findViewById(R.id.searchBox);
        hits = findViewById(R.id.hits);
        searchWithoutHits = findViewById(R.id.searchWithoutHits);
        fab = findViewById(R.id.fab);
        record1 = findViewById(R.id.record1);
        record2 = findViewById(R.id.record2);
        record3 = findViewById(R.id.record3);

        record1.setVisibility(View.GONE);
        record2.setVisibility(View.GONE);
        record3.setVisibility(View.GONE);

        searcher = Searcher.create(ALGOLIA_APP_ID, ALGOLIA_SEARCH_API_KEY, ALGOLIA_INDEX_NAME);
        final InstantSearch helper = new InstantSearch(SearchItemActivity.this, searcher);

        ImageView searchClose = (ImageView) searchBox.findViewById(android.support.v7.appcompat.R.id.search_close_btn);

        searchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                Log.d(TAG, "onQueryTextSumbit = " + query);

                //list.add(query);
                Paper.book().write(query,1);

                helper.search(query);

                searchBox.clearFocus();
                return true;

            }

            @Override
            public boolean onQueryTextChange(String s) {

                Log.d(TAG, "onQueryTextChange = " + s);

                if(s == null || s.length()==0){

                    searchWithoutHits.setVisibility(View.VISIBLE);
                    hits.setVisibility(View.INVISIBLE);

                }else {

                    searchWithoutHits.setVisibility(View.INVISIBLE);
                    hits.setVisibility(View.VISIBLE);
                    helper.search(s);

                }

                return true;
            }

        });

        if (Paper.book().getAllKeys().size() == 1){

            record1.setVisibility(View.VISIBLE);
            record1.setText(Paper.book().getAllKeys().get(0));

        } else if (Paper.book().getAllKeys().size() == 2){

            record1.setVisibility(View.VISIBLE);
            record1.setText(Paper.book().getAllKeys().get(1));
            record2.setVisibility(View.VISIBLE);
            record2.setText(Paper.book().getAllKeys().get(0));

        } else if (Paper.book().getAllKeys().size() >= 3){

            record1.setVisibility(View.VISIBLE);
            record1.setText(Paper.book().getAllKeys().get(Paper.book().getAllKeys().size()-1));
            record2.setVisibility(View.VISIBLE);
            record2.setText(Paper.book().getAllKeys().get(Paper.book().getAllKeys().size()-2));
            record3.setVisibility(View.VISIBLE);
            record3.setText(Paper.book().getAllKeys().get(Paper.book().getAllKeys().size()-3));

        } else {

            record1.setVisibility(View.GONE);
            record2.setVisibility(View.GONE);
            record3.setVisibility(View.GONE);

        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Paper.book().destroy();

                record1.setVisibility(View.GONE);
                record2.setVisibility(View.GONE);
                record3.setVisibility(View.GONE);

                Log.d(TAG,Paper.book().getAllKeys().toString());
                //Toast.makeText(SearchItemActivity.this,,Toast.LENGTH_LONG).show();

            }
        });

        searchClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hits.setVisibility(View.GONE);
                searchWithoutHits.setVisibility(View.VISIBLE);
                searchBox.setQuery("",false);
                searchBox.requestFocus();

                if (Paper.book().getAllKeys().size() == 1){

                    record1.setVisibility(View.VISIBLE);
                    record1.setText(Paper.book().getAllKeys().get(0));

                } else if (Paper.book().getAllKeys().size() == 2){

                    record1.setVisibility(View.VISIBLE);
                    record1.setText(Paper.book().getAllKeys().get(1));
                    record2.setVisibility(View.VISIBLE);
                    record2.setText(Paper.book().getAllKeys().get(0));

                } else if (Paper.book().getAllKeys().size() >= 3){

                    record1.setVisibility(View.VISIBLE);
                    record1.setText(Paper.book().getAllKeys().get(Paper.book().getAllKeys().size()-1));
                    record2.setVisibility(View.VISIBLE);
                    record2.setText(Paper.book().getAllKeys().get(Paper.book().getAllKeys().size()-2));
                    record3.setVisibility(View.VISIBLE);
                    record3.setText(Paper.book().getAllKeys().get(Paper.book().getAllKeys().size()-3));

                }

                Log.d(TAG,Paper.book().getAllKeys().toString());
            }
        });




        /*hits.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
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
        });*/

    }

    @Override
    protected void onDestroy() {

        searcher.destroy();

        //Paper.book().destroy();
        super.onDestroy();
    }


}

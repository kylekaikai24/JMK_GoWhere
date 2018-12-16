package com.example.jmk2018.jmk_gowhere;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class CardViewTabbed extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    private String cardCategory;
//    private String cardName;
//    private String cardLocation;
//    private String cardImageUrl;
      private String cardPhoneNumber;
      private String cardLink;
//    private String cardAddress;
      private Double cardLatitude;
      private Double cardLongitude;
//    private Integer cardNumofLikes;
      private ProgressBar imgProgressBar;

    private static final int REQUEST_PHONE_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_tabbed);

        ImageView imgImage = (ImageView) findViewById(R.id.image);
        String cardImageUrl = getIntent().getStringExtra("ImageUrl");
        Glide.with(this).load(cardImageUrl).into(imgImage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView txtName = (TextView) findViewById(R.id.name);
        TextView txtLocation = (TextView) findViewById(R.id.location);
        TextView txtCategory = (TextView) findViewById(R.id.category);
        TextView txtPhoneNumber = (TextView) findViewById(R.id.phoneNumber);
        TextView txtLink = (TextView) findViewById(R.id.link);
        TextView txtAddress = (TextView) findViewById(R.id.textView3);

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.cardviewtabbed_content);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String cardCategory = getIntent().getStringExtra("Category");
        String cardName = getIntent().getStringExtra("Name");
        String cardLocation = getIntent().getStringExtra("Location");
        String cardAddress = getIntent().getStringExtra("Address");
        cardPhoneNumber = getIntent().getStringExtra("Phone Number");
        cardLink = getIntent().getStringExtra("Link");
        cardLatitude = getIntent().getDoubleExtra("Latitude", 0.00);
        cardLongitude = getIntent().getDoubleExtra("Longitude", 0.00);

        txtCategory.setText(cardCategory);
        txtName.setText(cardName);
        txtLocation.setText(cardLocation);
        txtPhoneNumber.setText(cardPhoneNumber);
        txtLink.setText(cardLink);
        txtAddress.setText(cardAddress);



        txtAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MapsActivity.class);

                intent.putExtra("Latitude", cardLatitude);
                intent.putExtra("Longitude", cardLongitude);

                v.getContext().startActivity(intent);
            }
        });

        txtPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri phoneNumber = Uri.parse("tel:" + cardPhoneNumber);
                Intent callIntent = new Intent(Intent.ACTION_CALL, phoneNumber);

                if (ContextCompat.checkSelfPermission(CardViewTabbed.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                  ActivityCompat.requestPermissions(CardViewTabbed.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);

                }
                else
                {
                    //ActivityCompat.requestPermissions(CardViewTabbed.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                     v.getContext().startActivity(callIntent);
                }

            }
        });

        txtLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Uri link = Uri.parse(cardLink);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, link);

                view.getContext().startActivity(browserIntent);


            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.card_view_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_explore){
            Intent intent = new Intent(CardViewTabbed.this, MainPage.class);
            intent.putExtra("Tab",0);
            startActivity(intent);
            //mViewPager.setCurrentItem(0);
        } else if (id == R.id.nav_promotion){
            Intent intent = new Intent(CardViewTabbed.this, MainPage.class);
            intent.putExtra("Tab",1);
            startActivity(intent);
            //mViewPager.setCurrentItem(1);
        } else if (id == R.id.nav_frontpage){
            Intent intent = new Intent(CardViewTabbed.this, FrontPage.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

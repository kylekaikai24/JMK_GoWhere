package com.example.jmk2018.jmk_gowhere;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PhotoGallery extends AppCompatActivity {

    private DatabaseReference mDatabasePhotos;
    private RecyclerView recyclerView;
    private String post_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);

        post_key = getIntent().getStringExtra("post_key");

        mDatabasePhotos = FirebaseDatabase.getInstance().getReference("Photos").child(post_key);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        FirebaseRecyclerAdapter<Photos,PhotoHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Photos, PhotoHolder>
                (Photos.class, R.layout.gallery_photos, PhotoHolder.class, mDatabasePhotos){

            @Override
            protected void populateViewHolder(final PhotoHolder viewHolder, final Photos model, int position){

                viewHolder.setImage(getApplicationContext(), model.getImageUrl());

            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);


    }






}

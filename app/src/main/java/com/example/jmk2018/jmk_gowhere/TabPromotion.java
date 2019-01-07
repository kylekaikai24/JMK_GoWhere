package com.example.jmk2018.jmk_gowhere;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class TabPromotion extends Fragment{

    private DatabaseReference mDatabase;
    private RecyclerView promotionRecyclerview;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.promotion_main_page, container, false);
        promotionRecyclerview = rootView.findViewById(R.id.promotionRecyclerview);
        promotionRecyclerview.setHasFixedSize(true);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Database");

        LinearLayoutManager promotionLLM = new LinearLayoutManager(getActivity());
        promotionRecyclerview.setLayoutManager(promotionLLM);

        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<Database, CardviewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Database, CardviewHolder>
                (Database.class, R.layout.database_cardview_new, CardviewHolder.class, mDatabase){
            @Override
            protected void populateViewHolder(CardviewHolder viewHolder, final Database model, int position){
                viewHolder.setCategory(model.getCategory());
                viewHolder.setName(model.getName());
                viewHolder.setLocation(model.getLocation());
                viewHolder.setPhone(model.getPhone());
                viewHolder.setAddress(model.getAddress());
                viewHolder.setImage(getContext(), model.getImageUrl());
                viewHolder.setLatitude(model.getLatitude());
                viewHolder.setLongitude(model.getLongitude());
                viewHolder.setKeywords(model.getKeywords());
                viewHolder.setLink(model.getLink());

                final String post_key = getRef(position).getKey();

                viewHolder.mView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){

                        Intent intent = new Intent(view.getContext(),CardViewTabbed.class);
                        intent.putExtra("post_key",post_key);

                        intent.putExtra("ImageUrl",model.getImageUrl());
                        intent.putExtra("Category",model.getCategory());
                        intent.putExtra("Name",model.getName());
                        intent.putExtra("Phone Number",model.getPhone());
                        intent.putExtra("Link",model.getLink());
                        intent.putExtra("Address",model.getAddress());
                        intent.putExtra("Latitude", model.getLatitude());
                        intent.putExtra("Longitude", model.getLongitude());

                        view.getContext().startActivity(intent);

                    }
                });

            }
        };

        promotionRecyclerview.setAdapter(firebaseRecyclerAdapter);

    }





}

package com.example.jmk2018.jmk_gowhere;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TabExplore extends Fragment {

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseLike;
    private DatabaseReference mDatabaseHotSearch;

    private RecyclerView exploreRecyclerview;

    private boolean mProcessLike = false;

    private FirebaseAuth mAuth;

    private ImageView imgHotSearch1;
    private ImageView imgHotSearch2;
    private ImageView imgHotSearch3;
    private ImageView imgHotSearch4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.explore_main_page, container, false);
        exploreRecyclerview = rootView.findViewById(R.id.exploreRecyclerview);
        exploreRecyclerview.setHasFixedSize(true);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Database");
        mDatabaseLike = FirebaseDatabase.getInstance().getReference().child("Likes");
        mDatabaseHotSearch = FirebaseDatabase.getInstance().getReference().child("HotSearch");

        mDatabase.keepSynced(true);
        mDatabaseLike.keepSynced(true);
        mDatabaseHotSearch.keepSynced(true);

        LinearLayoutManager exploreLLM = new LinearLayoutManager(getActivity());
        exploreRecyclerview.setLayoutManager(exploreLLM);
        exploreRecyclerview.setNestedScrollingEnabled(false);
        exploreLLM.setAutoMeasureEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        imgHotSearch1 = rootView.findViewById(R.id.imgHotSearch1);
        imgHotSearch2 = rootView.findViewById(R.id.imgHotSearch2);
        imgHotSearch3 = rootView.findViewById(R.id.imgHotSearch3);
        imgHotSearch4 = rootView.findViewById(R.id.imgHotSearch4);

        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();

        FirebaseRecyclerAdapter<Database, CardviewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Database, CardviewHolder>
                (Database.class, R.layout.database_cardview, CardviewHolder.class, mDatabase){

            @Override
            protected void populateViewHolder(final CardviewHolder viewHolder, Database model, int position){

                final String post_key = getRef(position).getKey();

                viewHolder.setCategory(model.getCategory());
                viewHolder.setName(model.getName());
                viewHolder.setLocation(model.getLocation());
                viewHolder.setPhone(model.getPhone());
                viewHolder.setLink(model.getLink());
                viewHolder.setAddress(model.getAddress());
                viewHolder.setImage(getContext(), model.getImageUrl());
                viewHolder.setLatitude(model.getLatitude());
                viewHolder.setLongitude(model.getLongitude());
                viewHolder.setKeywords(model.getKeywords());
                viewHolder.setLikeButton(post_key);
                viewHolder.setNumOfLikes(post_key);

                /*viewHolder.mView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){

                        Toast.makeText(getActivity(), post_key, Toast.LENGTH_LONG).show();

                    }
                });*/
                // cardview design and explore layout refiine



                viewHolder.likeButton.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view){

                        mProcessLike = true;

                            mDatabaseLike.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if (mProcessLike) {

                                        if (dataSnapshot.child(post_key).hasChild(mAuth.getCurrentUser().getUid())) {

                                            mDatabaseLike.child(post_key).child(mAuth.getCurrentUser().getUid()).removeValue();

                                            mProcessLike = false;

                                        } else {

                                            mDatabaseLike.child(post_key).child(mAuth.getCurrentUser().getUid()).setValue("Hi");

                                            mProcessLike = false;

                                        }
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });



                    }


                });




            }
        };

        exploreRecyclerview.setAdapter(firebaseRecyclerAdapter);

        mDatabaseHotSearch.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String imgUrl1 = dataSnapshot.child("1").getValue(String.class);
                String imgUrl2 = dataSnapshot.child("2").getValue(String.class);
                String imgUrl3 = dataSnapshot.child("3").getValue(String.class);
                String imgUrl4 = dataSnapshot.child("4").getValue(String.class);

                Picasso.with(getContext()).load(imgUrl1).
                        transform(new RoundCornersTransformation(20, 1, true, true)).
                        into(imgHotSearch1);
                Picasso.with(getContext()).load(imgUrl1).
                        transform(new RoundCornersTransformation(20, 1, true, true)).
                        into(imgHotSearch2);
                Picasso.with(getContext()).load(imgUrl1).
                        transform(new RoundCornersTransformation(20, 1, true, true)).
                        into(imgHotSearch3);
                Picasso.with(getContext()).load(imgUrl1).
                        transform(new RoundCornersTransformation(20, 2, true, true)).
                        into(imgHotSearch4);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    }

}

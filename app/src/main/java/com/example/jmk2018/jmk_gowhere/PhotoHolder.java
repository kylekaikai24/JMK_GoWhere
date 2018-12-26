package com.example.jmk2018.jmk_gowhere;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class PhotoHolder extends RecyclerView.ViewHolder {

    View mView;
    String pImage;

    public PhotoHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;
    }

    public void setImage(Context context, String imageUrl){
        pImage = imageUrl;
        ImageView postPhoto = mView.findViewById(R.id.thumbnail);
        //Picasso.with(context).load(imageUrl).fit().noFade().into(card_image);
        Glide.with(context).load(imageUrl).into(postPhoto);

    }

}

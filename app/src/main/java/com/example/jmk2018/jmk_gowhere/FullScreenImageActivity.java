package com.example.jmk2018.jmk_gowhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullScreenImageActivity extends AppCompatActivity {

    String imageUrl;
    ImageView fullSreenImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_full_screen_image);

        fullSreenImage = findViewById(R.id.fullImageView);

        Intent intent = getIntent();

        imageUrl = getIntent().getStringExtra("ImageUrl");

        Glide.with(getApplicationContext()).load(imageUrl).into(fullSreenImage);

    }


}

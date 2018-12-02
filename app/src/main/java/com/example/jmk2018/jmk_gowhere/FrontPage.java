package com.example.jmk2018.jmk_gowhere;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FrontPage extends FirebaseUIActivity implements
        View.OnClickListener{

    //Hihihihi
    //diu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        //ImageView backgroundImage = (ImageView) findViewById(R.id.backgroundImage);
        findViewById(R.id.signOutButton).setOnClickListener(this);
        findViewById(R.id.signInWithEmailButton).setOnClickListener(this);
        findViewById(R.id.signInWithGmailButton).setOnClickListener(this);



        if (ActivityCompat.checkSelfPermission(FrontPage.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(FrontPage.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(FrontPage.this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

    }


    @Override
    public void onClick(View v) {

        int i = v.getId();

        if (i == R.id.signInWithEmailButton) {

            Intent intentToEmailLogin = new Intent();
            intentToEmailLogin.setClass(FrontPage.this, EmailPasswordActivity.class);
            startActivity(intentToEmailLogin);
            findViewById(R.id.signInWithGmailButton).setVisibility(View.INVISIBLE);

        } else if (i == R.id.signInWithGmailButton) {

            Intent intentToGoogleLogin = new Intent();
            intentToGoogleLogin.setClass(FrontPage.this, GoogleSignInActivity.class);
            startActivity(intentToGoogleLogin);
            findViewById(R.id.signInWithEmailButton).setVisibility(View.INVISIBLE);

        } else if (i == R.id.signOutButton) {

            signOut();
            findViewById(R.id.signInWithEmailButton).setVisibility(View.VISIBLE);
            findViewById(R.id.signInWithGmailButton).setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //finish();
        finishAffinity();
    }


}

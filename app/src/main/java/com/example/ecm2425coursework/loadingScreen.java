package com.example.ecm2425coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loadingScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                // Decides where to sent the User based on if they're signed in or not
                if (currentUser==null){
                    startActivity(new Intent(loadingScreen.this,LoginPage.class));
                }else {
                    startActivity(new Intent(loadingScreen.this, MainActivity.class));
                }


            }
        }, 2000 );
    }
}
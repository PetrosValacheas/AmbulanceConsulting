package com.example.ambulanceconsulting.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ambulanceconsulting.R;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseUser currentUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {

        super.onStart();

        if(currentUser==null){

            sendUsertoLoginActiity();

        }

    }

    private void sendUsertoLoginActiity() {

        Intent loginIntent = new Intent( MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }
}

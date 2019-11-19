package com.example.ambulanceconsulting.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ambulanceconsulting.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

    }

    @Override
    protected void onStart() {

        super.onStart();

        if(currentUser == null){

            sendUsertoLoginActivity();

        }

    }

    private void sendUsertoLoginActivity() {

        Intent loginIntent = new Intent( MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }
}

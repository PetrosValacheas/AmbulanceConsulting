package com.example.ambulanceconsulting.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ambulanceconsulting.R;

public class RegisterActivity extends AppCompatActivity {

    private Button registerButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        registerButton = (Button) findViewById(R.id.button_sign_up);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}

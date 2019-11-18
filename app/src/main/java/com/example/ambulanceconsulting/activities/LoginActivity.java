package com.example.ambulanceconsulting.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ambulanceconsulting.R;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText loginUsername , loginPassword;
    private Button buttonSignIn;
    private TextView needNewAccount;

    private FirebaseUser currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InitializeFields();

        needNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendUsertoRegisterActivity();

            }
        });
    }

    private void InitializeFields() {

        loginUsername = (EditText) findViewById(R.id.username_edit_text);
        loginPassword = (EditText) findViewById(R.id.password_edit_text);
        buttonSignIn = (Button)   findViewById(R.id.button_sign_in);
        needNewAccount = (TextView) findViewById(R.id.sign_up_text_view);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(currentUser!=null){
            sendUsertoMainActivity();
        }
    }

    private void sendUsertoMainActivity() {

        Intent loginIntent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(loginIntent);
    }

    private void sendUsertoRegisterActivity() {

        Intent loginIntent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(loginIntent);
    }
}

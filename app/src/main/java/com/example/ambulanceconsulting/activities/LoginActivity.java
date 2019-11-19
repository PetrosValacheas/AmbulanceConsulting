package com.example.ambulanceconsulting.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ambulanceconsulting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail , loginPassword;
    private Button buttonSignIn;
    private TextView needNewAccount;

    private ProgressDialog loadingBar;

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        InitializeFields();

        needNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendUsertoRegisterActivity();

            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AllowUsertoLogin();

            }
        });
    }

    private void AllowUsertoLogin() {

        String email = loginEmail.getText().toString();
        String passwd = loginPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email .......", Toast.LENGTH_SHORT);
        }

        if(TextUtils.isEmpty(passwd)){
            Toast.makeText(this,"Please enter password .......", Toast.LENGTH_SHORT);
        }else{

            loadingBar.setTitle("Sign In");
            loadingBar.setMessage("Please wait.....");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();


            mAuth.signInWithEmailAndPassword(email,passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {

                        sendUsertoMainActivity();
                        Toast.makeText(LoginActivity.this,"Logged in Successful",Toast.LENGTH_SHORT);
                        loadingBar.dismiss();

                    }else{
                        String message = task.getException().toString();
                        Toast.makeText(LoginActivity.this,"Error: "+ message,Toast.LENGTH_SHORT);
                        loadingBar.dismiss();
                    }
                }
            });
        }
    }

    private void InitializeFields() {

        loginEmail = (EditText) findViewById(R.id.email_edit_text);
        loginPassword = (EditText) findViewById(R.id.password_edit_text);
        buttonSignIn = (Button)   findViewById(R.id.button_sign_in);
        needNewAccount = (TextView) findViewById(R.id.sign_up_text_view);
        loadingBar = new ProgressDialog(this);
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

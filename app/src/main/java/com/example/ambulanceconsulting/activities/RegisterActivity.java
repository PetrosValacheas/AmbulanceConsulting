package com.example.ambulanceconsulting.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ambulanceconsulting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import model.member;

public class RegisterActivity extends AppCompatActivity {


    private EditText registerUsername, registerName, registerEmail , registerMobileNumber,
    registerPassword , registerRetypePassword ;
    private Button registerButton;

    private ProgressDialog loadingBar;
    DatabaseReference reff;
    FirebaseAuth mauth;

   member Member;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        mauth = FirebaseAuth.getInstance();

        initializeFields();

        Member = new member();
        reff = FirebaseDatabase.getInstance().getReference().child("Member");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createNewAccount();

            }
        });
    }

    private void initializeFields() {

        registerButton = (Button) findViewById(R.id.button_sign_up);
        registerUsername = (EditText) findViewById(R.id.username_edit_text_sign_up);
        registerName = (EditText) findViewById(R.id.name_edit_text_sign_up);
        registerEmail = (EditText) findViewById(R.id.email_address_edit_text_sign_up);
        registerMobileNumber = (EditText) findViewById(R.id.mobile_number_edit_text_sign_up);
        registerPassword = (EditText) findViewById(R.id.password_edit_text_sign_up);
        registerRetypePassword = (EditText) findViewById(R.id.retype_password_edit_text_sign_up);
        loadingBar = new ProgressDialog(this );

    }

    private void createNewAccount() {

        String email = registerEmail.getText().toString().trim();
        String name  = registerName.getText().toString().trim();
        String username = registerUsername.getText().toString().trim();
        String mobile = registerMobileNumber.getText().toString().trim();
        String password = registerPassword.getText().toString().trim();
        String retypedPass = registerRetypePassword.getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Please enter email .......", Toast.LENGTH_SHORT);
        }
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Please enter username .......", Toast.LENGTH_SHORT);
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password .......", Toast.LENGTH_SHORT);
        }

        Member.setEmail(email);
        Member.setMobile(mobile);
        Member.setName(name);
        Member.setUsername(username);


        if(validate(password,retypedPass)){

            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait , While we are creating new account for you.....");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){

                        reff.push().setValue(Member);

                        sendUsertoLoginActivity();

                        Toast.makeText(RegisterActivity.this,"Account Created Sucessfully ....", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }else{

                      String message = task.getException().toString();
                      Toast.makeText(RegisterActivity.this,"Error : " + message,Toast.LENGTH_LONG).show();
                      loadingBar.dismiss();
                    }

                }
            });
        }else{
            Toast.makeText(this,"Password Not matching", Toast.LENGTH_SHORT).show();
        }


    }

    private void sendUsertoLoginActivity() {

        Intent registerIntent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(registerIntent);
    }

    private boolean validate(String pass1 , String pass2) {

        if(!pass1.equals(pass2))
            return false;
        else
            return true;
    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}

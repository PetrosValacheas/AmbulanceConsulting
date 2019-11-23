package com.example.ambulanceconsulting.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ambulanceconsulting.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    private TextView userName , mobile , email , name ;

    private DatabaseReference profileUserRef;
    private FirebaseAuth mAuth;

    private String currentUserId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        InitializeFields();

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        profileUserRef = FirebaseDatabase.getInstance().getReference().child("Member").child(currentUserId);

        profileUserRef.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String myName = dataSnapshot.child("name").getValue().toString();
                    String myEmail = dataSnapshot.child("email").getValue().toString();
                    String myuserName = dataSnapshot.child("username").getValue().toString();
                    String myMobile = dataSnapshot.child("mobile").getValue().toString();

                    Log.d("Profile","EMAIL: "+ myEmail);
                    Log.d("Profile","Username: "+ myuserName);


                 email.setText("koula");
                 name.setText(myName);
                 userName.setText(myuserName);
                 mobile.setText(myMobile);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }
        }));

        Log.d("PROFILE","CANCEL" + email.getText().toString());

    }

    private void InitializeFields() {

        userName = (TextView) findViewById(R.id.userName_input);
        email = (TextView) findViewById(R.id.email_input);
        name = (TextView)findViewById(R.id.name_input);
        mobile = (TextView)findViewById(R.id.mobile_input);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}

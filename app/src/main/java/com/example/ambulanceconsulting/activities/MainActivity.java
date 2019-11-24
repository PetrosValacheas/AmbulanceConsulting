package com.example.ambulanceconsulting.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ambulanceconsulting.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button hospitals;
    private Button infos;
    private Button call;
    private Button medicalDiagnosis;

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        initializeFields();

        hospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendUsertoMap();
            }
        });

        infos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendUsertoInfos();
                
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUsertoCall();
            }
        });

        medicalDiagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUsertoDiagnosis();
            }
        });


    }

    private void sendUsertoDiagnosis() {

        Intent medicalintent = new Intent(MainActivity.this,SymptomsActivity.class);
        startActivity(medicalintent);
    }

    private void sendUsertoCall() {

        Intent callIntent = new Intent(MainActivity.this,ContactActivity.class);
        startActivity(callIntent);
    }

    private void sendUsertoInfos() {

        Intent infoIntent = new Intent(MainActivity.this,InfosAcitvity.class);
        startActivity(infoIntent);
    }

    private void sendUsertoMap() {

        Intent mapIntent = new Intent(MainActivity.this,MapsActivity.class);
        startActivity(mapIntent);
    }

    private void initializeFields() {

        hospitals = (Button) findViewById(R.id.bthospital);
        infos = (Button) findViewById(R.id.btnInfos);
        call = (Button)findViewById(R.id.btnCall);
        medicalDiagnosis = (Button)findViewById(R.id.btnDiagnosis);
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
    private void sendUsertoProfile() {

        Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(profileIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);

         getMenuInflater().inflate(R.menu.options_menu,menu);
         return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);


         if(item.getItemId() == R.id.main_logout_option){

             mAuth.signOut();
             sendUsertoLoginActivity();
         }
        if(item.getItemId() == R.id.main_Profile_option){

            sendUsertoProfile();
        }
       return true;
    }
}

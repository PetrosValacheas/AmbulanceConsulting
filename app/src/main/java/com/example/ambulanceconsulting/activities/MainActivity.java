package com.example.ambulanceconsulting.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.ambulanceconsulting.R;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity  {

    private Button hospitals;
    private Button infos;
    private Button call;
    private Button medicalDiagnosis;

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        client = LocationServices.getFusedLocationProviderClient(this);




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
    private void sendUsertoHeartRate() {

        Intent heartIntent = new Intent(MainActivity.this,HeartRateProcess.class);
        startActivity(heartIntent);
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

        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED ){
            return;
        }

        client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location!=null){

                    //Toast.makeText(MainActivity.this,"Location:"+location.toString(),Toast.LENGTH_SHORT).show();
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference ambulanceAvailability = FirebaseDatabase.getInstance().getReference().child("Ambulances Available");

                    GeoFire geoFire = new GeoFire(ambulanceAvailability);
                    geoFire.setLocation(userId,new GeoLocation(location.getLatitude(),location.getLongitude()));

                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED ){
            return;
        }

        client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location!=null){

                    //Toast.makeText(MainActivity.this,"Location:"+location.toString(),Toast.LENGTH_SHORT).show();
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference ambulanceAvailability = FirebaseDatabase.getInstance().getReference().child("Ambulances Available");

                    GeoFire geoFire = new GeoFire(ambulanceAvailability);
                    geoFire.setLocation(userId,new GeoLocation(location.getLatitude(),location.getLongitude()));

                }

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ambulanceAvailability = FirebaseDatabase.getInstance().getReference().child("Ambulances Available");

        GeoFire geoFire = new GeoFire(ambulanceAvailability);
        geoFire.removeLocation(userId);
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
        if(item.getItemId() == R.id.main_heart_rate){

            sendUsertoHeartRate();
        }
       return true;
    }

    private void sendUsertoHeartCheck() {

        Intent heartCheckIntent = new Intent(MainActivity.this, HeartRateProcess.class);
        startActivity(heartCheckIntent);
    }
}

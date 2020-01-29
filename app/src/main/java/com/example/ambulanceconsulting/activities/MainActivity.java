package com.example.ambulanceconsulting.activities;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
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
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity  {

    private Button hospitals;
    private Button infos;
    private Button call;
    private Button medicalDiagnosis;

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    private FusedLocationProviderClient client;

    DatabaseReference ambulanceAvailability;
    DatabaseReference tokensAvailability;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ambulanceAvailability = FirebaseDatabase.getInstance().getReference().child("Ambulances Available");
        tokensAvailability =    FirebaseDatabase.getInstance().getReference().child("Ambulance Tokens");

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

            NotificationChannel channel = new NotificationChannel("MyNotifications","MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

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

        }else{

            if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED ){
                return;
            }

            client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if(location!=null){

                        //Toast.makeText(MainActivity.this,"Location:"+location.toString(),Toast.LENGTH_SHORT).show();
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        GeoFire geoFire = new GeoFire(ambulanceAvailability);
                        geoFire.setLocation(userId,new GeoLocation(location.getLatitude(),location.getLongitude()));

                        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

                        tokensAvailability.child(userId).setValue(refreshedToken);

                    }
                }
            });

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(currentUser!=null){

            if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED ){
                return;
            }

            client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if(location!=null){

                        //Toast.makeText(MainActivity.this,"Location:"+location.toString(),Toast.LENGTH_SHORT).show();
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


                        GeoFire geoFire = new GeoFire(ambulanceAvailability);
                        geoFire.setLocation(userId,new GeoLocation(location.getLatitude(),location.getLongitude()));

                        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

                        tokensAvailability.child(userId).setValue(refreshedToken);

                    }
                }
            });

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       
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

             String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

             tokensAvailability.child(userId).removeValue();
             GeoFire geoFire = new GeoFire(ambulanceAvailability);
             geoFire.removeLocation(userId);

             mAuth.signOut();

             sendUsertoLoginActivity();
         }
        if(item.getItemId() == R.id.main_Profile_option){

            sendUsertoProfile();
        }
        if(item.getItemId() == R.id.main_heart_rate){

            sendUsertoHeartRate();
        }

        if(item.getItemId() == R.id.main_respiration_rate){

            sendUsertorespirationRate();
        }
       return true;
    }

    private void sendUsertorespirationRate() {

        Intent respirationCheckIntent = new Intent(MainActivity.this, respirationProcess.class);
        startActivity(respirationCheckIntent);
    }

    private void sendUsertoHeartCheck() {

        Intent heartCheckIntent = new Intent(MainActivity.this, HeartRateProcess.class);
        startActivity(heartCheckIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        moveTaskToBack(true);
    }
}

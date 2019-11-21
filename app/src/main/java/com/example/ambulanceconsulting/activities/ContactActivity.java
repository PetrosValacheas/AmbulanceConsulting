package com.example.ambulanceconsulting.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ambulanceconsulting.R;

public class ContactActivity extends AppCompatActivity {

    private Button callAmbulance ,callNEambulance , callEuropean,callBloodDonation,callPoisonCenter,callOnDutyHospitals;

    private static final int REQUEST_CALL = 1;
    private static String contactNumbers [] = new String[] {"166","210691300", "112", "2102410000", "2107793777","1434"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        InitializeFields();


        callAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(0);
            }
        });
        callNEambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(1);
            }
        });
        callEuropean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(2);
            }
        });
        callBloodDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(3);
            }
        });
        callPoisonCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(4);
            }
        });
        callOnDutyHospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(5);
            }
        });
    }

    private void InitializeFields() {

        callAmbulance = (Button)findViewById(R.id.call_Ambulance);
        callNEambulance = (Button) findViewById(R.id.call_non_e_Ambulance);
        callEuropean=(Button) findViewById(R.id.europeanEmergency);
        callBloodDonation = (Button) findViewById(R.id.bloodDonation);
        callPoisonCenter = (Button) findViewById(R.id.poison_center);
        callOnDutyHospitals = (Button) findViewById(R.id.onDutyHospitals);
    }

    private void makePhoneCall(int index) {
        if (ContextCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ContactActivity.this, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+contactNumbers[index])));
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}

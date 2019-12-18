package com.example.ambulanceconsulting.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ambulanceconsulting.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class HeartRateResult extends AppCompatActivity {

    private String user,Date;
    int HR;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date today = Calendar.getInstance().getTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hear_rate_result);

        Date = df.format(today);
        TextView RHR = (TextView) this.findViewById(R.id.HRR);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            HR = bundle.getInt("bpm");
            user = bundle.getString("Usr");
            Log.d("DEBUG_TAG", "ccccc"+ user);
            RHR.setText(String.valueOf(HR));
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(HeartRateResult.this, MainActivity.class);
        startActivity(i);
        finish();
    }
   }

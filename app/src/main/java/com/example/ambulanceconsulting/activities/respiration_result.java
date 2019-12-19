package com.example.ambulanceconsulting.activities;

import android.content.Intent;
import android.os.Bundle;
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

public class respiration_result extends AppCompatActivity {

    private String Date;
    int RR;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date today = Calendar.getInstance().getTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.respiration_result);

        Date = df.format(today);
        TextView RRR = (TextView) this.findViewById(R.id.RRR);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            RR = bundle.getInt("bpm");
            RRR.setText(String.valueOf(RR));
        }

    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(respiration_result.this, MainActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();

    }
}

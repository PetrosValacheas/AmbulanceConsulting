package com.example.ambulanceconsulting.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ambulanceconsulting.R;

import java.util.ArrayList;

public class DiagnosisActivity extends AppCompatActivity {

    private ArrayList<String> symptomIds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        symptomIds = new ArrayList<>();
        Intent i = getIntent();
        symptomIds = i.getStringArrayListExtra("ids");
        Toast.makeText(DiagnosisActivity.this,String.valueOf(symptomIds.size()),Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}

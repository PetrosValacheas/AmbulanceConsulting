package com.example.ambulanceconsulting.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ambulanceconsulting.R;

import java.util.ArrayList;

import model.diagnosis;

public class DiagnosisResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_result);

        ArrayList<diagnosis> diagnosis;

        diagnosis = (ArrayList<diagnosis>) getIntent().getSerializableExtra("diagnosisResults");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}

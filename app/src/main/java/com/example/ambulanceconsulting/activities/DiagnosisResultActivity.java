package com.example.ambulanceconsulting.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ambulanceconsulting.R;

import java.util.ArrayList;

import model.diagnosis;

public class DiagnosisResultActivity extends AppCompatActivity {

    private TextView disease , accuracy ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_result);

        Initialfields();

        ArrayList<diagnosis> diagnosis;

        diagnosis = (ArrayList<diagnosis>) getIntent().getSerializableExtra("diagnosisResults");

        disease.setText(diagnosis.get(0).getIssue().getName());
        accuracy.setText(String.valueOf(diagnosis.get(0).getIssue().getAccuracy()));
    }

    private void Initialfields() {

        disease = (TextView) findViewById(R.id.disease);
        accuracy = (TextView) findViewById(R.id.accuracy);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}

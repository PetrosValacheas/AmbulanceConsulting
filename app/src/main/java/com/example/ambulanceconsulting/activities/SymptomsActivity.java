package com.example.ambulanceconsulting.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ambulanceconsulting.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Connection.RetrofitInstance;
import adapter.SymtomAdaper;
import apiInterface.getSymptomsService;
import model.Symptom;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SymptomsActivity extends AppCompatActivity {

     SymtomAdaper mAdapter;
     RecyclerView recyclerView;

    StringBuffer sb = null;
    ArrayList<String> symptomsIds ;

    private String ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InBldHJvc3ZhMTJAZ21haWwuY29tIiwicm9sZSI6IlVzZXIiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9zaWQiOiI2MTEwIiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy92ZXJzaW9uIjoiMjAwIiwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWltcy9saW1pdCI6Ijk5OTk5OTk5OSIsImh0dHA6Ly9leGFtcGxlLm9yZy9jbGFpbXMvbWVtYmVyc2hpcCI6IlByZW1pdW0iLCJodHRwOi8vZXhhbXBsZS5vcmcvY2xhaW1zL2xhbmd1YWdlIjoiZW4tZ2IiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL2V4cGlyYXRpb24iOiIyMDk5LTEyLTMxIiwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWltcy9tZW1iZXJzaGlwc3RhcnQiOiIyMDE5LTExLTIzIiwiaXNzIjoiaHR0cHM6Ly9zYW5kYm94LWF1dGhzZXJ2aWNlLnByaWFpZC5jaCIsImF1ZCI6Imh0dHBzOi8vaGVhbHRoc2VydmljZS5wcmlhaWQuY2giLCJleHAiOjE1NzQ3MjU2MjMsIm5iZiI6MTU3NDcxODQyM30.KlJYhDU6-fH1beFiopccZmZ20ydHLDFRVtZDMK3rA1k";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_symptoms);

        recyclerView = findViewById(R.id.recycler_view_symptom_list);

        /** Create handle for the RetrofitInstance interface*/
        getSymptomsService service = RetrofitInstance.
                getRetrofitInstance().create(getSymptomsService.class);

        /** Call the method with parameter in the interface to get the symptom data*/
        Call<ArrayList<Symptom>> call = service.getSymptomData();

        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<ArrayList<Symptom>>() {
            @Override
            public void onResponse(Call<ArrayList<Symptom>> call, Response<ArrayList<Symptom>> response) {
                generateSymptomList(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Symptom>> call, Throwable t) {
                Toast.makeText(SymptomsActivity.this, "Something went wrong...Error message: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.continueDiagnosis);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sb = new StringBuffer();
                symptomsIds = new ArrayList<>();

                for(Symptom s :mAdapter.checkedSymptoms){

                    sb.append(s.getName());
                    sb.append("\n");
                    symptomsIds.add(String.valueOf(s.getId()));
                }

                if(mAdapter.checkedSymptoms.size()>0){

                    Toast.makeText(SymptomsActivity.this,sb.toString(),Toast.LENGTH_SHORT).show();
                    Intent diagnosisIntent = new Intent(SymptomsActivity.this, DiagnosisActivity.class);

                    diagnosisIntent.putStringArrayListExtra("ids",symptomsIds);
                    startActivity(diagnosisIntent);

                   // Toast.makeText(SymptomsActivity.this,String.valueOf(symptomsIds.size()),Toast.LENGTH_SHORT).show();


                }else{

                    Toast.makeText(SymptomsActivity.this,"Please Select Symptoms...",Toast.LENGTH_SHORT).show();

                }
            }
        });



    }

    private void generateSymptomList(ArrayList<Symptom> symptomArrayList) {

        mAdapter = new SymtomAdaper(symptomArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SymptomsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}

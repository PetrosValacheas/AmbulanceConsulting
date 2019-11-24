package com.example.ambulanceconsulting.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ambulanceconsulting.R;

import java.util.ArrayList;

import Connection.RetrofitInstance;
import adapter.SymtomAdaper;
import apiInterface.getSymptomsService;
import model.Symptom;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SymptomsActivity extends AppCompatActivity {

    private SymtomAdaper mAdapter;
    private RecyclerView recyclerView;

    private String ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InBldHJvc3ZhMTJAZ21haWwuY29tIiwicm9sZSI6IlVzZXIiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9zaWQiOiI2MTEwIiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy92ZXJzaW9uIjoiMjAwIiwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWltcy9saW1pdCI6Ijk5OTk5OTk5OSIsImh0dHA6Ly9leGFtcGxlLm9yZy9jbGFpbXMvbWVtYmVyc2hpcCI6IlByZW1pdW0iLCJodHRwOi8vZXhhbXBsZS5vcmcvY2xhaW1zL2xhbmd1YWdlIjoiZW4tZ2IiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL2V4cGlyYXRpb24iOiIyMDk5LTEyLTMxIiwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWltcy9tZW1iZXJzaGlwc3RhcnQiOiIyMDE5LTExLTIzIiwiaXNzIjoiaHR0cHM6Ly9zYW5kYm94LWF1dGhzZXJ2aWNlLnByaWFpZC5jaCIsImF1ZCI6Imh0dHBzOi8vaGVhbHRoc2VydmljZS5wcmlhaWQuY2giLCJleHAiOjE1NzQ2MjgwOTcsIm5iZiI6MTU3NDYyMDg5N30.lipgu6cHpCixaMoAjG_dfqrE4sI7DVfYnysOVasPLCY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_symptoms);

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
                Log.d("okokokokokokko","11111111111111111");
            }

            @Override
            public void onFailure(Call<ArrayList<Symptom>> call, Throwable t) {
                Toast.makeText(SymptomsActivity.this, "Something went wrong...Error message: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
                Log.d("okokokokokokko","2222222222222222222222");

            }
        });

    }

    private void generateSymptomList(ArrayList<Symptom> symptomArrayList) {
        recyclerView = findViewById(R.id.recycler_view_symptom_list);
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

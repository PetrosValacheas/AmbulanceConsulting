package com.example.ambulanceconsulting.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ambulanceconsulting.R;

import java.util.ArrayList;
import model.diagnosis;

import Connection.RetrofitInstance;
import apiInterface.getDiagnosisService;
import apiInterface.getSymptomsService;
import model.Isuue;
import model.Symptom;
import model.diagnosis;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.nio.charset.StandardCharsets.*;

public class DiagnosisActivity extends AppCompatActivity {

    private ArrayList<String> symptomIds;
    private EditText year , gender;
    private Button diagnosis;
    public String Gender , Year;
    public String result = "";

    private String ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InBldHJvc3ZhMTJAZ21haWwuY29tIiwicm9sZSI6IlVzZXIiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9zaWQiOiI2MTEwIiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy92ZXJzaW9uIjoiMjAwIiwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWltcy9saW1pdCI6Ijk5OTk5OTk5OSIsImh0dHA6Ly9leGFtcGxlLm9yZy9jbGFpbXMvbWVtYmVyc2hpcCI6IlByZW1pdW0iLCJodHRwOi8vZXhhbXBsZS5vcmcvY2xhaW1zL2xhbmd1YWdlIjoiZW4tZ2IiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL2V4cGlyYXRpb24iOiIyMDk5LTEyLTMxIiwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWltcy9tZW1iZXJzaGlwc3RhcnQiOiIyMDE5LTExLTIzIiwiaXNzIjoiaHR0cHM6Ly9zYW5kYm94LWF1dGhzZXJ2aWNlLnByaWFpZC5jaCIsImF1ZCI6Imh0dHBzOi8vaGVhbHRoc2VydmljZS5wcmlhaWQuY2giLCJleHAiOjE1NzUzNjExNTUsIm5iZiI6MTU3NTM1Mzk1NX0.n2NJx5I-R2U4fxn6OY5fZc2Ds8MrYaUIAEqkYp28JHo";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        year = (EditText) findViewById(R.id.year);
        gender = (EditText) findViewById(R.id.gender);
        diagnosis =(Button) findViewById(R.id.btnDiagnosis);

        symptomIds = new ArrayList<>();
        Intent i = getIntent();
        symptomIds = i.getStringArrayListExtra("ids");
       // Toast.makeText(DiagnosisActivity.this,String.valueOf(symptomIds.size()),Toast.LENGTH_SHORT).show();
        String symptomsConcat = "[";
        for(int j=0;j<symptomIds.size();j++){

          symptomsConcat =  symptomsConcat.concat(symptomIds.get(j));
          symptomsConcat =  symptomsConcat.concat(",");
        }

        result = symptomsConcat.substring(0,symptomsConcat.length()-1);
        result = result.concat("]");

        byte pText[] = result.getBytes(ISO_8859_1);
        final String value = new String(pText,UTF_8);


        diagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Year = year.getText().toString();
                Gender = gender.getText().toString();

                /** Create handle for the RetrofitInstance interface*/
                getDiagnosisService service = RetrofitInstance.
                        getRetrofitInstance().create(getDiagnosisService.class);

                /** Call the method with parameter in the interface to get the symptom data*/
                Call<ArrayList<diagnosis>> call = service.getDiagnosis(ACCESS_TOKEN,"en-gb",value,Gender,Year);

                Log.wtf("URL Called", call.request().url() + "");

                call.enqueue(new Callback<ArrayList<diagnosis>>() {
                    @Override
                    public void onResponse(Call<ArrayList<diagnosis>> call, Response<ArrayList<diagnosis>> response) {

                        if(response.isSuccessful()){

                            ArrayList<diagnosis> diagnosisIssues = new ArrayList<>();
                            diagnosisIssues  = response.body();
                            if(diagnosisIssues.size()>0){

                                Intent resultIntent = new Intent(DiagnosisActivity.this, DiagnosisResultActivity.class);
                                resultIntent.putExtra("diagnosisResults",diagnosisIssues);

                                startActivity(resultIntent);
                            }else{

                                Toast.makeText(DiagnosisActivity.this,"Please Select Again Symptoms...",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Log.d("Error : ",response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<diagnosis>> call, Throwable t) {

                        Log.d("Error :",t.getLocalizedMessage());

                    }
                });

            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
    }
}

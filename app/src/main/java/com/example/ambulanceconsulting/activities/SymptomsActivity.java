package com.example.ambulanceconsulting.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ambulanceconsulting.BuildConfig;
import com.example.ambulanceconsulting.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import Connection.RetrofitInstance;
import adapter.SymtomAdaper;
import apiInterface.getSymptomsService;
import model.Symptom;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okio.ByteString;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor;

import static java.lang.String.valueOf;

import okhttp3.OkHttpClient;
import okhttp3.Request;



public class SymptomsActivity extends AppCompatActivity {

     SymtomAdaper mAdapter;
     RecyclerView recyclerView;

    StringBuffer sb = null;
    ArrayList<String> symptomsIds ;

    private static String userName = "petrosva12@gmail.com";
    private static String passWord = "b6B2EtKc37Qrj9A5X";
    private String token = "";
    private final OkHttpClient client = new OkHttpClient();
    private static String Url = "https://sandbox-healthservice.priaid.ch/login";

    private String ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InBldHJvc3ZhMTJAZ21haWwuY29tIiwicm9sZSI6IlVzZXIiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9zaWQiOiI2MTEwIiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy92ZXJzaW9uIjoiMjAwIiwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWltcy9saW1pdCI6Ijk5OTk5OTk5OSIsImh0dHA6Ly9leGFtcGxlLm9yZy9jbGFpbXMvbWVtYmVyc2hpcCI6IlByZW1pdW0iLCJodHRwOi8vZXhhbXBsZS5vcmcvY2xhaW1zL2xhbmd1YWdlIjoiZW4tZ2IiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL2V4cGlyYXRpb24iOiIyMDk5LTEyLTMxIiwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWltcy9tZW1iZXJzaGlwc3RhcnQiOiIyMDE5LTExLTIzIiwiaXNzIjoiaHR0cHM6Ly9zYW5kYm94LWF1dGhzZXJ2aWNlLnByaWFpZC5jaCIsImF1ZCI6Imh0dHBzOi8vaGVhbHRoc2VydmljZS5wcmlhaWQuY2giLCJleHAiOjE1NzQ3MjU2MjMsIm5iZiI6MTU3NDcxODQyM30.KlJYhDU6-fH1beFiopccZmZ20ydHLDFRVtZDMK3rA1k";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_symptoms);

        recyclerView = findViewById(R.id.recycler_view_symptom_list);

        /** Create handle for the RetrofitInstance interface*/
        getSymptomsService service = RetrofitInstance.
                getRetrofitInstance().create(getSymptomsService.class);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                SecretKeySpec keySpec = new SecretKeySpec(
                        passWord.getBytes(),
                        "HmacMD5");

                String computedHashString = "";
                try {
                    Mac mac = Mac.getInstance("HmacMD5");
                    mac.init(keySpec);
                    byte[] result = mac.doFinal(Url.getBytes());

                    // BASE64Encoder encoder = new BASE64Encoder();
                    computedHashString = Base64.getEncoder().encodeToString(result);
                    Log.d("hash",computedHashString);

                } catch (NoSuchAlgorithmException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    try {
                        throw new Exception("Can not create token (NoSuchAlgorithmException)");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } catch (InvalidKeyException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    try {
                        throw new Exception("Can not create token (InvalidKeyException)");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }


                Log.d("Use:",userName);
                Log.d("Pass:",passWord);
                Log.d("url:",Url);
                //ArrayList<NameValuePair> parms = new ArrayList<NameValuePair>();
                // parms.add(new BasicNameValuePair("Authorization","Bearer  " + userName + ":" + computedHashString));
                // httpPost = new HttpPost(Url);
                //  httpPost.setEntity(new UrlEncodedFormEntity(parms));
                // httpPost.setHeader("Content-type","application/json");
                //  httpPost.setHeader("Cache-Control", "no-cache");
                //  httpPost.setHeader(new BasicHeader("Authorization", "Bearer  " + userName + ":" + computedHashString));

                //  CloseableHttpResponse  response = httpclient.execute(httpPost);

                MediaType FORM_DATA_TYPE
                       = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");


                String hed = "Bearer " + userName + ":" + computedHashString;

                String result = hed.replace("\n", "");


                RequestBody body = RequestBody.create(FORM_DATA_TYPE,"" );


                Request request = new Request.Builder()
                        .url(Url)
                        //.header("Host","authservice.priaid.ch")
                        .header("Authorization",result)
                        .post(body)
                        .build();

                try {
                    okhttp3.Response response = client.newCall(request).execute();

                    Log.d("RESPONSE1111111111111111",response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }});

        t.start(); // spawn thread

        try {
            t.join();  // wait for thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



       /* try {
            token = LoadToken(userName,passWord , Url);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        /** Call the method with parameter in the interface to get the symptom data*/
        Call<ArrayList<Symptom>> call = service.getSymptomData(token , "en-gb" ,"json");

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
                    symptomsIds.add(valueOf(s.getId()));
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

   /* private String LoadToken(final String userName, final String passWord , final String url) throws Exception {

        SecretKeySpec keySpec = new SecretKeySpec(
                passWord.getBytes(),
                "HmacMD5");

        String computedHashString = "";
        try {
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(keySpec);
            byte[] result = mac.doFinal(url.getBytes());

            // BASE64Encoder encoder = new BASE64Encoder();
            computedHashString = Base64.getEncoder().encodeToString(result);

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new Exception("Can not create token (NoSuchAlgorithmException)");
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new Exception("Can not create token (InvalidKeyException)");
        }


        try {
            httpPost = new HttpPost(url);
            httpPost.setHeader("Authorization", "Bearer " + userName + ":" + computedHashString);
            HttpResponse response = httpclient.execute(httpPost);

            if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
               Log.d("Response with Status","response error");
            }

            token = response.getEntity().getContent().toString();

        }
        catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new Exception("Can not create token (ClientProtocolException)");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new Exception("Can not create token (IOException)");
        }


        return token;


    }*/

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

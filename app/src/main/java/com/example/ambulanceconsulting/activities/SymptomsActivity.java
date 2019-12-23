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
import org.json.JSONException;
import org.json.JSONObject;

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

    private static String userName = "Ld36T_GMAIL_COM_AUT";
    private static String passWord = "s3C9Pam7S6YtEc4x8";
    private String token = "";
    private final OkHttpClient client = new OkHttpClient();
    private static String Url = "https://authservice.priaid.ch/login";

    private String ACCESS_TOKEN = "";

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

                    //Log.d("RESPONSE1111111111111111",response.body().string());
                    String jsonData = response.body().string();
                    JSONObject Jobject = new JSONObject(jsonData);
                    ACCESS_TOKEN = Jobject.getString("Token");



                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
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
        Call<ArrayList<Symptom>> call = service.getSymptomData(ACCESS_TOKEN , "en-gb" ,"json");

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
                    diagnosisIntent.putExtra("token",ACCESS_TOKEN);
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

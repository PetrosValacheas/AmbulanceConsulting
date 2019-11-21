package com.example.ambulanceconsulting.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ambulanceconsulting.R;

public class InfosAcitvity extends AppCompatActivity {

    private Button btnStroke,btnBurns,btnTrauma,btnBone,btnAnaphylaxis,btnPoison,btnHeart,btnHeat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);

        InitializeFields();

        btnStroke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent strokeIntent = new Intent(InfosAcitvity.this,StrokeAcivity.class);
                startActivity(strokeIntent);
            }
        });
        btnTrauma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent traumaIntent = new Intent(InfosAcitvity.this,TraumaActivity.class);
                startActivity(traumaIntent);
            }
        });
        btnBurns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent burnsIntent = new Intent(InfosAcitvity.this,BurnActivity.class);
                startActivity(burnsIntent);
            }
        });
        btnBone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent boneIntent = new Intent(InfosAcitvity.this,BoneActivity.class);
                startActivity(boneIntent);
            }
        });
        btnAnaphylaxis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent anaphylaxisIntent = new Intent(InfosAcitvity.this, AllergicActivity.class);
                startActivity(anaphylaxisIntent);
            }
        });
        btnPoison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent poisonIntent = new Intent(InfosAcitvity.this, PoisonActivity.class);
                startActivity(poisonIntent);
            }
        });
        btnHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent heartIntent = new Intent(InfosAcitvity.this, HeartActivity.class);
                startActivity(heartIntent);
            }
        });
        btnHeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent heatIntent = new Intent(InfosAcitvity.this, HeatActivity.class);
                startActivity(heatIntent);
            }
        });
    }

    private void InitializeFields() {

        btnStroke = (Button)findViewById(R.id.strokeButton);
        btnBurns = (Button)findViewById(R.id.burnButton);
        btnTrauma = (Button)findViewById(R.id.traumaButton);
        btnBone = (Button)findViewById(R.id.boneButton);
        btnAnaphylaxis = (Button)findViewById(R.id.allergicButton);
        btnPoison = (Button)findViewById(R.id.poisonButton);
        btnHeart = (Button)findViewById(R.id.heartAttackButton);
        btnHeat = (Button)findViewById(R.id.heatStrokeButton);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}

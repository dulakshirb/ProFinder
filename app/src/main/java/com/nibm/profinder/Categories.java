package com.nibm.profinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class Categories extends AppCompatActivity {

    RelativeLayout ctArchitecture,ctConstruction,ctEducational,ctEngineering,ctFinance,ctFashion,ctHealthcare,ctIct,ctLegalConsultant,ctMedia,ctSports,ctTravelAndTourism,ctOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        ctArchitecture = (RelativeLayout) findViewById(R.id.cat_architecture);
        ctArchitecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, Architecture.class);
                startActivity(intent);
            }
        });

        ctConstruction = (RelativeLayout) findViewById(R.id.cat_constructor);
        ctConstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, Construction.class);
                startActivity(intent);
            }
        });


        ctEducational = (RelativeLayout) findViewById(R.id.cat_educational);
        ctEducational.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, Educational.class);
                startActivity(intent);
            }
        });

        ctEngineering = (RelativeLayout) findViewById(R.id.cat_engineering);
        ctEngineering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, Engineering.class);
                startActivity(intent);
            }
        });

        ctFinance = (RelativeLayout) findViewById(R.id.cat_finance);
        ctFinance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, Finance.class);
                startActivity(intent);
            }
        });

        ctFashion = (RelativeLayout) findViewById(R.id.cat_fashion);
        ctFashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, Fashion.class);
                startActivity(intent);
            }
        });

        ctHealthcare = (RelativeLayout) findViewById(R.id.cat_healthcare);
        ctHealthcare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, Healthcare.class);
                startActivity(intent);
            }
        });

        ctIct = (RelativeLayout) findViewById(R.id.cat_ict);
        ctIct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, ICT.class);
                startActivity(intent);
            }
        });

        ctLegalConsultant = (RelativeLayout) findViewById(R.id.cat_legalConsultant);
        ctLegalConsultant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, LegalConsultant.class);
                startActivity(intent);
            }
        });

        ctMedia = (RelativeLayout) findViewById(R.id.cat_media);
        ctMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, Media.class);
                startActivity(intent);
            }
        });

        ctSports = (RelativeLayout) findViewById(R.id.cat_sports);
        ctSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, Sports.class);
                startActivity(intent);
            }
        });

        ctTravelAndTourism = (RelativeLayout) findViewById(R.id.cat_travelAndTourism);
        ctTravelAndTourism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, TravelAndTourism.class);
                startActivity(intent);
            }
        });

        ctOther = (RelativeLayout) findViewById(R.id.cat_other);
        ctOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, Other.class);
                startActivity(intent);
            }
        });
    }
}
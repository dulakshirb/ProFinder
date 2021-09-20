package com.nibm.profinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Welcome extends AppCompatActivity implements View.OnClickListener{

    private Button btn_login , btn_regFinder, btn_regPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btn_login = findViewById(R.id.btn_login);
        btn_regFinder = findViewById(R.id.btn_regFinder);
        btn_regPro = findViewById(R.id.btn_regPro);

        btn_login.setOnClickListener(this);
        btn_regFinder.setOnClickListener(this);
        btn_regPro.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                startActivity(new Intent(this, Login.class));
                break;

            case R.id.btn_regFinder:
                startActivity(new Intent(this, UserSignup.class));
                break;
                
            case R.id.btn_regPro:
                startActivity(new Intent(this, ProSignup.class));
                break;
        }
    }
}
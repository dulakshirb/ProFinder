package com.nibm.profinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    private ImageView splashImage;
    private TextView splashText;
    private static int SPLASH_SCREEN_TIME_OUT=2000;
    Animation topAnimation,bottomAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        splashImage= findViewById(R.id.img_logo);
        splashText= findViewById(R.id.txt_plashTitle);

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        splashImage.setAnimation(topAnimation);
        splashText.setAnimation(bottomAnimation);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashScreen.this,Welcome.class);
//                startActivity(intent);
//                finish();
//            }
//        },SPLASH_SCREEN_TIME_OUT);

        Thread loading = new Thread(){

            public void run(){
                try{
                    sleep(3000);
                    Intent intent = new Intent(SplashScreen.this,Welcome.class);
                    startActivity(intent);
                     finish();

                }catch (Exception e){

                    e.printStackTrace();
                }
                finally {
                    finish();
                }

            }
        };
        loading.start();
    }
}
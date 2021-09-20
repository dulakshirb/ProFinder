package com.nibm.profinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private EditText emailrst;
    private Button resetpassword;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        emailrst = (EditText) findViewById(R.id.txt_email);
        resetpassword = (Button) findViewById(R.id.btn_resetPassword);

        auth = FirebaseAuth.getInstance();

        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = emailrst.getText().toString().trim();

        if(email.isEmpty()){
            emailrst.setError("Email is Required");
            emailrst.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailrst.setError("Invalid Email");
            emailrst.requestFocus();
            return;

        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

             if(task.isSuccessful()) {
                 Toast.makeText(ResetPassword.this, "Check your email  to reset your Password", Toast.LENGTH_LONG).show();
                 Intent intent = new Intent(ResetPassword.this,Login.class);
                 startActivity(intent);

             }
             else{
                 Toast.makeText(ResetPassword.this, "Try again! Something went Wrong", Toast.LENGTH_LONG).show();
             }
            }
        });
    }
}
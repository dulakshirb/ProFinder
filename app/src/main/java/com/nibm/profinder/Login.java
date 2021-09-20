package com.nibm.profinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private ImageButton btn_backWelcome;
    private Button loginUser;
    private TextView resetPassword;
    private EditText email, password;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_backWelcome = findViewById(R.id.btn_backWelcome);
        btn_backWelcome.setOnClickListener(this);

        loginUser = (Button) findViewById(R.id.btn_loginUser);
        loginUser.setOnClickListener(this);

        email = (EditText)findViewById(R.id.txt_username);
        password = (EditText)findViewById(R.id.txt_password);

        mAuth = FirebaseAuth.getInstance();

        resetPassword = (TextView) findViewById(R.id.btn_forgetPassword);
        resetPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_backWelcome:
                startActivity(new Intent(this, Welcome.class));
                break;

            case R.id.btn_loginUser:
                userLogin();
                break;

            case R.id.btn_forgetPassword:
                startActivity(new Intent(this,ResetPassword.class));

        }
    }

    private void userLogin(){
        String userEmail = email.getText().toString().trim();
        String userPass = password.getText().toString().trim();

        if(userEmail.isEmpty()){
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }
        if(userPass.isEmpty()){
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }
        if(userPass.length()<6){
            password.setError("Minimum password length is 6 characters!");
            password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()) {
                        //redirect to profile
                       startActivity(new Intent(Login.this, Featured.class));
                    }
                    else
                    {
                        user.sendEmailVerification();
                        Toast.makeText(Login.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Login.this, "Failed to login! Please check your email & password!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
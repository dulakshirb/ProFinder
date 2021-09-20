package com.nibm.profinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProView extends AppCompatActivity {

    private CircleImageView img;
    TextView name, designation, bio, location, contactno, email, pimage;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_view);

        img = findViewById(R.id.img_dpProUpdate);
        name = findViewById(R.id.txt_nameProView);
        designation = findViewById(R.id.txt_desigProView);
        bio = findViewById(R.id.txt_bioProView);
        location = findViewById(R.id.txt_locationProView);
        contactno = findViewById(R.id.txt_contactNoView);
        email = findViewById(R.id.txt_emailProView);

        ref = FirebaseDatabase.getInstance().getReference().child("Users");

        String UsersKey = getIntent().getStringExtra("UsersKey");
        ref.child(UsersKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String proName = snapshot.child("name").getValue().toString();
                    String proDesignation = snapshot.child("designation").getValue().toString();
                    String proBio = snapshot.child("bio").getValue().toString();
                    String proLocation = snapshot.child("location").getValue().toString();
                    String proContactNo = snapshot.child("contactno").getValue().toString();
                    String proEmail = snapshot.child("email").getValue().toString();
                    String proImage = snapshot.child("pimage").getValue().toString();


                    name.setText(proName);
                    designation.setText(proDesignation);
                    bio.setText(proBio);
                    location.setText(proLocation);
                    contactno.setText(proContactNo);
                    email.setText(proEmail);
                    Picasso.get().load(proImage).into(img);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
package com.nibm.profinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private CircleImageView userDp;



    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();

        userDp = findViewById(R.id.img_dpProUpdate);

        final TextView uName = (TextView) findViewById(R.id.txt_nameProProfile);
        final TextView uDesignation = (TextView) findViewById(R.id.txt_desigProProfile);
        final TextView uBio = (TextView) findViewById(R.id.txt_bioProfile);
        final TextView uEmail = (TextView) findViewById(R.id.txt_emailProProfile);
        final TextView uContact = (TextView) findViewById(R.id.txt_contactNo);
        final TextView uLocation = (TextView) findViewById(R.id.txt_locationProfile);
        final TextView uKeywords = (TextView) findViewById(R.id.txt_keywordsProfile);
        final CircleImageView uImage = (CircleImageView)findViewById(R.id.img_dpProUpdate);

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Professional profile  = snapshot.getValue(Professional.class);

                if(profile != null){

                    String name = profile.name;
                     String designation = profile.designation;
                     String bio = profile.bio;
                     String email = profile.email;
                     String contact = profile.contactno;
                     String location = profile.location;
                     String keywords = profile.keywords;

                     uName.setText(name);
                     uDesignation.setText(designation);
                     uBio.setText(bio);
                     uContact.setText(contact);
                     uEmail.setText(email);
                     uLocation.setText(location);
                     uKeywords.setText(keywords);

                        String msg = snapshot.child("pimage").getValue(String.class);
                        Picasso.get().load(msg).into(userDp);
                }

//                Finder finderp  = snapshot.getValue(Finder.class);
//
//                if(finderp != null){
//
//                    String name = profile.name;
//
//
//                    uName.setText(name);
//
//
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this,"Something went wrong", Toast.LENGTH_LONG).show();
            }
        });





    }
}
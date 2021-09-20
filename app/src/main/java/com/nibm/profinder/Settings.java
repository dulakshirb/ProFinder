package com.nibm.profinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;
import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("unchecked")
public class Settings extends AppCompatActivity {

    private Button logout, update;
    private FirebaseUser user;
    private DatabaseReference reference;
    private CircleImageView userDp;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        logout = (Button) findViewById(R.id.btn_logoutPro);
        //chngpw = (Button) findViewById(R.id.btn_resetPassword);
        update = (Button) findViewById(R.id.btn_updatePro);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();
        userDp = findViewById(R.id.img_dpProUpdate);

        final TextView uName = (TextView) findViewById(R.id.txt_nameProUpdate);
        final TextView uDesignation = (TextView) findViewById(R.id.txt_desigProUpdate);
        final TextView uBio = (TextView) findViewById(R.id.txt_bioUpdate);
        final TextView uEmail = (TextView) findViewById(R.id.txt_emailProUpdate);
        final TextView uContact = (TextView) findViewById(R.id.txt_contactUpdate);
        final TextView uLocation = (TextView) findViewById(R.id.txt_locationUpdate);
        final TextView uKeywords = (TextView) findViewById(R.id.txt_keywordsUpdate);
        final CircleImageView uImage = (CircleImageView)findViewById(R.id.img_dpProUpdate);
        final TextView uCategory = (TextView) findViewById(R.id.txt_categoryProUpdate);

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
                    String category = profile.category;

                    uName.setText(name);
                    uDesignation.setText(designation);
                    uBio.setText(bio);
                    uContact.setText(contact);
                    uEmail.setText(email);
                    uLocation.setText(location);
                    uKeywords.setText(keywords);
                    uCategory.setText(category);

                    String msg = snapshot.child("pimage").getValue(String.class);
                    Picasso.get().load(msg).into(userDp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Settings.this,"Something went wrong", Toast.LENGTH_LONG).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namePro = uName.getText().toString().trim();
                String designationPro = uDesignation.getText().toString().trim();
                String bioPro = uBio.getText().toString().trim();
                String locationPro = uLocation.getText().toString().trim();
                String keywordsPro = uKeywords.getText().toString().trim();
                String contactNoPro = uContact.getText().toString().trim();
                String categoryPro = uCategory.getText().toString().trim();

                if(namePro.isEmpty()){
                    uName.setError("Name cannot Empty");
                    uName.requestFocus();
                    return;
                }

                if(designationPro.isEmpty()){
                    uDesignation.setError("Designation cannot Empty");
                    uDesignation.requestFocus();
                    return;
                }
                if(bioPro.isEmpty()){
                    uBio.setError("Bio cannot Empty");
                    uBio.requestFocus();
                    return;
                }
                if(locationPro.isEmpty()){
                    uLocation.setError("Location cannot Empty");
                    uLocation.requestFocus();
                    return;
                }
                if(keywordsPro.isEmpty()){
                    uKeywords.setError("Enter atleast one keyword");
                    uKeywords.requestFocus();
                    return;
                }
                if(contactNoPro.isEmpty()){
                    uContact.setError("Contact Number cannot Empty");
                    uContact.requestFocus();
                    return;
                }
                if(!Patterns.PHONE.matcher(contactNoPro).matches()){
                    uContact.setError("Contact Number cannot Empty");
                    uContact.requestFocus();
                    return;
                }

                HashMap hashMap = new HashMap();
                hashMap.put("name",namePro);
                hashMap.put("designation",designationPro);
                hashMap.put("category", categoryPro);
                hashMap.put("bio",bioPro);
                hashMap.put("location",locationPro);
                hashMap.put("contactno",contactNoPro);
                hashMap.put("keywords",keywordsPro);

                reference.child(userId).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Settings.this,"Update Successful",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Settings.this,Profile.class);
                            startActivity(intent);
                        }else
                            Toast.makeText(Settings.this,"Update Unsuccessful",Toast.LENGTH_LONG).show();
                    }
                });

            }

        });

//        chngpw.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Settings.this,ResetPassword.class);
//                startActivity(intent);
//            }
//        });


        logout.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Settings.this, Welcome.class));
            }
        }));
    }
}
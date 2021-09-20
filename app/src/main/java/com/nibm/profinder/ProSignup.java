package com.nibm.profinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.internal.$Gson$Preconditions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProSignup extends AppCompatActivity implements View.OnClickListener
{
    private EditText name, designation, bio, location, keywords, contactno, email, password, conPassword;
    private Button registerPro;
    Uri filePath = null;
    CircleImageView img;
    Bitmap bitmap;
    private Spinner spnCategories;

    private  final int PICK_IMAGE_GALLERY_CODE = 78;
    private  final int CAMERA_PERMISSION_REQUEST_CODE = 12345;
    private  final int CAMERA_PICTURE_REQUEST_CODE = 56789;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_signup);

        mAuth = FirebaseAuth.getInstance();

        registerPro = (Button) findViewById(R.id.btn_signupPro);
        registerPro.setOnClickListener(this);

        name = (EditText) findViewById(R.id.txt_namePro);
        designation = (EditText) findViewById(R.id.txt_desigPro);
        bio = (EditText) findViewById(R.id.txt_bio);
        contactno = (EditText) findViewById(R.id.txt_contactNo);
        location = (EditText) findViewById(R.id.txt_location);
        keywords = (EditText) findViewById(R.id.txt_keywords);
        email = (EditText) findViewById(R.id.txt_emailLogPro);
        password = (EditText) findViewById(R.id.txt_passPro);
        conPassword = (EditText) findViewById(R.id.txt_conPassPro);
        img=(CircleImageView) findViewById(R.id.img_dpPro);
        img.setOnClickListener(this);
        spnCategories = (Spinner)findViewById(R.id.spin_category);
        spnCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_dpPro:
                imageDpPro();
                break;

            case R.id.btn_signupPro:
                registerUser();
                break;
        }
    }

    private void imageDpPro() {
        Dexter.withActivity(ProSignup.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response)
                    {
                        showImageSelectedDialog();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImageSelectedDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image");
        builder.setMessage("Please select an option");

        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkCameraPermission();
                dialog.dismiss();
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectFromGallery();
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void checkCameraPermission(){
        if(ContextCompat.checkSelfPermission(ProSignup.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(ProSignup.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ProSignup.this, new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, CAMERA_PERMISSION_REQUEST_CODE);
        }else {
            openCamera();
        }
    }

    private void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, CAMERA_PICTURE_REQUEST_CODE);
        }
    }

    private void selectFromGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            if (data == null || data.getData() == null)
                return;
            try {
                filePath = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                img.setImageBitmap(bitmap);

            } catch (Exception ex){}
        }else if(requestCode == CAMERA_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            img.setImageBitmap(bitmap);
            filePath = getImageUri(getApplicationContext(), bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private Uri getImageUri(Context context, Bitmap bitmap){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", null);
        return Uri.parse(path);
    }


    private void registerUser() {
        String namePro = name.getText().toString().trim();
        String designationPro = designation.getText().toString().trim();
        String bioPro = bio.getText().toString().trim();
        String locationPro = location.getText().toString().trim();
        String keywordsPro = keywords.getText().toString().trim();
        String contactNoPro = contactno.getText().toString().trim();
        String emailPro =  email.getText().toString().trim();
        String passwordPro = password.getText().toString().trim();
        String conPassPro = conPassword.getText().toString().trim();
        String categPro = spnCategories.getSelectedItem().toString();

        if(namePro.isEmpty()){
            name.setError("Name cannot Empty");
            name.requestFocus();
            return;
        }
        if(categPro.equals("select")){
            Toast.makeText(this, "Please select your category", Toast.LENGTH_SHORT).show();
            spnCategories.requestFocus();
            return;
        }
        if(designationPro.isEmpty()){
            designation.setError("Designation cannot Empty");
            designation.requestFocus();
            return;
        }
        if(bioPro.isEmpty()){
            bio.setError("Bio cannot Empty");
            bio.requestFocus();
            return;
        }
        if(locationPro.isEmpty()){
            location.setError("Location cannot Empty");
            location.requestFocus();
            return;
        }
        if(keywordsPro.isEmpty()){
            keywords.setError("Enter atleast one keyword");
            keywords.requestFocus();
            return;
        }
        if(contactNoPro.isEmpty()){
            contactno.setError("Contact Number cannot Empty");
            contactno.requestFocus();
            return;
        }
        if(!Patterns.PHONE.matcher(contactNoPro).matches()){
            contactno.setError("Contact Number cannot Empty");
            contactno.requestFocus();
            return;
        }
        if(emailPro.isEmpty()){
            email.setError("Email cannot Empty");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailPro).matches()){
            email.setError("Please provide valid email");
            email.requestFocus();
            return;
        }

        if(passwordPro.isEmpty()){
            password.setError("Password cannot Empty");
            password.requestFocus();
            return;
        }
        if(conPassPro.isEmpty()){
            conPassword.setError("Confirm password cannot Empty");
            conPassword.requestFocus();
            return;
        }
        if(passwordPro.length()<6){
            password.setError("Minimum password Length should be 6 characters");
            password.requestFocus();
            return;
        }
        if(!passwordPro.equals(conPassPro)){
            conPassword.setError("Confirm password does not match with Password");
            conPassword.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(emailPro,passwordPro)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            FirebaseStorage storage=FirebaseStorage.getInstance();
                            final StorageReference uploader=storage.getReference("Image1"+new Random().nextInt(50));

                            uploader.putFile(filePath)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                        {
                                            uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri){

                                                    FirebaseDatabase db=FirebaseDatabase.getInstance();
                                                    DatabaseReference root=db.getReference("Users");

                                                    String typePro = "Pro";
                                                    Professional pro = new Professional(typePro, namePro, categPro, designationPro, bioPro, locationPro, keywordsPro, contactNoPro, emailPro, uri.toString());

                                                    FirebaseDatabase.getInstance().getReference("Users")
                                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                            .setValue(pro).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                Toast.makeText(ProSignup.this,"Registration Successful",Toast.LENGTH_LONG).show();
                                                                name.setText("");
                                                                designation.setText("");
                                                                bio.setText("");
                                                                location.setText("");
                                                                keywords.setText("");
                                                                contactno.setText("");
                                                                email.setText("");
                                                                password.setText("");
                                                                conPassword.setText("");
                                                                img.setImageResource(R.drawable.user_img);

                                                                Intent intent = new Intent(ProSignup.this,Login.class);
                                                                startActivity(intent);


                                                            }
                                                            else{
                                                                Toast.makeText(ProSignup.this,"Registration Unsuccessful Please Try again",Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    });
                        }
                        else{
                            Toast.makeText(ProSignup.this,"Registration Unsuccessful Please Try again",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}
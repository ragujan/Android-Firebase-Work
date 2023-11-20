package com.rag.firebaseauthentication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rag.firebaseauthentication.databinding.ActivityHomeBinding;
import com.rag.firebaseauthentication.databinding.ActivityMainBinding;
import com.rag.firebaseauthentication.util.ui.ListenerUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            binding.textView.setText("User is there " + mAuth.getCurrentUser().getEmail());
            binding.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
        if (currentUser == null) {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        }
        binding.addTestData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
                addNestedData();
            }
        });
        binding.uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, UploadImage.class);
                startActivity(intent);
            }
        });
        ListenerUtil.onClickBtnIntent(binding.button,this, UploadImage.class);
    }

    private void addData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> city = new HashMap<>();
        city.put("name", "Los Angeles");
        city.put("state", "CA");
        city.put("country", "USA");

        db.collection("cities").document("LA")
                .set(city)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        ;


    }
    public void addNestedData(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> docData = new HashMap<>();
        docData.put("stringExample", "Hello world!");
        docData.put("booleanExample", true);
        docData.put("numberExample", 3.14159265);
        docData.put("dateExample", new Timestamp(new Date()));
        docData.put("listExample", Arrays.asList(1, 2, 3));
        docData.put("nullExample", null);

        Map<String, Object> nestedData = new HashMap<>();
        nestedData.put("a", 5);
        nestedData.put("b", true);

        docData.put("objectExample", nestedData);

        db.collection("data").document("one")
                .set(docData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
}
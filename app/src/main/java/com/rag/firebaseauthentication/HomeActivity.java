package com.rag.firebaseauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rag.firebaseauthentication.databinding.ActivityHomeBinding;
import com.rag.firebaseauthentication.databinding.ActivityMainBinding;

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
        if(currentUser != null){
          binding.textView.setText("User is there");
        }
    }
}
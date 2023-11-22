package com.rag.firebaseauthentication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rag.firebaseauthentication.domain.FoodDomain;
import com.rag.firebaseauthentication.util.Constants;
import com.rag.firebaseauthentication.util.firebaseUtil.FoodListRetrieval;

import java.util.LinkedList;
import java.util.List;

public class ViewFoodItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food_items);
        viewFoodItems();
    }

    @SuppressLint("CheckResult")
    public void viewFoodItems(){
        FoodListRetrieval.getAllFoods()
                .subscribe(
                        resultsSet->{

                            if(resultsSet.get(Constants.DATA_RETRIEVAL_STATUS).equals("Success")){
                                List<FoodDomain> foodDomainList = (List<FoodDomain>) resultsSet.get("foodDomainList");
                                foodDomainList.forEach(e-> System.out.println("food title is "+e.getTitle()));

                            }
                        },
                        throwable -> {

                        }
                );

    }
}
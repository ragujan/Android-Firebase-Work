package com.rag.firebaseauthentication.util.firebaseUtil;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.rag.firebaseauthentication.domain.FoodDomainRetrieval;
import com.rag.firebaseauthentication.util.Constants;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FoodListRetrieval {
    public static final Single<Map<String, Object>> getAllFoods() {
        return Single.<Map<String, Object>>create(emitter -> {

            List<FoodDomainRetrieval> foodDomainList = new LinkedList<>();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, Object> dataResuts = new HashMap<>();
            db.collection(Constants.FAST_FOOD_ITEMS_COLLECTION_NAME).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            FoodDomainRetrieval foodDomainRetrieval = (FoodDomainRetrieval) document.toObject(FoodDomainRetrieval.class);
                            String id = document.getId();
                            foodDomainRetrieval.setUniqueId(id);
                            foodDomainList.add(foodDomainRetrieval);
                            final DocumentReference docRef = document.getReference();

                            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                                    if (e != null) {
                                        Log.w(TAG, "Listen failed.", e);
                                        return;
                                    }

                                    if (snapshot != null && snapshot.exists()) {
//                                        Log.d(TAG, "Current data hi hi:  " + snapshot.getData());
                                        FoodDomainRetrieval foodDomainRetrieval = (FoodDomainRetrieval) snapshot.toObject(FoodDomainRetrieval.class);
//                                        String id = snapshot.getId();
//                                        System.out.println("id is " + id);
//                                        System.out.println("title is " + foodDomainRetrieval.getTitle());
//                                        System.out.println("image url is " + foodDomainRetrieval.getImageUrl());
//                                        foodDomainRetrieval.setUniqueId(id);
//                                        foodDomainList.add(foodDomainRetrieval);

                                    } else {
//                                        Log.d(TAG, "Current data: null");
                                    }

                                }

                            });
                        }
                        dataResuts.put(Constants.DATA_RETRIEVAL_STATUS, "Success");
                        dataResuts.put("foodDomainList", foodDomainList);

                        foodDomainList.forEach(e-> System.out.println("name is "+e.getTitle()));

//                        System.out.println("hey hey this is from some where else ");
                        emitter.onSuccess(dataResuts);
                    } else {
                        dataResuts.put(Constants.DATA_RETRIEVAL_STATUS, "fail");
                        emitter.onSuccess(dataResuts);

                    }
                }
            });


        }).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread());
    }
}

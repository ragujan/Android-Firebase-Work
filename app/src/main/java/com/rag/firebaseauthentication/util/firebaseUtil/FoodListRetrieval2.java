package com.rag.firebaseauthentication.util.firebaseUtil;

import static android.content.ContentValues.TAG;

import android.util.Log;

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
import com.rag.firebaseauthentication.domain.FoodDomainRetrieval;
import com.rag.firebaseauthentication.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FoodListRetrieval2 {
    public static final Single<Map<String, Object>> getAllFoods(AppCompatActivity activity) {
        return Single.<Map<String, Object>>create(emitter -> {

            List<FoodDomainRetrieval> foodDomainList = new LinkedList<>();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, Object> dataResuts = new HashMap<>();

            db.collection(Constants.FAST_FOOD_ITEMS_COLLECTION_NAME)
                    .addSnapshotListener(
                            new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value,
                                                    @Nullable FirebaseFirestoreException e) {
                                    if (e != null) {
                                        Log.w(TAG, "Listen failed.", e);
                                        return;
                                    }

                                    List<String> cities = new ArrayList<>();
                                    for (QueryDocumentSnapshot document : value) {
                                        FoodDomainRetrieval foodDomainRetrieval = (FoodDomainRetrieval) document.toObject(FoodDomainRetrieval.class);
                                        String id = document.getId();
                                        foodDomainRetrieval.setUniqueId(id);
                                        foodDomainList.add(foodDomainRetrieval);


                                    }
                                    dataResuts.put(Constants.DATA_RETRIEVAL_STATUS, "Success");
                                    dataResuts.put("foodDomainList", foodDomainList);

                                    System.out.println("hey hey this is from some where else ");
                                    emitter.onSuccess(dataResuts);
                                }
                            }

                    );


        }).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread());
    }
}

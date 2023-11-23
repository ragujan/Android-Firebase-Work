package com.rag.firebaseauthentication.util.firebaseUtil;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rag.firebaseauthentication.domain.FoodDomainRetrieval;
import com.rag.firebaseauthentication.util.Constants;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FoodListRetrievalV2 {
    public static final Single<Map<String, Object>> getAllFoods( ) {
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
                                    for (QueryDocumentSnapshot document : value) {
                                        FoodDomainRetrieval foodDomainRetrieval = (FoodDomainRetrieval) document.toObject(FoodDomainRetrieval.class);
                                        String id = document.getId();
                                        foodDomainRetrieval.setUniqueId(id);
                                        foodDomainList.add(foodDomainRetrieval);
                                    }
                                    dataResuts.put(Constants.DATA_RETRIEVAL_STATUS, "Success");
                                    dataResuts.put("foodDomainList", foodDomainList);
//                                    adapter.updateData(foodDomainList);

                                    emitter.onSuccess(dataResuts);
                                }
                            }

                    );


        }).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread());
    }
}

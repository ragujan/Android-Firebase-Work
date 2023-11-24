package com.rag.firebaseauthentication.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.rag.firebaseauthentication.fragments.FoodItemDisplayFragment;
import com.rag.firebaseauthentication.R;
import com.rag.firebaseauthentication.adapters.AllFoodListAdapter;
import com.rag.firebaseauthentication.domain.FoodDomainRetrieval;
import com.rag.firebaseauthentication.helpers.FoodItemRetrievelViewModelV2;
import com.rag.firebaseauthentication.util.Constants;
import com.rag.firebaseauthentication.util.firebaseUtil.FoodListRetrievalV3;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ViewFoodItemsActivity extends AppCompatActivity {
    private FoodItemRetrievelViewModelV2 viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food_items);
        viewModel = new ViewModelProvider(this).get(FoodItemRetrievelViewModelV2.class);
        viewFoodItems();
        loadFragment(new FoodItemDisplayFragment());
    }

    @SuppressLint("CheckResult")
    public void viewFoodItems() {
        AllFoodListAdapter recyclerViewAdapter = new AllFoodListAdapter(new LinkedList<>());
        FoodListRetrievalV3.getAllFoods(recyclerViewAdapter)
                .subscribe(
                        resultsSet -> {

                            if (resultsSet.get(Constants.DATA_RETRIEVAL_STATUS).equals("Success")) {
                                List<FoodDomainRetrieval> foodDomainList = (List<FoodDomainRetrieval>) resultsSet.get("foodDomainList");
                                AllFoodListAdapter adapter = (AllFoodListAdapter) resultsSet.get("adapter");
                                Map<String, Object> map = new HashMap<>();
                                map.put("foodDomainList", foodDomainList);
                                map.put("adapter", adapter);
                                viewModel.setFoodItemsRetrieved(map);
                            }
                        },
                        throwable -> {

                        }
                );

    }

    private void loadFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.foodItemViewFrameLayout, fragment.getClass(), null)
                .setReorderingAllowed(true)
                .commit();
    }
}
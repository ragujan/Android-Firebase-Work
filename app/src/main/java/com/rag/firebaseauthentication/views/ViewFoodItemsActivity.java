package com.rag.firebaseauthentication.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.rag.firebaseauthentication.databinding.ActivityViewFoodItemsBinding;
import com.rag.firebaseauthentication.domain.FastFoodCategory;
import com.rag.firebaseauthentication.fragments.FoodItemDisplayFragment;
import com.rag.firebaseauthentication.R;
import com.rag.firebaseauthentication.adapters.AllFoodListAdapter;
import com.rag.firebaseauthentication.domain.FoodDomainRetrieval;
import com.rag.firebaseauthentication.helpers.FoodItemRetrievelViewModelV2;
import com.rag.firebaseauthentication.util.Constants;
import com.rag.firebaseauthentication.util.SortByOptions;
import com.rag.firebaseauthentication.util.firebaseUtil.FoodListRetrievalV3;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ViewFoodItemsActivity extends AppCompatActivity {
    private FoodItemRetrievelViewModelV2 viewModel;
    ActivityViewFoodItemsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewFoodItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(FoodItemRetrievelViewModelV2.class);
        viewFoodItems();
        loadCategorySpinner();
        binding.sortByCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String selectedItem = parentView.getSelectedItem().toString();

                if (selectedItem.equals(SortByOptions.ALL.toString())) {
                    viewFoodItems();
                }
                if (selectedItem.equals(SortByOptions.AVAILABLE.toString())) {
                    viewAvailableFoodItems(true);
                }
                if (selectedItem.equals(SortByOptions.UNAVAILABLE.toString())) {
                    viewAvailableFoodItems(false);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


//        binding.showAllBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewFoodItems();
//            }
//        });
//
//        binding.showAvailableOnlyBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewAvailableFoodItems(true);
//            }
//        });
//        binding.showUnavailableOnlyBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewAvailableFoodItems(false);
//            }
//        });

        loadFragment(new FoodItemDisplayFragment());
    }

    @SuppressLint("CheckResult")
    public void viewAvailableFoodItems(boolean status) {
        AllFoodListAdapter recyclerViewAdapter = new AllFoodListAdapter(new LinkedList<>());
        FoodListRetrievalV3.getAvailableFoodItemsOnly(recyclerViewAdapter, status)
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

    public void loadCategorySpinner() {
        List<String> categoryNames = Stream.of(SortByOptions.values()).map(SortByOptions::name).collect(Collectors.toList());

        Spinner spinner = binding.sortByCategorySpinner;

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryNames);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }


}
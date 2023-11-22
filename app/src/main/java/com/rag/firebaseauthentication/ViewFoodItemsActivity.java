package com.rag.firebaseauthentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.rag.firebaseauthentication.domain.FoodDomain;
import com.rag.firebaseauthentication.helpers.FoodItemRetrievelViewModel;
import com.rag.firebaseauthentication.helpers.ItemViewModel;
import com.rag.firebaseauthentication.util.Constants;
import com.rag.firebaseauthentication.util.firebaseUtil.FoodListRetrieval;

import java.util.List;

public class ViewFoodItemsActivity extends AppCompatActivity {
    private FoodItemRetrievelViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food_items);
        viewModel = new ViewModelProvider(this).get(FoodItemRetrievelViewModel.class);
        viewFoodItems();
        loadFragment(new FoodItemDisplayFragment());
    }

    @SuppressLint("CheckResult")
    public void viewFoodItems(){
        FoodListRetrieval.getAllFoods()
                .subscribe(
                        resultsSet->{

                            if(resultsSet.get(Constants.DATA_RETRIEVAL_STATUS).equals("Success")){
                                List<FoodDomain> foodDomainList = (List<FoodDomain>) resultsSet.get("foodDomainList");
                                foodDomainList.forEach(e-> System.out.println("food title is "+e.getTitle()));

                                viewModel.setFoodItemsRetrieved(foodDomainList);
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
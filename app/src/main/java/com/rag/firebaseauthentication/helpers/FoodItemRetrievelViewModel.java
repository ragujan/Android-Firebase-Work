package com.rag.firebaseauthentication.helpers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rag.firebaseauthentication.domain.FoodDomain;

import java.util.List;

public class FoodItemRetrievelViewModel extends ViewModel {
    private final MutableLiveData<List<FoodDomain>> foodItemsRetrieved = new MutableLiveData<>();

    public void setFoodItemsRetrieved(List<FoodDomain> data){
        foodItemsRetrieved.postValue(data);
    }

    public LiveData<List<FoodDomain>> getFoodItemRetrieved(){
        return foodItemsRetrieved;
    }



}

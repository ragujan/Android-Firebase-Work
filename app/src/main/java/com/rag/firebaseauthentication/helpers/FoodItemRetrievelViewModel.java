package com.rag.firebaseauthentication.helpers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rag.firebaseauthentication.domain.FoodDomain;
import com.rag.firebaseauthentication.domain.FoodDomainRetrieval;

import java.util.List;

public class FoodItemRetrievelViewModel extends ViewModel {
    private final MutableLiveData<List<FoodDomainRetrieval>> foodItemsRetrieved = new MutableLiveData<>();

    public void setFoodItemsRetrieved(List<FoodDomainRetrieval> data){
        foodItemsRetrieved.postValue(data);
    }

    public LiveData<List<FoodDomainRetrieval>> getFoodItemRetrieved(){
        return foodItemsRetrieved;
    }



}

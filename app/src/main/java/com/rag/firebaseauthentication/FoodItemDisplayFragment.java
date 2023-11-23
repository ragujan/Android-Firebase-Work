package com.rag.firebaseauthentication;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rag.firebaseauthentication.adapters.AllFoodListAdapter;
import com.rag.firebaseauthentication.databinding.FragmentFoodItemDisplayBinding;
import com.rag.firebaseauthentication.domain.FoodDomainRetrieval;
import com.rag.firebaseauthentication.helpers.FoodItemRetrievelViewModel;
import com.rag.firebaseauthentication.helpers.FoodItemRetrievelViewModelV2;
import com.rag.firebaseauthentication.util.Constants;

import java.util.List;


public class FoodItemDisplayFragment extends Fragment {
    private RecyclerView recyclerView;
    private AllFoodListAdapter recyclerViewAdapter;
    FragmentFoodItemDisplayBinding binding;
    private FoodItemRetrievelViewModelV2 itemViewModel;

    public FoodItemDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("on create, step 1");

    }

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFoodItemDisplayBinding.inflate(getLayoutInflater());
        itemViewModel = new ViewModelProvider(requireActivity()).get(FoodItemRetrievelViewModelV2.class);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        itemViewModel.getFoodItemRetrieved().observe(getViewLifecycleOwner(), item -> {

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
            recyclerView = getActivity().findViewById(R.id.recyclerViewFoodItemList);
            recyclerView.setLayoutManager(gridLayoutManager);


            List<FoodDomainRetrieval> foodDomainRetrievalList = (List<FoodDomainRetrieval>) item.get("foodDomainList");
            recyclerViewAdapter = (AllFoodListAdapter) item.get("adapter");
            recyclerView.setAdapter(recyclerViewAdapter);


        });

//        recyclerViewAdapter = new AllFoodListAdapter();
//        FoodListRetrieval.getAllFoods()
//                .subscribe(
//                        resultsSet -> {
//
//                            if (resultsSet.get(Constants.DATA_RETRIEVAL_STATUS).equals("Success")) {
//                                List<FoodDomainRetrieval> foodDomainList = (List<FoodDomainRetrieval>) resultsSet.get("foodDomainList");
//
//
//                                foodDomainList.forEach(e-> System.out.println("food title is rag "+e.getTitle()));
//
//                                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
//                                recyclerView = getActivity().findViewById(R.id.recyclerViewFoodItemList);
//                                recyclerView.setLayoutManager(gridLayoutManager);
//
//
//                                recyclerViewAdapter = new AllFoodListAdapter(foodDomainList);
//
//                                recyclerView.setAdapter(recyclerViewAdapter);
//                            }
//                        },
//                        throwable -> {
//
//                        }
//                );

        return binding.getRoot();
    }


}
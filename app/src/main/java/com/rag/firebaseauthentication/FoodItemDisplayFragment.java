package com.rag.firebaseauthentication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rag.firebaseauthentication.adapters.AllFoodListAdapter;
import com.rag.firebaseauthentication.databinding.FragmentFoodItemDisplayBinding;
import com.rag.firebaseauthentication.helpers.FoodItemRetrievelViewModel;
import com.rag.firebaseauthentication.helpers.ItemViewModel;


public class FoodItemDisplayFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    FragmentFoodItemDisplayBinding binding;
    private FoodItemRetrievelViewModel itemViewModel;
    public FoodItemDisplayFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("on create, step 1");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFoodItemDisplayBinding.inflate(getLayoutInflater());
        itemViewModel = new ViewModelProvider(requireActivity()).get(FoodItemRetrievelViewModel.class);
        System.out.println("on create view, step 2");
        itemViewModel.getFoodItemRetrieved().observe(getViewLifecycleOwner(),item-> {

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2, RecyclerView.VERTICAL,false);
            recyclerView = getActivity().findViewById(R.id.recyclerViewFoodItemList);
            recyclerView.setLayoutManager(gridLayoutManager);

            recyclerViewAdapter = new AllFoodListAdapter(item);

            recyclerView.setAdapter(recyclerViewAdapter);


        });

        return binding.getRoot();
    }


}
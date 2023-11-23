package com.rag.firebaseauthentication;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.rag.firebaseauthentication.helpers.FoodItemRetrievelViewModel;
import com.rag.firebaseauthentication.helpers.ItemViewModel;
import com.rag.firebaseauthentication.util.Constants;


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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.FAST_FOOD_ITEMS_COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                final DocumentReference docRef = document.getReference();

                                docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot snapshot,
                                                        @Nullable FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Log.w(TAG, "Listen failed.", e);
                                            return;
                                        }

                                        if (snapshot != null && snapshot.exists()) {
                                            Log.d(TAG, "Current data: " + snapshot.getData());
                                        } else {
                                            Log.d(TAG, "Current data: null");
                                        }
                                    }
                                });

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        itemViewModel.getFoodItemRetrieved().observe(getViewLifecycleOwner(), item -> {

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
            recyclerView = getActivity().findViewById(R.id.recyclerViewFoodItemList);
            recyclerView.setLayoutManager(gridLayoutManager);



            recyclerViewAdapter = new AllFoodListAdapter(item);

            recyclerView.setAdapter(recyclerViewAdapter);


        });


        return binding.getRoot();
    }


}
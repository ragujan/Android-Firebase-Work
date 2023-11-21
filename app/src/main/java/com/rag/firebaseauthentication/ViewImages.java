package com.rag.firebaseauthentication;

import static com.rag.firebaseauthentication.util.Constants.foodImageFolderPath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.rag.firebaseauthentication.adapters.ImageListAdapter;
import com.rag.firebaseauthentication.databinding.ActivityViewImagesBinding;
import com.rag.firebaseauthentication.domain.ImageListDomain;
import com.rag.firebaseauthentication.util.ListImageUrls;
import com.rag.firebaseauthentication.util.ListenerUtil;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class ViewImages extends AppCompatActivity {

    List<String> imageNames = new LinkedList<>();
    ActivityViewImagesBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewImagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String url =
                "https://firebasestorage.googleapis.com/v0/b/fir-authenticationtest-af44e.appspot.com/o/foodImages%2Fai_zoom_pic.jpg?alt=media&token=281a1603-600c-49d4-8be6-877b4c7d8146";
        getImages();
    }
   public void getImages(){
       ListImageUrls.getAllImageUrls().subscribe(

             imageNames->{
                 imageNames.stream().forEach(e-> System.out.println("urls are "+e));
                 GridLayoutManager gridLayoutManager = new GridLayoutManager(ViewImages.this, 2, RecyclerView.VERTICAL,false);
                 recyclerView  = findViewById(R.id.recyclerViewListAllImages);
                 recyclerView.setLayoutManager(gridLayoutManager);

                 List<ImageListDomain> imageListDomainList = new LinkedList<>();

                 imageNames.forEach(e->imageListDomainList.add(new ImageListDomain(e)));


                recyclerViewAdapter = new ImageListAdapter(imageListDomainList);
                recyclerView.setAdapter(recyclerViewAdapter);



             }

       );
   }
}
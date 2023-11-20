package com.rag.firebaseauthentication;

import static com.rag.firebaseauthentication.util.Constants.foodImageFolderPath;
import static com.rag.firebaseauthentication.util.UniqueNameGenerationFirebase.observeUniqueName;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rag.firebaseauthentication.databinding.ActivityUploadImageBinding;
import com.rag.firebaseauthentication.util.UploadImageFirebase;

import java.io.ByteArrayOutputStream;

public class UploadImage extends AppCompatActivity {


    ActivityUploadImageBinding binding;
    String uniqueImageName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new PickVisualMedia(), uri -> {
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
                    if (uri != null) {
                        Log.d("PhotoPicker", "Selected URI: " + uri);
                        binding.chosenImage.setImageURI(uri);
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });


        binding.chooseImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch the photo picker and let the user choose only images.
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
                observeUniqueName().subscribe(
                        uniqueName -> {
                            System.out.println("uniqueName is " + uniqueName);
                            uniqueImageName = uniqueName;

                        },
                        throwable -> {

                        }
                );

            }
        });
        binding.UploadFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!uniqueImageName.equals("") || binding.chosenImage.getDrawable() != null) {
                    UploadImageFirebase.uploadImage(binding.chosenImage,uniqueImageName)
                            .subscribe(
                                    uploadStatus -> {
                                        if(uploadStatus){
                                            binding.chosenImage.setImageResource(0);
                                            uniqueImageName = "";
                                        }
                                    },
                                    throwable -> {

                                    }
                            );
                }else{
                    Toast.makeText(UploadImage.this, "Invalie details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void uploadImage(ImageView imageView, String uniqueImageName) {


    }



    public void imageChoose() {


    }
}
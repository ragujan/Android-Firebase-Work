package com.rag.firebaseauthentication;

import static com.rag.firebaseauthentication.util.ui.Constants.foodImageFolderPath;
import static com.rag.firebaseauthentication.util.ui.ImageUploadWork.observeUniqueName;

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
import com.rag.firebaseauthentication.util.ui.ImageUploadWork;

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

                if (!uniqueImageName.equals("")) {
                    uploadImage(binding.chosenImage, uniqueImageName);
                }
            }
        });

    }

    public void uploadImage(ImageView imageView, String uniqueImageName) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference myImageRef = storageRef.child(foodImageFolderPath + "/"+uniqueImageName);


        UploadTask uploadTask = myImageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Toast.makeText(UploadImage.this, "Upload success", Toast.LENGTH_SHORT).show();

                System.out.println("uploaded file lists");
//                Generation.generateFoodImageName();

            }
        });
    }

    public void uploadImageTest() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference spaceRef = storageRef.child("images/space.jpg");
        System.out.println("hey hey");
// Points to the root reference
        storageRef = storage.getReference();

// Points to "images"
        StorageReference imagesRef = storageRef.child("images");

// Points to "images/space.jpg"
// Note that you can use variables to create child values
        String fileName = "space.jpg";
        spaceRef = imagesRef.child(fileName);

// File path is "images/space.jpg"
        String path = spaceRef.getPath();

// File name is "space.jpg"
        String name = spaceRef.getName();

// Points to "images"
        imagesRef = spaceRef.getParent();
        System.out.println("path is " + path);

    }

    public void imageChoose() {


    }
}
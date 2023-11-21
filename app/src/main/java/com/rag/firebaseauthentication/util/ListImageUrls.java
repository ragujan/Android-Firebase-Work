package com.rag.firebaseauthentication.util;

import static com.rag.firebaseauthentication.util.Constants.foodImageFolderPath;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ListImageUrls {
    public static Single<List<Uri>> getAllImageUrls() {
        return Single.<List<Uri>>create(emitter -> {
            List<String> imageNames = new LinkedList<>();
            List<Uri> imageUrls = new LinkedList<>();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference listRef = storage.getReference().child(foodImageFolderPath);
            listRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {

                @Override
                public void onSuccess(ListResult listResult) {


                    for (StorageReference item : listResult.getItems()) {
                        imageNames.add(item.getName());
                    }
                    Observable.fromIterable(imageNames)
                            .flatMapSingle(imageName->getImageUrl(storageRef,imageName))
                            .toList()
                            .subscribe(
                               urls->{
                                   imageUrls.addAll(urls);
                                   emitter.onSuccess(imageUrls);
                               },
                               throwable -> emitter.onError(throwable)
                            );

//                    for (String imageName : imageNames) {
//                        final StorageReference storageReference = storageRef.child("foodImages/"+imageName);
//                          storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                              @Override
//                              public void onSuccess(Uri uri) {
//                                  Uri myUri = uri;
//                                  Log.d("Test uri ",myUri.toString());
//
//
//                              }
//                          });
//                    }

                }
            });


        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    private static Single<Uri> getImageUrl(StorageReference storageRef, String imageName) {
        String path = foodImageFolderPath + "/" + imageName;
        final StorageReference myRef = storageRef.child("foodImages/"+imageName);

        return Single.create(emitter -> {
            myRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
//                    System.out.println(uri.getHost() + " " + uri.getEncodedPath());
                    emitter.onSuccess(uri);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    emitter.onError(exception);
                }
            });
        });
    }
}

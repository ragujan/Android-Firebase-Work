package com.rag.firebaseauthentication.util.ui;

import static com.rag.firebaseauthentication.util.ui.Constants.foodImageFolderPath;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ImageUploadWork {

    public static Single<String> getUniqueImageName() {
        return Single.<String>create(emitter -> {
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference listRef = storage.getReference().child(foodImageFolderPath);

                    String generatedName = generateString();
                    listRef.listAll()
                            .addOnSuccessListener(listResult -> {
                                List<String> names = new LinkedList<>();

                                for (StorageReference prefix : listResult.getPrefixes()) {
                                    // All the prefixes under listRef.
                                    // You may call listAll() recursively on them.
                                }

                                for (StorageReference item : listResult.getItems()) {

                                    names.add(item.getName());
                                }

                                boolean isNameUnique = true;

                                String newName = generateString();
                                if (names.contains(newName)) {
                                    isNameUnique = false;
                                }
                                while (!isNameUnique) {
                                    System.out.println("hey");
                                    if (names.contains(newName)) {
                                        newName = generateString();
                                    } else {
                                        isNameUnique = true;
                                    }
                                }
                                System.out.println("list size is " + names.size() + " " + names.get(0));
                                System.out.println("new name is " + newName);

                                emitter.onSuccess(newName);
                            })
                            .addOnFailureListener(emitter::onError);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static List<String> getNamesFirebase() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference listRef = storage.getReference().child(foodImageFolderPath);

        List<String> names = new LinkedList<>();
        listRef.listAll()
                .addOnSuccessListener(listResult -> {

                    for (StorageReference prefix : listResult.getPrefixes()) {
                        // All the prefixes under listRef.
                        // You may call listAll() recursively on them.
                    }

                    for (StorageReference item : listResult.getItems()) {

                        names.add(item.getName());
                    }

                });

        return names;
    }


    private String generateRandomString(List<String> existingNames, String baseImageName) {
        Random random = new Random();
        String randomImageName;

        do {
            // Generate a random suffix
            String randomSuffix = String.valueOf(random.nextInt(1000));

            // Combine the base name and suffix to create a random string
            randomImageName = baseImageName + "_" + randomSuffix;

            // Check if the generated name is not in the existing list; if not, return it
        } while (existingNames.contains(randomImageName));

        return randomImageName;
    }

    public static Single<String> observeUniqueName() {

        Single<String> single = getUniqueImageName();

        return single;
    }

    private static String generateString() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        int n = 8;
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

}

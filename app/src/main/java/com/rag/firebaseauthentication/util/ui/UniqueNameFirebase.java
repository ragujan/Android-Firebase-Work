package com.rag.firebaseauthentication.util.ui;

import static com.rag.firebaseauthentication.util.ui.Constants.foodImageFolderPath;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class UniqueNameFirebase {


    public Single<List<String>> getImageNames() {
        return Single.create(emitter -> {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference listRef = storage.getReference().child(foodImageFolderPath);

            listRef.listAll()
                    .addOnSuccessListener(listResult -> {
                        List<String> names = new ArrayList<>();

                        for (StorageReference item : listResult.getItems()) {
                            names.add(item.getName());
                        }

                        emitter.onSuccess(names);
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    //    public Observable<String> processImages() {
//        return getImageNames()
//                .toObservable()
//                .flatMapIterable(imageNames -> imageNames)
//                .map(randomImageName -> generateRandomString(imageNames, randomImageName));
//
//    }
    public Observable<String> processImages() {
        return getImageNames()
                .toObservable()
                .flatMapIterable(imageNames -> imageNames)
                .map(this::generateRandomString);
    }
    private String generateRandomString(List<String> existingNames, String baseImageName) {
        Random random = new Random();
        String randomImageName;

        do {
            // Generate a random suffix
            String randomSuffix = String.valueOf(random.nextInt(1000));

            // Combine the base name and suffix to create a unique random string
            randomImageName = baseImageName + "_" + randomSuffix;

            // Check if the generated name is unique; if not, regenerate
        } while (existingNames.contains(randomImageName));

        return randomImageName;
    }

    private String generateRandomString(String baseImageName) {
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

    private String generateRandomString(List<String> imageNames) {
        Random random = new Random();
        String randomImageName;

        do {
            // Generate a random index
            int randomIndex = random.nextInt(imageNames.size());
            // Use the selected image name as a base for the random string
            String baseImageName = imageNames.get(randomIndex);

            // Generate a random suffix
            String randomSuffix = String.valueOf(random.nextInt(1000));

            // Combine the base name and suffix to create a unique random string
            randomImageName = baseImageName + "_" + randomSuffix;

            // Check if the generated name is unique; if not, regenerate
        } while (imageNames.contains(randomImageName));

        return randomImageName;
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

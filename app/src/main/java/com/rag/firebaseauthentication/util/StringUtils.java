package com.rag.firebaseauthentication.util;

import java.util.Arrays;
import java.util.List;

public class StringUtils {
    public static String[] getImageUniqueName(String imageUrl) {

        String[] pathNameArray = new String[2];

        String FOOD_IMAGE_FOLDER_PATH = "foodImages";
        try {
            String[] parts = imageUrl.split("/");
            List<String> list = Arrays.asList(parts);

            list.forEach(
                    e -> {
                        if (e.contains(FOOD_IMAGE_FOLDER_PATH)) {
                            List<String> myList = Arrays.asList(e.split("\\?"));
                            String pathAndName = myList.get(0);

                            String path = pathAndName.split("%2")[0];
                            String name = pathAndName.split("%2")[1];
                            pathNameArray[0] = path;
                            pathNameArray[1] = name.substring(1);
                        }
                    }
            );

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return pathNameArray;
    }
}

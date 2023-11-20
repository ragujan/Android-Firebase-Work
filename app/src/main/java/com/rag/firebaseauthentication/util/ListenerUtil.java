package com.rag.firebaseauthentication.util;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ListenerUtil {
    public static void onClickBtnIntent(View view, Context context, Class<?> destinationActivity ){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,destinationActivity.getClass());
                context.startActivity(intent);
            }
        });

    }
}

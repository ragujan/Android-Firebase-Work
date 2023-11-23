package com.rag.firebaseauthentication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class ChangeFoodStatusDialogFragment extends DialogFragment {
    boolean availableStatus = false;

    public ChangeFoodStatusDialogFragment(boolean availableStatus) {
        this.availableStatus = availableStatus;
    }

//    @SuppressLint("ResourceAsColor")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.availability_status_change_dialog, null);
        TextView viewText = (TextView) view.findViewById(R.id.statusTextDialogBox);
        Button button = (Button) view.findViewById(R.id.switchAvailableBtn);

        if(!availableStatus){
            viewText.setText("Unavailable");
            viewText.setTextColor(Color.parseColor("#C70039"));
            button.setText("Make Available");

        }else{
            viewText.setText("available");
            viewText.setTextColor(Color.parseColor("#0BDA51"));
            button.setText("Make Unavailable");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        builder.setView(view);



        return builder.create();
    }
}

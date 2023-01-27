package com.example.insuranceapp.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.insuranceapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DialogRembuseUser extends DialogFragment {
    private static final String TAG = "RembuseDialog";
    String url; 
    ImageView imgNota;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public DialogRembuseUser(String url) {
        this.url = url;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_rembuse, container, false);
        imgNota = view.findViewById(R.id.imagetNota);

        Picasso.get().load(url)
                .fit().into(imgNota);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog != null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}

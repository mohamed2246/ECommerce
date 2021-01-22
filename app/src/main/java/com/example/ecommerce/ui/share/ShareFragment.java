package com.example.ecommerce.ui.share;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.ecommerce.R;
import com.example.ecommerce.Register.MainActivity;
import com.example.ecommerce.UserData.user_data;
import com.google.firebase.auth.FirebaseAuth;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;
    user_data user_data;
    FirebaseAuth firebaseAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_logout, container, false);
        user_data= new user_data(getContext());
        Intent intent = new Intent(getContext() , MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        user_data.saveDate("","",false);
        SharedPreferences preferences = getActivity().getSharedPreferences("myFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        getActivity().finish();
        startActivity(intent);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        return root;
    }
}
package com.example.ecommerce.Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class registrationActivity extends AppCompatActivity {
    Button create_account_btn;
    EditText input_name, input_phone, input_password;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        create_account_btn = findViewById(R.id.registration__btn);
        input_phone = findViewById(R.id.registration__phone_mumber_input);
        input_name = findViewById(R.id.registration__username_input);
        input_password = findViewById(R.id.registration_password_mumber_input);
        loadingBar = new ProgressDialog(this);
        create_account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

    }

    private void createAccount() {
        String name = input_name.getText().toString();
        String phone = input_phone.getText().toString();
        String password = input_password.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please write your name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please write your phone", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please Wait , While we are checking the credentails. ");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            validate_phone_num(name, phone, password);

        }

    }

    private void validate_phone_num(final String name, final String phone, final String password) {
        final DatabaseReference root_ref;
        root_ref = FirebaseDatabase.getInstance().getReference();
        root_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child("Users").child(phone).exists())) {
                    Map<String, Object> user_data_Map = new HashMap<>();
                    user_data_Map.put("phone", phone);
                    user_data_Map.put("name", name);
                    user_data_Map.put("password", password);
                    root_ref.child("Users").child(phone).updateChildren(user_data_Map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(registrationActivity.this, "Your Account has been created .", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(registrationActivity.this, login_activity.class);
                                startActivity(intent);
                            } else {
                                loadingBar.dismiss();
                                Toast.makeText(registrationActivity.this, "Network Error .", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {

                    Toast.makeText(registrationActivity.this, "this" + phone + "already exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(registrationActivity.this, "please try again using another phone .", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(registrationActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

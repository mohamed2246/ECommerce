package com.example.ecommerce.Sellers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SellerRegisterationActivity extends AppCompatActivity {
    Button arready_have_account_btn, logInBtn;
    EditText edtName, edtPassword, edtEmail, edtAddress, edtPhone;
    DatabaseReference root_ref;
    private ProgressDialog loadingBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registeration);
        root_ref = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        edtName = findViewById(R.id.seller_name);
        edtEmail = findViewById(R.id.seller_email);
        edtAddress = findViewById(R.id.seller_address);
        edtPassword = findViewById(R.id.seller_password);
        edtPhone = findViewById(R.id.seller_phone);
        logInBtn = findViewById(R.id.Rigister_btn_seller);
        arready_have_account_btn = findViewById(R.id.arridy_logoin_btn);
        loadingBar = new ProgressDialog(this);
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSellerInfo();
            }
        });

        arready_have_account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SellerRegisterationActivity.this, SellerLoginActivity.class));
            }
        });
    }

    private void getSellerInfo() {
        final String name = edtName.getText().toString();
        final String email = edtEmail.getText().toString();
        final String password = edtPassword.getText().toString();
        final String address = edtAddress.getText().toString();
        final String phone = edtPhone.getText().toString();

        if (!name.equals("") && !phone.equals("") && !address.equals("") && !password.equals("") && !email.equals("")) {
            loadingBar.setTitle("Create Seller Account");
            loadingBar.setMessage("Please Wait , While we are checking the credentails. ");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String uid = firebaseAuth.getCurrentUser().getUid();
                        Map<String, Object> seller_data_Map = new HashMap<>();
                        seller_data_Map.put("sid", uid);
                        seller_data_Map.put("name", name);
                        seller_data_Map.put("phone", phone);
                        seller_data_Map.put("email", email);
                        seller_data_Map.put("password", password);
                        seller_data_Map.put("address", address);
                        seller_data_Map.put("check", "0");

                        root_ref.child("Seller").child(phone).updateChildren(seller_data_Map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SellerRegisterationActivity.this, "You Are Register Successfully.", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                    Intent intent = new Intent(SellerRegisterationActivity.this, SellerLoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    loadingBar.dismiss();
                                }
                            }
                        });
                    }
                    else {
                        loadingBar.dismiss();
                        Toast.makeText(getApplicationContext(), "You Are Resisted Before. ", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        } else {
            loadingBar.dismiss();
            Toast.makeText(this, "Please Complete The Registration Form. ", Toast.LENGTH_SHORT).show();
        }


    }
}

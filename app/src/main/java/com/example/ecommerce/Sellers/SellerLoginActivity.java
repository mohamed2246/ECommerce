package com.example.ecommerce.Sellers;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SellerLoginActivity extends AppCompatActivity {

    EditText input_email, input_password;
    Button login_btn;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        input_email = findViewById(R.id.seller_login_email);
        input_password = findViewById(R.id.seller_login_password);
        login_btn = findViewById(R.id.Rigister_btn_sell);
        loadingBar = new ProgressDialog(this);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_user();
            }
        });

    }

    private void login_user() {
        String phone = input_email.getText().toString();
        String password = input_password.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please write your email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Opening Account");
            loadingBar.setMessage("Please Wait , While we are checking the credentails. ");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            Allow_access_to_Account(phone, password);

        }
    }


    private void Allow_access_to_Account(final String phone, final String password) {

        final DatabaseReference root_ref;
        root_ref = FirebaseDatabase.getInstance().getReference().child("Seller");
        root_ref.child(phone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    String check = snapshot.child("check").getValue().toString();
                    String email = snapshot.child("phone").getValue().toString();
                    String password1 = snapshot.child("password").getValue().toString();

                    if (email.equals(phone) && password.equals(password1)) {

                        if (check.equals("0")) {
                            Toast.makeText(SellerLoginActivity.this, "Login In Successfully .", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SellerLoginActivity.this, SellerCatigoryProducts.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                            loadingBar.dismiss();

                        } else if (check.equals("1")) {
                            Toast.makeText(SellerLoginActivity.this, "Admin Blocked Your Account.", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }

                    } else {
                        Toast.makeText(SellerLoginActivity.this, "Email Or Password Is Wrong.", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }

                } else {
                    Toast.makeText(SellerLoginActivity.this, "You must register first .", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

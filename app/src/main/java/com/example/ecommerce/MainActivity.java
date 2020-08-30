package com.example.ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.Model.Users;
import com.example.ecommerce.pervalent.prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    Button join_now_btn , login_btn;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        join_now_btn =findViewById(R.id.main_join_now_btn);
        login_btn = findViewById(R.id.main_join_btn);
        loadingBar = new ProgressDialog(this);
        Paper.init(this);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , login_activity.class);
                startActivity(intent);
            }
        });

        join_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , registrationActivity.class);
                startActivity(intent);
            }
        });

    String userphonekey = Paper.book().read(prevalent.UserPhoneKey);
    String userpasswordkey = Paper.book().read(prevalent.UserPasswordKey);
    if (userphonekey!="" && userpasswordkey!=""){
        if (!TextUtils.isEmpty(userphonekey) &&!TextUtils.isEmpty(userpasswordkey)){

            loadingBar.setTitle("Already logged in");
            loadingBar.setMessage("Please Wait... ");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            Allow_access(userphonekey ,userpasswordkey);

        }

    }
    }

    private void Allow_access(final String phone, final String password) {

        final DatabaseReference root_ref;
        root_ref = FirebaseDatabase.getInstance().getReference();
        root_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(phone).exists()){
                    Users dataUsers = snapshot.child("Users").child(phone).getValue(Users.class);
                    if (dataUsers.getPhone().equals(phone)){

                        if (dataUsers.getPassword().equals(password)){
                            prevalent.current_online_users = dataUsers;
                            Toast.makeText(MainActivity.this, "logged is successfully ...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(MainActivity.this , NAVActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if (!dataUsers.getPhone().equals(phone)){
                        Toast.makeText(MainActivity.this, "Please log in again ... log in with your new phone..", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this , login_activity.class);
                        startActivity(intent);
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Account with this Number do not exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this, "you need to create a new account .", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

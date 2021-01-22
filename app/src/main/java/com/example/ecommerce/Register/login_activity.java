package com.example.ecommerce.Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.Admin.Admin_Catigory_activity;
import com.example.ecommerce.Model.Users;
import com.example.ecommerce.R;
import com.example.ecommerce.User.NAVActivity;
import com.example.ecommerce.UserData.user_data;
import com.example.ecommerce.pervalent.prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class login_activity extends AppCompatActivity {

    EditText input_phone , input_password;
    Button login_btn;
    CheckBox ch_remeber_me ;
    private ProgressDialog loadingBar;
    TextView admin_link , not_admin_link , forgetPassword;
    String parentDBName = "Users";
    user_data userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        userData = new user_data(login_activity.this);
        input_phone = findViewById(R.id.login_phone_mumber_input);
        input_password = findViewById(R.id.login_password_mumber_input);
        login_btn = findViewById(R.id.login_btn);
        forgetPassword = findViewById(R.id.forget_password_link);
        loadingBar = new ProgressDialog(this);
        ch_remeber_me = findViewById(R.id.remember_me_chkb);
        Paper.init(this);
        admin_link = findViewById(R.id.admin_panel_link);
        not_admin_link = findViewById(R.id.not_admin_panel_link);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_user();
            }
        });

        admin_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_btn.setText("Login Admin");
                admin_link.setVisibility(View.INVISIBLE);
                not_admin_link.setVisibility(View.VISIBLE);
                parentDBName = "Admins";
            }
        });

        not_admin_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_btn.setText("Login ");
                admin_link.setVisibility(View.VISIBLE);
                not_admin_link.setVisibility(View.INVISIBLE);
                parentDBName = "Users";
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_activity.this, resetPassword.class);
                intent.putExtra("check" ,"login");
                startActivity(intent);
            }
        });


    }

    private void login_user() {
        String phone = input_phone.getText().toString();
        String password = input_password.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please write your phone", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();
        }else {
            loadingBar.setTitle("Opening Account");
            loadingBar.setMessage("Please Wait , While we are checking the credentails. ");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            Allow_access_to_Account(phone,password);

        }
    }

    private void Allow_access_to_Account(final String phone, final String password) {
        if (ch_remeber_me.isChecked()){
            Paper.book().write(prevalent.UserPhoneKey , phone);
            Paper.book().write(prevalent.UserPasswordKey,password);

        }
        final DatabaseReference root_ref;
        root_ref = FirebaseDatabase.getInstance().getReference();
        root_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentDBName).child(phone).exists()){
                    Users dataUsers = snapshot.child(parentDBName).child(phone).getValue(Users.class);
                  if (dataUsers.getPhone().equals(phone)){
                        if (dataUsers.getPassword().equals(password)){
                            if (parentDBName.equals("Admins")){
                                Toast.makeText(login_activity.this, "Welcome Admin  ...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(login_activity.this , Admin_Catigory_activity.class);
                                startActivity(intent);
                            }
                            else if (parentDBName.equals("Users")){
                                userData.saveDate(phone,password,true);
                                //Toast.makeText(login_activity.this, "logged is successfully ...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(login_activity.this , NAVActivity.class);
                                prevalent.current_online_users = dataUsers;
                                intent.putExtra("phone" , phone);

                                startActivity(intent);
                            }
                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(login_activity.this, "Incorrect password ...", Toast.LENGTH_SHORT).show();

                        }
                   }
                }
                else {
                    Toast.makeText(login_activity.this, "Account with this Number do not exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(login_activity.this, "you need to create a new account .", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

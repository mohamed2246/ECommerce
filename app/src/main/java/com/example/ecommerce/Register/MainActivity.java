package com.example.ecommerce.Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.Model.Users;
import com.example.ecommerce.R;
import com.example.ecommerce.Sellers.SellerRegisterationActivity;
import com.example.ecommerce.User.NAVActivity;
import com.example.ecommerce.UserData.user_data;
import com.example.ecommerce.pervalent.prevalent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    Button join_now_btn, login_btn;
    private ProgressDialog loadingBar;
    user_data user_data;
    TextView seller_path;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        user_data = new user_data(MainActivity.this);
        join_now_btn = findViewById(R.id.main_join_now_btn);
        login_btn = findViewById(R.id.main_join_btn);
        seller_path = findViewById(R.id.seller_bigan);
        loadingBar = new ProgressDialog(this);
        Paper.init(this);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, login_activity.class);
                startActivity(intent);
            }
        });

        join_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, registrationActivity.class);
                startActivity(intent);
            }
        });

        if (user_data.isLogin()) {
            loadingBar.setTitle("Already logged in");
            loadingBar.setMessage("Please Wait... ");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            Allow_access(user_data.getPHONE(), user_data.getPassword());
        } else {
            Toast.makeText(this, "You Didn't log In Before .  ", Toast.LENGTH_SHORT).show();

        }

   /* String userphonekey = Paper.book().read(prevalent.UserPhoneKey);
    String userpasswordkey = Paper.book().read(prevalent.UserPasswordKey);
    if (userphonekey!="" && userpasswordkey!=""){
        if (!TextUtils.isEmpty(userphonekey) &&!TextUtils.isEmpty(userpasswordkey)){

            loadingBar.setTitle("Already logged in");
            loadingBar.setMessage("Please Wait... ");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            Allow_access(userphonekey ,userpasswordkey);

        }

    }*/

        seller_path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SellerRegisterationActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            Intent intent = new Intent(getApplicationContext() , MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }*/
    }

    private void Allow_access(final String phone, final String password) {

        final DatabaseReference root_ref;
        root_ref = FirebaseDatabase.getInstance().getReference();
        root_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(phone).exists()) {
                    Users dataUsers = snapshot.child("Users").child(phone).getValue(Users.class);
                    if (dataUsers.getPhone().equals(phone)) {

                        if (dataUsers.getPassword().equals(password)) {
                            prevalent.current_online_users = dataUsers;
                            //Toast.makeText(MainActivity.this, "logged is successfully ...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(MainActivity.this, NAVActivity.class);
                            intent.putExtra("phone", phone);
                            startActivity(intent);
                        }
                    } else if (!dataUsers.getPhone().equals(phone)) {
                        Toast.makeText(MainActivity.this, "Please log in again ... log in with your new phone..", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, login_activity.class);
                        startActivity(intent);
                    }
                } else {
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

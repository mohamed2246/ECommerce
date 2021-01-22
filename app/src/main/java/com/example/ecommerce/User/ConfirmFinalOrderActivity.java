package com.example.ecommerce.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ecommerce.R;
import com.example.ecommerce.UserData.user_data;
import com.example.ecommerce.pervalent.prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class ConfirmFinalOrderActivity extends AppCompatActivity {
    EditText fname_et, lname_et, email_et, address_et, phone_et, City_et;
    Button confirm_btn;
    Toolbar toolbar;
    String total_price = "";
    user_data user_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final);
        user_data = new user_data(getApplicationContext());
        intial();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Shipment");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        total_price = getIntent().getStringExtra("Total_Price");
        Toast.makeText(this, "Total price is " + total_price, Toast.LENGTH_SHORT).show();


        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });

    }

    private void check() {
        if (TextUtils.isEmpty(fname_et.getText().toString())) {
            Toast.makeText(this, "Please Enter your First Name..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(lname_et.getText().toString())) {
            Toast.makeText(this, "Please Enter your Last Name..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(email_et.getText().toString())) {
            Toast.makeText(this, "Please Enter your Email..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone_et.getText().toString())) {
            Toast.makeText(this, "Please Enter your Phone..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(address_et.getText().toString())) {
            Toast.makeText(this, "Please Enter your Address..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(City_et.getText().toString())) {
            Toast.makeText(this, "Please Enter your City Name..", Toast.LENGTH_SHORT).show();
        } else {
            Confirm_order();
        }

    }

    private void Confirm_order() {
        String current_time, current_date;
        Calendar cal_for_date = Calendar.getInstance();
        SimpleDateFormat current_date_sim = new SimpleDateFormat("MM dd , yyyy");
        current_date = current_date_sim.format(cal_for_date.getTime());

        SimpleDateFormat current_date_time = new SimpleDateFormat("HH:mm:ss a");
        current_time = current_date_time.format(cal_for_date.getTime());
        String current_phone = prevalent.current_online_users.getPhone();
        final DatabaseReference order_ref = FirebaseDatabase.getInstance().getReference()
                .child("Orders").child(current_phone);


        HashMap<String,Object> objectHashMap = new HashMap<>();
        String price = total_price;
        String fname = fname_et.getText().toString();
        String email = email_et.getText().toString();
        String lname = lname_et.getText().toString();
        String address = address_et.getText().toString();
        String city = City_et.getText().toString();
        String phone = phone_et.getText().toString();
        objectHashMap.put("total_amount",price);
        objectHashMap.put("fname",fname);
        objectHashMap.put("email",email);
        objectHashMap.put("lname",lname);
        objectHashMap.put("address",address);
        objectHashMap.put("city",city);
        objectHashMap.put("current_date",current_date);
        objectHashMap.put("current_time",current_time);
        objectHashMap.put("state","not shipped");
        objectHashMap.put("phone",phone);




        order_ref.setValue(objectHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    final String phone = user_data.getPHONE().toString();
                    FirebaseDatabase.getInstance().getReference().child("Cart List").child("User View")
                            .child(prevalent.current_online_users.getPhone())
                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ConfirmFinalOrderActivity.this, "Your Final Order Has Placed Successfully..", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ConfirmFinalOrderActivity.this, NAVActivity.class);
                            intent.putExtra("phone" , phone);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });
                } else {
                    Toast.makeText(ConfirmFinalOrderActivity.this, "Order Failed..", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void intial() {
        fname_et = findViewById(R.id.fname_et);
        lname_et = findViewById(R.id.lname_et);
        email_et = findViewById(R.id.email_et);
        phone_et = findViewById(R.id.phone_et);
        address_et = findViewById(R.id.address_et);
        City_et = findViewById(R.id.City_et);
        confirm_btn = findViewById(R.id.Confirm_btn);
        toolbar = findViewById(R.id.find_friends_tool_bar);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ConfirmFinalOrderActivity.this, NAVActivity.class));
    }
}

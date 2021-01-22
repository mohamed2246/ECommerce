package com.example.ecommerce.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.User.NAVActivity;
import com.example.ecommerce.R;
import com.example.ecommerce.Register.login_activity;

public class Admin_Catigory_activity extends AppCompatActivity {
    ImageView tshirts, spor_shirts, femailderess, seatshirt;
    ImageView glasses, hatscaps, walletsBagspuses, shoes;
    ImageView headphones, laptops, watches, mobilephones;

    Button check_orders ,maintainProductsBtn;
    ImageView log_out;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__catigory_activity);

        check_orders = findViewById(R.id.check_orders);
        maintainProductsBtn = findViewById(R.id.maintain_orders);
        log_out = findViewById(R.id.log_out);

        tshirts = findViewById(R.id.t_shirts);
        spor_shirts = findViewById(R.id.sports_t_shirts);
        mobilephones = findViewById(R.id.mobiles);
        femailderess = findViewById(R.id.fmail_dreses);
        seatshirt = findViewById(R.id.sweather);
        glasses = findViewById(R.id.glasses);
        hatscaps = findViewById(R.id.hats);
        walletsBagspuses = findViewById(R.id.purses_bags);
        shoes = findViewById(R.id.shoess);
        headphones = findViewById(R.id.headphoness);
        laptops = findViewById(R.id.laptops);
        watches = findViewById(R.id.watches);




        check_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Catigory_activity.this,Admin_new_orders.class);
                startActivity(intent);
            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Admin_Catigory_activity.this, login_activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        maintainProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Catigory_activity.this, NAVActivity.class);
                intent.putExtra("admin","admin");
                startActivity(intent);

            }
        });


        product_to_activity(tshirts ,"tShirts" );
        product_to_activity(spor_shirts ,"sports_shirts" );
        product_to_activity(femailderess ,"femail dresses" );
        product_to_activity(seatshirt ,"sea shirt" );
        product_to_activity(glasses ,"glasses" );
        product_to_activity(hatscaps ,"hats caps" );
        product_to_activity(walletsBagspuses ,"wallets Bagspuses" );
        product_to_activity(shoes ,"shoes" );
        product_to_activity(headphones ,"head phones" );
        product_to_activity(laptops ,"laptops" );
        product_to_activity(watches ,"watches" );
        product_to_activity(mobilephones ,"mobilephones" );


    }

    private void product_to_activity(ImageView imageView, final String string) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Catigory_activity.this, Admin_new_product_Activity.class);
                intent.putExtra("catogary", string);
                startActivity(intent);
            }
        });
    }
}



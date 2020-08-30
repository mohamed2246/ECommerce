package com.example.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Admin_Catigory_activity extends AppCompatActivity {
    ImageView tshirts, spor_shirts, femailderess, seatshirt;
    ImageView glasses, hatscaps, walletsBagspuses, shoes;
    ImageView headphones, laptops, watches, mobilephones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__catigory_activity);

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



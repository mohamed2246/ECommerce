package com.example.ecommerce.Sellers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.R;
import com.example.ecommerce.Register.MainActivity;

public class SellerCatigoryProducts extends AppCompatActivity {
    ImageView tshirts, spor_shirts, femailderess, seatshirt;
    ImageView glasses, hatscaps, walletsBagspuses, shoes;
    ImageView headphones, laptops, watches, mobilephones;
    ImageView log_out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_catigory_products);

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



        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SellerCatigoryProducts.this, SellerRegisterationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
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
                Intent intent = new Intent(SellerCatigoryProducts.this, SellerAddNewProducts.class);
                intent.putExtra("catogary", string);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SellerCatigoryProducts.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

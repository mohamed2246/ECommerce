package com.example.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ecommerce.pervalent.prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class product_details_Activity extends AppCompatActivity {

    String url;
    ImageView product_image;
    ElegantNumberButton number_btn ;
    TextView product_name , product_price,product_description;
    String product_id ;
    Button  add_to_cart_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_);
        add_to_cart_btn = findViewById(R.id.Add_to_cart);
        number_btn = findViewById(R.id.number_btn);
        product_image= findViewById(R.id.product_image_details);
        product_name = findViewById(R.id.product_name_details);
        product_price = findViewById(R.id.product_price_details);
        product_description = findViewById(R.id.product_description_details);
        product_id = getIntent().getExtras().getString("pid");
        getProduct_details();

        add_to_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Adding_to_cart_list();
            }
        });
    }

    private void Adding_to_cart_list() {
        String current_time , current_date;
        Calendar cal_for_date = Calendar.getInstance();
        SimpleDateFormat current_date_sim = new SimpleDateFormat("MM dd , yyyy");
        current_date = current_date_sim.format(cal_for_date.getTime());

        SimpleDateFormat current_date_time = new SimpleDateFormat("HH:mm:ss a");
        current_time = current_date_time.format(cal_for_date.getTime());

        final DatabaseReference cart_lidt_cart = FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String , Object> cart_hash_map = new HashMap<>();
        cart_hash_map.put("pid" ,product_id );
        cart_hash_map.put("pname" ,product_name.getText().toString() );
        cart_hash_map.put("description" ,product_description.getText().toString() );
        cart_hash_map.put("price" ,product_price.getText().toString() );
        cart_hash_map.put("date" ,current_date );
        cart_hash_map.put("time" ,current_time );
        cart_hash_map.put("quantity" ,number_btn.getNumber() );
        cart_hash_map.put("image" ,url );
        cart_hash_map.put("discount" ,"");
        cart_lidt_cart.child("User View").child(prevalent.current_online_users.getPhone()).child("Products").child(product_id)
                .updateChildren(cart_hash_map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    cart_lidt_cart.child("Admin View").child(prevalent.current_online_users.getPhone()).child("Products").child(product_id)
                            .updateChildren(cart_hash_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(product_details_Activity.this, "Added To cart list ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(product_details_Activity.this , NAVActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|FLAG_ACTIVITY_SINGLE_TOP);
                            finish();
                            startActivity(intent);
                        }
                    });
                }
            }
        });





    }

    private void getProduct_details() {
        DatabaseReference product_ref = FirebaseDatabase.getInstance().getReference().child("Products");
        product_ref.child(product_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products products = new products();
                if (snapshot.exists()){
                    products products1 = snapshot.getValue(products.class);
                    product_name .setText(products1.getPname());
                    product_description.setText(products1.getDescription());
                    product_price.setText(products1.getPrice() + " $");
                    url = products1.getImage();
                    Picasso.get().load(products1.getImage()).placeholder(R.drawable.profile_image).into(product_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}

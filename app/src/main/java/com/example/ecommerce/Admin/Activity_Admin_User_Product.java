package com.example.ecommerce.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.User.cart_model;
import com.example.ecommerce.User.cart_view_holder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Activity_Admin_User_Product extends AppCompatActivity {
    private RecyclerView rec;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference cartRef;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__admin__user__product);
        userId = getIntent().getExtras().getString("uid");
        rec = findViewById(R.id.products_list);
        cartRef = FirebaseDatabase.getInstance().getReference()
                .child("Cart List").child("Admin View").child(userId).child("Products");
        layoutManager = new LinearLayoutManager(this);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<cart_model> options = new FirebaseRecyclerOptions.Builder<cart_model>()
                .setQuery(cartRef,cart_model.class).build();
        FirebaseRecyclerAdapter<cart_model, cart_view_holder> adapter = new FirebaseRecyclerAdapter<cart_model, cart_view_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull cart_view_holder holder, int position, @NonNull cart_model model) {
                holder.txt_product_name.setText( model.getPname());
                holder.txt_product_price.setText( "price : " + model.getPrice()+" $");
                holder.txt_product_quantity.setText("Quantity : " +model.getQuantity());
                Picasso.get().load(model.getimage()).placeholder(R.drawable.profile_image).into(holder.image_product_image);
            }

            @NonNull
            @Override
            public cart_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
                return new cart_view_holder(view);
            }
        };
        rec.setAdapter(adapter);
        adapter.startListening();

    }
}

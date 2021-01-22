package com.example.ecommerce.Admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.View_holder.AdminOrdersViewHolder;
import com.example.ecommerce.Model.admin_orders_model;
import com.example.ecommerce.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_new_orders extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference order_ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);

        order_ref = FirebaseDatabase.getInstance().getReference().child("Orders");
        recyclerView = findViewById(R.id.order_list1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<admin_orders_model> options = new FirebaseRecyclerOptions
                .Builder<admin_orders_model>()
                .setQuery(order_ref, admin_orders_model.class)
                .build();
        FirebaseRecyclerAdapter<admin_orders_model, AdminOrdersViewHolder>
                adapter = new FirebaseRecyclerAdapter<admin_orders_model, AdminOrdersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, final int position, @NonNull final admin_orders_model model) {
                holder.userName.setText("Name : " + model.getfname() + " " + model.getlname());
                holder.userPhoneNumber.setText("Phone : " + model.getPhone());
                holder.userTotalPrice.setText("Total price = $ " + model.getTotal_amount());
                holder.userDateTime.setText("Order at : " + model.getCurrent_date() + "  " + model.getCurrent_time());
                holder.userShippingAddress.setText("Shipping Address : " + model.getAddress() + " , " + model.getCity());
                holder.showOrderButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String phone = getRef(position).getKey();
                        Intent intent = new Intent(Admin_new_orders.this,Activity_Admin_User_Product.class);
                        intent.putExtra("uid",phone);
                        startActivity(intent);
                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{
                                "YES",
                                "NO"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_new_orders.this);
                        builder.setTitle("Have you shipped this order products ? ");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i==0){
                                    String phone = getRef(position).getKey();
                                    remove_Order(phone);

                                }
                                else {
                                    // do some thing
                                }

                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                return new AdminOrdersViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    private void remove_Order(String phone) {
        order_ref.child(phone).removeValue();

    }
}

package com.example.ecommerce.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.User.ConfirmFinalOrderActivity;
import com.example.ecommerce.User.NAVActivity;
import com.example.ecommerce.R;
import com.example.ecommerce.User.cart_model;
import com.example.ecommerce.User.cart_view_holder;
import com.example.ecommerce.pervalent.prevalent;
import com.example.ecommerce.User.product_details_Activity;
import com.example.ecommerce.ui.send.Home_new_framgent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Cart_New_Fragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Button next_process_btn;
    TextView txt_total_amount,txt_msg1;
    int over_total_price=0;
    Context context;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        ((NAVActivity)getActivity()).imageView.setVisibility(View.VISIBLE);
        ((NAVActivity)getActivity()).toolbar.setTitle("Cart");
        ((NAVActivity)getActivity()).fab.hide();
        next_process_btn = root.findViewById(R.id.Next_btn);
        txt_msg1 = root.findViewById(R.id.msg1);
        txt_total_amount = root.findViewById(R.id.total_price);
        recyclerView  = root.findViewById(R.id.cart_list1);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        next_process_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ConfirmFinalOrderActivity.class);
                intent.putExtra("Total_Price",String.valueOf(over_total_price));
                startActivity(intent);

            }
        });


        return root;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        checkOrderState();
        final DatabaseReference cartlist_ref = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<cart_model> options = new FirebaseRecyclerOptions.Builder<cart_model>()
                .setQuery(cartlist_ref.child("User View").child(prevalent.current_online_users.getPhone()).child("Products"),cart_model.class)
                .build();
        FirebaseRecyclerAdapter <cart_model, cart_view_holder> adapter = new FirebaseRecyclerAdapter<cart_model, cart_view_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull cart_view_holder holder, final int position, @NonNull final cart_model model) {
                holder.txt_product_name.setText( model.getPname());
                holder.txt_product_price.setText( "price : " + model.getPrice()+" $");
                holder.txt_product_quantity.setText("Quantity : " +model.getQuantity());
                int one_type_product_price = ((Integer.valueOf(model.getPrice())))*((Integer.valueOf(model.getQuantity())));
                over_total_price=over_total_price+one_type_product_price;
                txt_total_amount.setText("Total Price = " + String.valueOf(over_total_price+" $") );
                Picasso.get().load(model.getimage()).placeholder(R.drawable.profile_image).into(holder.image_product_image);




                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{
                                "Edit",
                                "Remove"
                        };
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Cart Options: ");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i==0){
                                    Intent intent = new Intent(getContext(),product_details_Activity.class);
                                    intent.putExtra("pid",model.getPid());
                                    startActivity(intent);
                                }
                                if (i==1){
                                    cartlist_ref.child("User View")
                                            .child(prevalent.current_online_users.getPhone())
                                            .child("Products")
                                            .child(model.getPid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(getContext(), "Item Removed successafully", Toast.LENGTH_SHORT).show();
                                                        cartlist_ref.child("Admin View")
                                                                .child(prevalent.current_online_users.getPhone())
                                                                .child("Products")
                                                                .child(model.getPid())
                                                                .removeValue();
                                                        Fragment fragment = new Home_new_framgent();
                                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                                                        fragmentTransaction.addToBackStack(null);
                                                        fragmentTransaction.commit();


                                                    }
                                                }
                                            });
                                }

                            }
                        });
                        builder.show();
                    }
                });

            }

            @NonNull
            @Override
            public cart_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
                cart_view_holder cart_view_holder = new cart_view_holder(view);
                return cart_view_holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        txt_total_amount.setText("Total Price = " + String.valueOf(over_total_price) );

    }


    private void checkOrderState(){
        final DatabaseReference order_database;
        order_database = FirebaseDatabase.getInstance().getReference().child("Orders").child(prevalent.current_online_users.getPhone());
        order_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String shiping_state = snapshot.child("state").getValue().toString();
                    String fname = snapshot.child("fname").getValue().toString();
                    String lname = snapshot.child("lname").getValue().toString();
                    if (shiping_state.equals("shipped")){
                        txt_total_amount.setText("Dear "+ fname + " "+ lname + "\n Order is shipped successfully.");
                        recyclerView.setVisibility(View.GONE);
                        txt_msg1.setVisibility(View.VISIBLE);
                        txt_msg1.setText("Congratulations , Your final Order has been placed successfully . soon you will receive your order at your door step ...");
                        next_process_btn.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "You can purchase more products, Once you receive your final order.", Toast.LENGTH_SHORT).show();

                    } else if (shiping_state.equals("not shipped")){
                        txt_total_amount.setText("Shipping State = Not Shipped");
                        recyclerView.setVisibility(View.GONE);
                        txt_msg1.setVisibility(View.VISIBLE);
                        next_process_btn.setVisibility(View.GONE);
                       // Toast.makeText(getContext(), "You can purchase more products, Once you receive your final order.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
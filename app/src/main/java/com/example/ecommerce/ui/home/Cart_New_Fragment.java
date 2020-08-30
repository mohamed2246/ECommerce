package com.example.ecommerce.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.NAVActivity;
import com.example.ecommerce.R;
import com.example.ecommerce.cart_model;
import com.example.ecommerce.cart_view_holder;
import com.example.ecommerce.pervalent.prevalent;
import com.example.ecommerce.product_details_Activity;
import com.example.ecommerce.ui.send.Home_new_framgent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Cart_New_Fragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Button next_process_btn;
    TextView txt_total_amount;

    ImageView arrow2;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        ((NAVActivity)getActivity()).imageView.setVisibility(View.VISIBLE);

        recyclerView  = root.findViewById(R.id.cart_list1);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);



        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        final DatabaseReference cartlist_ref = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<cart_model> options = new FirebaseRecyclerOptions.Builder<cart_model>()
                .setQuery(cartlist_ref.child("User View").child(prevalent.current_online_users.getPhone()).child("Products"),cart_model.class)
                .build();
        FirebaseRecyclerAdapter <cart_model, cart_view_holder> adapter = new FirebaseRecyclerAdapter<cart_model, cart_view_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull cart_view_holder holder, final int position, @NonNull final cart_model model) {
                holder.txt_product_name.setText( model.getPname());
                holder.txt_product_price.setText( "price : " + model.getPrice());
                holder.txt_product_quantity.setText("Quantity : " +model.getQuantity());
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

    }
}
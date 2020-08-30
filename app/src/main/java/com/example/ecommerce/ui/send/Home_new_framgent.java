package com.example.ecommerce.ui.send;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.NAVActivity;
import com.example.ecommerce.R;
import com.example.ecommerce.View_holder.product_view_holder;
import com.example.ecommerce.product_details_Activity;
import com.example.ecommerce.products;
import com.example.ecommerce.ui.home.Cart_New_Fragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Home_new_framgent extends Fragment {

    private SendViewModel sendViewModel;
    View root;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference productref;
    ImageView arrow ;
    //ImageView imageView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.hooome, container, false);
        productref = FirebaseDatabase.getInstance().getReference().child("Products");
        recyclerView = root.findViewById(R.id.recycle_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
       // imageView = root.findViewById(R.id.back_home);

        ((NAVActivity)getActivity()).imageView.setVisibility(View.GONE);
        ((NAVActivity)getActivity()).fab.show();
        ((NAVActivity)getActivity()).fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NAVActivity)getActivity()).flag = 1;
                Fragment fragment = new Cart_New_Fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

       // imageView.setVisibility(View.INVISIBLE);
        FirebaseRecyclerOptions<products> options  = new FirebaseRecyclerOptions.Builder<products>()
                .setQuery(productref,products.class).build();

        FirebaseRecyclerAdapter<products, product_view_holder> adapter = new FirebaseRecyclerAdapter<products, product_view_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull product_view_holder holder, int position, @NonNull final products model) {
                holder.text_product_name.setText(model.getPname());
                holder.text_product_desc.setText(model.getDescription());
                holder.text_product_price.setText("Price = "+model.getPrice() + " $");
                Picasso.get().load(model.getImage()).into(holder.imageView);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext() , product_details_Activity.class);
                        intent.putExtra("pid" , model.getPid());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public product_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
                product_view_holder contacts_viewHolder = new product_view_holder(view);
                return contacts_viewHolder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
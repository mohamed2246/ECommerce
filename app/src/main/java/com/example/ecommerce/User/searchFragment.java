package com.example.ecommerce.User;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.View_holder.product_view_holder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class searchFragment extends Fragment {
    private Button search_btn;
    private EditText inputText;
    private RecyclerView searchRecy;
    private String searchInput;
    private RecyclerView.LayoutManager layoutManager;
    Context context;
    public searchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view= inflater.inflate(R.layout.fragment_search, container, false);
        search_btn = view.findViewById(R.id.search_btn);
        inputText = view.findViewById(R.id.searh_product);
        searchRecy = view.findViewById(R.id.search_rec);
        searchRecy.setLayoutManager(new LinearLayoutManager(getContext()));

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchInput = inputText.getText().toString();
                onStart();
            }
        });



        return view;
    }




    @Override
    public void onStart() {
        super.onStart();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");
        FirebaseRecyclerOptions<products> options = new FirebaseRecyclerOptions.Builder<products>()
                .setQuery(reference.orderByChild("pname").startAt(searchInput),products.class).build();

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

        searchRecy.setAdapter(adapter);
        adapter.startListening();
    }
}

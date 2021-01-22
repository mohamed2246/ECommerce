package com.example.ecommerce.ui.send;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Admin.Admin_Maintain_Product_Activity;
import com.example.ecommerce.User.NAVActivity;
import com.example.ecommerce.R;
import com.example.ecommerce.View_holder.product_view_holder;
import com.example.ecommerce.User.product_details_Activity;
import com.example.ecommerce.User.products;
import com.example.ecommerce.ui.home.Cart_New_Fragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class Home_new_framgent extends Fragment {

    private SendViewModel sendViewModel;
    View root;
    RecyclerView recyclerView_mobile , recyclerView_tshirt , recyclerView_shirt , recyclerView_laptop , recyclerView_driss, recyclerView_profal ,  recyclerView_glasses , recyclerView_bags , recyclerView_hats ,  recyclerView_sheo , recyclerView_whatch , recyclerView_headphone ;
    RecyclerView.LayoutManager layoutManager_mobile, layoutManager_tshirt , layoutManager_shirt , layoutManager_laptop , layoutManager_driss, layoutManager_profal ,  layoutManager_glasses , layoutManager_bags , layoutManager_hats ,  layoutManager_sheo , layoutManager_whatch , layoutManager_headphone ;
    private DatabaseReference productref;
    ImageView arrow;
    //ImageView imageView;
    String type="";
    Query query;
    TextView t1 ,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11 ,t12;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.hooome, container, false);

        t1 = root.findViewById(R.id.txt_Mobiles);
        t2 = root.findViewById(R.id.txt_labs);
        t3 = root.findViewById(R.id.txt_area);
        t4 = root.findViewById(R.id.txt_whatch);
        t5 = root.findViewById(R.id.txt_sheo);
        t6 = root.findViewById(R.id.txt_glasses);
        t7 = root.findViewById(R.id.txt_tshirts);
        t8 = root.findViewById(R.id.txt_shirts);
        t9 = root.findViewById(R.id.txt_dress);
        t10 = root.findViewById(R.id.txt_plover);
        t11 = root.findViewById(R.id.txt_bags);
        t12 = root.findViewById(R.id.txt_hats);


        t1.setVisibility(View.INVISIBLE);
        t2.setVisibility(View.INVISIBLE);
        t3.setVisibility(View.INVISIBLE);
        t4.setVisibility(View.INVISIBLE);
        t5.setVisibility(View.INVISIBLE);
        t6.setVisibility(View.INVISIBLE);
        t7.setVisibility(View.INVISIBLE);
        t8.setVisibility(View.INVISIBLE);
        t9.setVisibility(View.INVISIBLE);
        t10.setVisibility(View.INVISIBLE);
        t11.setVisibility(View.INVISIBLE);
        t12.setVisibility(View.INVISIBLE);




        productref = FirebaseDatabase.getInstance().getReference().child("Products");
        ////////////////////////////////////////////////////////// Mobile
        recyclerView_mobile = root.findViewById(R.id.mobiles_recycle_menu);
        layoutManager_mobile = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView_mobile.setLayoutManager(layoutManager_mobile);

        /////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////// labtop
        recyclerView_laptop = root.findViewById(R.id.laptop_recycle_menu);
        layoutManager_laptop= new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView_laptop.setLayoutManager(layoutManager_laptop);
        /////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////// headphone
        recyclerView_headphone = root.findViewById(R.id.areapod_recycle_menu);
        layoutManager_headphone= new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView_headphone.setLayoutManager(layoutManager_headphone);
        /////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////// whatsch
        recyclerView_whatch = root.findViewById(R.id.whatch_recycle_menu);
        layoutManager_whatch= new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView_whatch.setLayoutManager(layoutManager_whatch);
        /////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////// shoe
        recyclerView_sheo = root.findViewById(R.id.sheo_recycle_menu);
        layoutManager_sheo= new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView_sheo.setLayoutManager(layoutManager_sheo);
        /////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////// hat
        recyclerView_hats = root.findViewById(R.id.hats_recycle_menu);
        layoutManager_hats= new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView_hats.setLayoutManager(layoutManager_hats);
        /////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////// bags
        recyclerView_bags = root.findViewById(R.id.bags_recycle_menu);
        layoutManager_bags= new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView_bags.setLayoutManager(layoutManager_bags);
        /////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////// glass
        recyclerView_glasses = root.findViewById(R.id.glasses_recycle_menu);
        layoutManager_glasses= new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView_glasses.setLayoutManager(layoutManager_glasses);
        /////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////// profer
        recyclerView_profal = root.findViewById(R.id.plover_recycle_menu);
        layoutManager_profal= new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView_profal.setLayoutManager(layoutManager_profal);
        /////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////// dress
        recyclerView_driss = root.findViewById(R.id.dress_recycle_menu);
        layoutManager_driss= new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView_driss.setLayoutManager(layoutManager_driss);
        /////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////// tShirt
        recyclerView_tshirt = root.findViewById(R.id.recycle_menu_t_shirt);
        layoutManager_tshirt= new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView_tshirt.setLayoutManager(layoutManager_tshirt);
        /////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////// Shirt
        recyclerView_shirt = root.findViewById(R.id.shirt_recycle_menu);
        layoutManager_shirt= new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView_shirt.setLayoutManager(layoutManager_shirt);
        /////////////////////////////////////////////////////////////////////////////////
       /* FillRecyleViews(recyclerView_mobile,"mobilephones",t1);
        FillRecyleViews(recyclerView_laptop,"laptops",t2);
        FillRecyleViews(recyclerView_headphone,"head phones",t3);
        FillRecyleViews(recyclerView_profal,"sea shirt",t4);
        FillRecyleViews(recyclerView_shirt,"tShirts",t5);
        FillRecyleViews(recyclerView_glasses,"glasses",t6);
        FillRecyleViews(recyclerView_hats,"hats caps",t7);
        FillRecyleViews(recyclerView_bags,"wallets Bagspuses",t8);
        FillRecyleViews(recyclerView_sheo,"shoes",t9);
        FillRecyleViews(recyclerView_whatch,"watches",t10);
        FillRecyleViews(recyclerView_driss,"femail dresses",t11);
        FillRecyleViews(recyclerView_tshirt,"sports_shirts",t12);*/
        /////////////////////////////////////////////////////////////////////////////////
        // imageView = root.findViewById(R.id.back_home);
        ((NAVActivity) getActivity()).toolbar.setTitle("Home");
        ((NAVActivity) getActivity()).imageView.setVisibility(View.GONE);
        ((NAVActivity) getActivity()).fab.show();
        Intent intent = getActivity().getIntent();
        if (intent.hasExtra("admin")) {
            ((NAVActivity)getActivity()).fab.hide();
        }
        ((NAVActivity) getActivity()).fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NAVActivity) getActivity()).flag = 1;
                Fragment fragment = new Cart_New_Fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return root;

        ///////////////////////////////////////////////////////////////////////////
    }

    @Override
    public void onStart() {
        super.onStart();

        FillRecyleViews(recyclerView_mobile,"mobilephones",t1);
        FillRecyleViews(recyclerView_laptop,"laptops",t2);
        FillRecyleViews(recyclerView_headphone,"head phones",t3);
        FillRecyleViews(recyclerView_profal,"sea shirt",t4);
        FillRecyleViews(recyclerView_shirt,"tShirts",t5);
        FillRecyleViews(recyclerView_glasses,"glasses",t6);
        FillRecyleViews(recyclerView_hats,"hats caps",t7);
        FillRecyleViews(recyclerView_bags,"wallets Bagspuses",t8);
        FillRecyleViews(recyclerView_sheo,"shoes",t9);
        FillRecyleViews(recyclerView_whatch,"watches",t10);
        FillRecyleViews(recyclerView_driss,"femail dresses",t11);
        FillRecyleViews(recyclerView_tshirt,"sports_shirts",t12);



            }





    private void FillRecyleViews(RecyclerView rec , String type , final TextView textView) {

        query = productref.orderByChild("catogry").equalTo(type);

        // imageView.setVisibility(View.INVISIBLE);


        FirebaseRecyclerOptions<products> options = new FirebaseRecyclerOptions.Builder<products>()
                .setQuery(query, products.class).build();

        FirebaseRecyclerAdapter<products, product_view_holder> adapter = new FirebaseRecyclerAdapter<products, product_view_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull product_view_holder holder, int position, @NonNull final products model) {

                textView.setVisibility(View.VISIBLE);
                holder.text_product_name.setText(model.getPname());
                holder.text_product_desc.setText(model.getDescription());
                holder.text_product_price.setText("Price = " + model.getPrice() + " $");
                Picasso.get().load(model.getImage()).into(holder.imageView);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = getActivity().getIntent();
                        if (intent1.hasExtra("admin")) {
                            Intent intent = new Intent(getContext(), Admin_Maintain_Product_Activity.class);
                            intent.putExtra("pid", model.getPid());
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getContext(), product_details_Activity.class);
                            intent.putExtra("pid", model.getPid());
                            startActivity(intent);
                        }

                    }
                });
            }

            @NonNull
            @Override
            public product_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_small, parent, false);
                product_view_holder contacts_viewHolder = new product_view_holder(view);
                return contacts_viewHolder;
            }
        };
        rec.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public void onResume() {
        super.onResume();
/*
        FillRecyleViews(recyclerView_mobile,"mobilephones",t1);
        FillRecyleViews(recyclerView_laptop,"laptops",t2);
        FillRecyleViews(recyclerView_headphone,"head phones",t3);
        FillRecyleViews(recyclerView_profal,"sea shirt",t4);
        FillRecyleViews(recyclerView_shirt,"tShirts",t5);
        FillRecyleViews(recyclerView_glasses,"glasses",t6);
        FillRecyleViews(recyclerView_hats,"hats caps",t7);
        FillRecyleViews(recyclerView_bags,"wallets Bagspuses",t8);
        FillRecyleViews(recyclerView_sheo,"shoes",t9);
        FillRecyleViews(recyclerView_whatch,"watches",t10);
        FillRecyleViews(recyclerView_driss,"femail dresses",t11);
        FillRecyleViews(recyclerView_tshirt,"sports_shirts",t12);*/
    }
}
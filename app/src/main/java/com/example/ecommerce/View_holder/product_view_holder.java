package com.example.ecommerce.View_holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Interface.item_click_lesiner;
import com.example.ecommerce.R;

public class product_view_holder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView text_product_name;
    public TextView text_product_desc;
    public TextView text_product_price;
    public ImageView imageView;
    item_click_lesiner my_Item_click_lesiner;

    public product_view_holder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.product_imag);
        text_product_name = itemView.findViewById(R.id.product_nam);
        text_product_desc = itemView.findViewById(R.id.product_descriptio);
        text_product_price = itemView.findViewById(R.id.product_pric);
    }


    @Override
    public void onClick(View view) {
        my_Item_click_lesiner.onclick(view , getAdapterPosition() , false);
    }

    void set_item_click_lisenner(item_click_lesiner lesiner){
        my_Item_click_lesiner = lesiner;
    }

}

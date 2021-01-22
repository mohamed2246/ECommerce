package com.example.ecommerce.User;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Interface.item_click_lesiner;
import com.example.ecommerce.R;

public class cart_view_holder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_product_name;
    public TextView txt_product_price;
    public TextView txt_product_quantity;
    public ImageView image_product_image;
    private item_click_lesiner my_item_click_lesiner;
    public cart_view_holder(@NonNull View itemView) {
        super(itemView);
        txt_product_name = itemView.findViewById(R.id.product_item_name);
        txt_product_price = itemView.findViewById(R.id.product_item_price);
        txt_product_quantity = itemView.findViewById(R.id.product_item_quantity);
        image_product_image = itemView.findViewById(R.id.product_item_image);
    }

    @Override
    public void onClick(View view) {
        my_item_click_lesiner.onclick(view , getAdapterPosition(), false);
    }

    public void setMy_item_click_lesiner(item_click_lesiner my_item_click_lesiner) {
        this.my_item_click_lesiner = my_item_click_lesiner;
    }

}

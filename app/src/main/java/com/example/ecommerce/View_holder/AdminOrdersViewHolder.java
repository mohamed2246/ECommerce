package com.example.ecommerce.View_holder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;

public class AdminOrdersViewHolder extends RecyclerView.ViewHolder {
    public TextView userName;
    public TextView userPhoneNumber;
    public TextView userTotalPrice;
    public TextView userDateTime;
    public TextView userShippingAddress;
    public Button showOrderButton;
    public AdminOrdersViewHolder(@NonNull View itemView) {
        super(itemView);
        userName = itemView.findViewById(R.id.order_user_name);
        userPhoneNumber = itemView.findViewById(R.id.order_phone_number);
        userTotalPrice = itemView.findViewById(R.id.order_total_price);
        userDateTime = itemView.findViewById(R.id.order_date_time);
        userShippingAddress = itemView.findViewById(R.id.order_address_city);
        showOrderButton = itemView.findViewById(R.id.show_all_products);

    }
}

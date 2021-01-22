package com.example.ecommerce.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Admin_Maintain_Product_Activity extends AppCompatActivity {

    private Button applay_change_btn , delete_btn;
    private EditText et_name,et_price,et_description ;
    private ImageView imageView;
    private String productID = "" ;
    private DatabaseReference product_ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__maintain__product_);
        applay_change_btn = findViewById(R.id.product_maintain_btn);
        delete_btn = findViewById(R.id.product_Delete_btn);
        et_name = findViewById(R.id.product_name_maintain);
        et_price = findViewById(R.id.product_pric_maintain);
        et_description = findViewById(R.id.product_descriptio_maintain);
        imageView = findViewById(R.id.product_imag_maintain);
        productID = getIntent().getExtras().getString("pid");
        product_ref = FirebaseDatabase.getInstance().getReference().child("Products").child(productID);

        DisplayProductInfo();

        applay_change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applay_changes();
            }
        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem();
            }
        });

    }

    private void deleteItem() {
        product_ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Admin_Maintain_Product_Activity.this, "This Product Deleted Successfully ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Admin_Maintain_Product_Activity.this,Admin_Catigory_activity.class));
                    finish();
                }
            }
        });
    }

    private void applay_changes() {
        String Pname = et_name.getText().toString();
        String Pdisc = et_description.getText().toString();
        String Pprice = et_price.getText().toString();

         if (TextUtils.isEmpty(Pname)){
            Toast.makeText(this, "Please Write Product Name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Pdisc)){
            Toast.makeText(this, "Please Write Product Discription", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Pprice)){
            Toast.makeText(this, "Please Write Product Price", Toast.LENGTH_SHORT).show();
        }
        else {
             HashMap<String , Object> product_map= new HashMap<>();
             product_map.put("pid" , productID);
             product_map.put("description" , Pdisc);
             product_map.put("price" , Pprice);
             product_map.put("pname" , Pname);

             product_ref.updateChildren(product_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     if (task.isSuccessful()){
                         Toast.makeText(Admin_Maintain_Product_Activity.this, "Changes applied successfully ", Toast.LENGTH_SHORT).show();
                         startActivity(new Intent(Admin_Maintain_Product_Activity.this,Admin_Catigory_activity.class));
                         finish();
                     }
                 }
             });
        }

    }



    private void DisplayProductInfo() {

        product_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String name = snapshot.child("pname").getValue().toString();
                    String desc = snapshot.child("description").getValue().toString();
                    String price = snapshot.child("price").getValue().toString();
                    String image = snapshot.child("image").getValue().toString();

                    et_name.setText(name);
                    et_description.setText(desc);
                    et_price.setText(price);
                    Picasso.get().load(image).into(imageView);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

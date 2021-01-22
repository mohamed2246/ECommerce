package com.example.ecommerce.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Admin_new_product_Activity extends AppCompatActivity {
    String catogry_name , description , price , pname , save_current_date , save_current_time;
    Button AddNewProductButton;
    EditText Input_Product_name, input_product_description, input_product_price;
    ImageView input_product_image;
    private static final int gallary_pick = 1;
    private Uri ImageURI;
    private String product_key , downloadImageUrl;
    private StorageReference product_image_ref;
    private ProgressDialog loadingBar;
    DatabaseReference products_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_product_);
        catogry_name = getIntent().getExtras().getString("catogary");
        product_image_ref = FirebaseStorage.getInstance().getReference().child("Product Images");
        products_ref= FirebaseDatabase.getInstance().getReference().child("Products");
        loadingBar = new ProgressDialog(this);
        AddNewProductButton = findViewById(R.id.add_new_product);
        Input_Product_name = findViewById(R.id.product_name);
        input_product_description = findViewById(R.id.product_description);
        input_product_price = findViewById(R.id.product_price);
        input_product_image = findViewById(R.id.select_product_image);
        Toast.makeText(this, catogry_name, Toast.LENGTH_SHORT).show();
        input_product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_galary();
            }
        });

        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate_product_data();
            }
        });

    }

    private void validate_product_data() {
        description = input_product_description.getText().toString();
        pname = Input_Product_name.getText().toString();
        price = input_product_price.getText().toString();

        if (ImageURI == null){
            Toast.makeText(this, "Product Image Is Mendatory", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(description)){
            Toast.makeText(this, "Please Write Product decription", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pname)){
            Toast.makeText(this, "Please Write Product name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(price)){
            Toast.makeText(this, "Please Write Product Price", Toast.LENGTH_SHORT).show();
        }
        else {
            store_product_information();
        }
    }

    private void store_product_information() {
        loadingBar.setTitle("Add new Product");
        loadingBar.setMessage("Dear Admin , please wait while we are adding the new product");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        save_current_date = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss ");
        save_current_time = currentTime.format(calendar.getTime());
        product_key = save_current_date+save_current_time;

        final StorageReference file_path = product_image_ref.child(ImageURI.getLastPathSegment() + product_key + ".jpg");
        final UploadTask uploadTask = file_path.putFile(ImageURI);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.getMessage();
                Toast.makeText(Admin_new_product_Activity.this, "error " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Admin_new_product_Activity.this, "Product Image Upload Successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask =uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadImageUrl = file_path.getDownloadUrl().toString();
                        return file_path.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(Admin_new_product_Activity.this, "Product image Url to database succesafully", Toast.LENGTH_SHORT).show();
                            save_product_info_to_data_base();
                        }
                    }
                });

            }

        });
    }

    private void save_product_info_to_data_base() {
        HashMap<String , Object> product_map= new HashMap<>();
        product_map.put("pid" , product_key);
        product_map.put("date" , save_current_date);
        product_map.put("time" , save_current_time);
        product_map.put("description" , description);
        product_map.put("image" , downloadImageUrl);
        product_map.put("catogry" , catogry_name);
        product_map.put("price" , price);
        product_map.put("pname" , pname);

        products_ref.child(product_key).updateChildren(product_map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(Admin_new_product_Activity.this , Admin_Catigory_activity.class);
                    startActivity(intent);
                    loadingBar.dismiss();
                    Toast.makeText(Admin_new_product_Activity.this, "Product is Added successfully... ", Toast.LENGTH_SHORT).show();
                }
                else {
                    loadingBar.dismiss();
                    String message = task.getException().getMessage();
                    Toast.makeText(Admin_new_product_Activity.this, "Error " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void open_galary() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, gallary_pick);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==gallary_pick && resultCode==RESULT_OK && data!=null ){
            ImageURI = data.getData();
            input_product_image.setImageURI(ImageURI);
        }
    }
}

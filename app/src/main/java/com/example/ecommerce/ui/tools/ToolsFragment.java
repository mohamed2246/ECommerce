package com.example.ecommerce.ui.tools;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.ecommerce.NAVActivity;
import com.example.ecommerce.R;
import com.example.ecommerce.pervalent.prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    CircleImageView profile_image_view;
    EditText name_edite_text, phone_edit_text, address_edit_text;
    Button saved_btn;
    TextView profile_change;
    Toolbar toolbar;
    String myUrl = "";
    Uri uri = null;
    String checker = "";
    StorageReference storageprofile_picture_ref;
    int galary = 1;
    UploadTask uploadTask;
    ProgressDialog loading_dialog;
    DatabaseReference my_root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        ((NAVActivity) getActivity()).imageView.setVisibility(View.VISIBLE);
        ((NAVActivity) getActivity()).fab.hide();
        saved_btn = root.findViewById(R.id.Update);
        profile_image_view = root.findViewById(R.id.set_profile_image);
        name_edite_text = root.findViewById(R.id.set_progile_name);
        phone_edit_text = root.findViewById(R.id.set_progile_phone);
        address_edit_text = root.findViewById(R.id.set_progile_address);
        profile_change = root.findViewById(R.id.change_profile);
        my_root = FirebaseDatabase.getInstance().getReference();
        User_info_display(profile_image_view, name_edite_text, address_edit_text, phone_edit_text);
        storageprofile_picture_ref = FirebaseStorage.getInstance().getReference().child("Profile pictures");
        loading_dialog = new ProgressDialog(getContext());
        saved_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checker.equals("clicked")) {

                    user_info_saved();

                } else {

                    update_only_user_info();
                }
            }
        });

        profile_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "helllo", Toast.LENGTH_SHORT).show();
            }
        });

        profile_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker = "clicked";

                /*CropImage.activity(Uri.parse(myUrl))
                        .setAspectRatio(1, 1)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(getActivity());*/
                Intent galary_intent = new Intent();
                galary_intent.setAction(Intent.ACTION_GET_CONTENT);
                galary_intent.setType("image/*");
                startActivityForResult(galary_intent, galary);
            }
        });


        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null &&data.getData()!=null) {

            CropImage.ActivityResult activityResult = CropImage.getActivityResult(data);
             uri = activityResult.getUri();
            Toast.makeText(getContext(), uri.toString(), Toast.LENGTH_SHORT).show();
            Picasso.get().load(uri).into(profile_image_view);


        }else {

            Toast.makeText(getContext(), "Try Again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), Admin_Catigory_activity.class));
        }
*/


        if (requestCode == galary && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            Picasso.get().load(uri).into(profile_image_view);
            CropImage.activity(uri)
                    .setAspectRatio(1, 1)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(getActivity());
        }

    }

    private void update_only_user_info() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        Map<String, Object> user_map = new HashMap<>();
        user_map.put("name", name_edite_text.getText().toString());
        user_map.put("phone", phone_edit_text.getText().toString());
        user_map.put("address", address_edit_text.getText().toString());
        startActivity(new Intent(getContext(), NAVActivity.class));
        ref.child(prevalent.current_online_users.getPhone()).updateChildren(user_map);
        Toast.makeText(getContext(), "Profile Info updated successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getContext(), NAVActivity.class));

        getActivity().finish();


    }

    private void user_info_saved() {
        if (TextUtils.isEmpty(name_edite_text.getText().toString())) {
            Toast.makeText(getContext(), "Name is mendatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone_edit_text.getText().toString())) {
            Toast.makeText(getContext(), "phone is mendatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(address_edit_text.getText().toString())) {
            Toast.makeText(getContext(), "Address is mendatory", Toast.LENGTH_SHORT).show();
        } else if (checker.equals("clicked")) {
            UploadImage();
        }

    }

    private void UploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("please wait , while we are updating your account information ... ");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (uri != null) {
            final StorageReference fileref = storageprofile_picture_ref.child(prevalent.current_online_users.getPhone() + ".jpg");

            uploadTask = fileref.putFile(uri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fileref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri download_uri = task.getResult();
                        uri = download_uri;
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                        Map<String, Object> user_map = new HashMap<>();
                        user_map.put("name", name_edite_text.getText().toString());
                        user_map.put("phone", phone_edit_text.getText().toString());
                        user_map.put("address", address_edit_text.getText().toString());
                        user_map.put("image", uri.toString());
                        progressDialog.dismiss();
                        startActivity(new Intent(getContext(), NAVActivity.class));
                        ref.child(prevalent.current_online_users.getPhone()).updateChildren(user_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Database Updated sucsessafully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Database Updated faiiild", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        Toast.makeText(getContext(), "Profile Info updated successfully", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }

            });


        } else {
            Toast.makeText(getContext(), "image is not selected", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }

    }

    private void User_info_display(final CircleImageView profile_image_view, final EditText name_edite_text, final EditText address_edit_text, final EditText phone_edit_text) {
        Toast.makeText(getContext(), prevalent.current_online_users.getPhone(), Toast.LENGTH_SHORT).show();
        DatabaseReference User_ref = FirebaseDatabase.getInstance().getReference().child("Users").child(prevalent.current_online_users.getPhone());
        User_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.child("image").exists()) {
                        String image = snapshot.child("image").getValue().toString();
                        String name = snapshot.child("name").getValue().toString();
                        String phone = snapshot.child("phone").getValue().toString();
                        String address = snapshot.child("address").getValue().toString();
                        Picasso.get().load(image).placeholder(R.drawable.profile_image).into(profile_image_view);
                        name_edite_text.setText(name);
                        phone_edit_text.setText(phone);
                        address_edit_text.setText(address);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
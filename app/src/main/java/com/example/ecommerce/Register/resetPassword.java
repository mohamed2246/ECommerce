package com.example.ecommerce.Register;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.R;
import com.example.ecommerce.User.NAVActivity;
import com.example.ecommerce.UserData.user_data;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class resetPassword extends AppCompatActivity {

    private String check;
    private TextView pageTitle, title_question;
    private EditText phone_num, question1, question2;
    private Button verify_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        check = getIntent().getExtras().getString("check");
        pageTitle = findViewById(R.id.page_title);
        verify_btn = findViewById(R.id.verify_btn);
        phone_num = findViewById(R.id.find_phone_num);
        question1 = findViewById(R.id.question1);
        question2 = findViewById(R.id.question2);
        title_question = findViewById(R.id.full_text);


    }

    @Override
    protected void onStart() {
        super.onStart();
        phone_num.setVisibility(View.GONE);
        if (check.equals("settings")) {
            pageTitle.setText("Set Questions");
            title_question.setText("Please set Answer for The Following Security Questions ? ");
            verify_btn.setText("Set");
            desplayPreviosAnswers();
            verify_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setAnswers();


                }
            });

        } else if (check.equals("login")) {
            phone_num.setVisibility(View.VISIBLE);
            verify_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Verefy_User();
                }
            });

        }
    }

    private void Verefy_User() {
        final String phone = phone_num.getText().toString();
        final String answer1 = question1.getText().toString().toLowerCase();
        final String answer2 = question2.getText().toString().toLowerCase();

        if (!phone.equals("")&&!answer1.equals("")&&!answer2.equals("")){

            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(phone);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String mphone = snapshot.child("phone").getValue().toString();


                        if (mphone.equals(phone)) {
                            if (snapshot.hasChild("Questions")) {
                                String mQ1 = snapshot.child("Questions").child("answer1").getValue().toString();
                                String mQ2 = snapshot.child("Questions").child("answer2").getValue().toString();

                                if (!mQ1.equals(answer1)) {
                                    Toast.makeText(resetPassword.this, "Your First Answer Is Wrong", Toast.LENGTH_SHORT).show();
                                } else if (!mQ2.equals(answer2)) {

                                    Toast.makeText(resetPassword.this, "Your Second Answer Is Wrong", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(resetPassword.this);
                                    builder.setTitle("New Password ");
                                    final  EditText newPassword = new EditText(getApplicationContext());
                                    newPassword.setHint("Write Password Here...");
                                    newPassword.setPadding(5,5,5,5);
                                    newPassword.setBackgroundResource(R.drawable.button_white);
                                    builder.setView(newPassword);
                                    builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (!newPassword.getText().toString().equals("")){
                                                ref.child("password").setValue(newPassword.getText().toString())
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()){
                                                                    Toast.makeText(resetPassword.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                                                    Intent intent =new Intent(getApplicationContext(),login_activity.class);
                                                                    startActivity(intent);
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                                    builder.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });
                                    builder.show();
                                }

                            }
                            else {
                                Toast.makeText(resetPassword.this, "You Didn't Set The Security Questions  ", Toast.LENGTH_SHORT).show();
                            }

                        } else {

                            Toast.makeText(resetPassword.this, "This User Not Exist ", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        Toast.makeText(resetPassword.this, "This Phone Number Not Exist ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




        }
        else {
            Toast.makeText(this, "Please Complete The Form", Toast.LENGTH_SHORT).show();
        }


    }

    private void desplayPreviosAnswers() {
        user_data user_data = new user_data(this);
        String phone = user_data.getPHONE();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(phone);
        ref.child("Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    question1.setText(snapshot.child("answer1").getValue().toString());
                    question2.setText(snapshot.child("answer2").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setAnswers() {

        String answer1 = question1.getText().toString().toLowerCase();
        String answer2 = question2.getText().toString().toLowerCase();

        if (answer1.equals("") || answer2.equals("")) {
            Toast.makeText(resetPassword.this, "Please Answer Both Question", Toast.LENGTH_SHORT).show();
        } else {
            user_data user_data = new user_data(resetPassword.this);
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(user_data.getPHONE());
            Map<String, Object> user_data_Map = new HashMap<>();
            user_data_Map.put("answer1", answer1);
            user_data_Map.put("answer2", answer2);

            ref.child("Questions").updateChildren(user_data_Map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(resetPassword.this, "You Have Set Security Quetions Successfully .", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(resetPassword.this, NAVActivity.class));
                    }
                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(resetPassword.this, NAVActivity.class);
        startActivity(intent);
        finish();
    }
}

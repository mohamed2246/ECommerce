package com.example.ecommerce.User;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ecommerce.R;
import com.example.ecommerce.UserData.user_data;
import com.example.ecommerce.ui.send.Home_new_framgent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import io.paperdb.Paper;

public class NAVActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public Toolbar toolbar;
    public ImageView imageView;
    DatabaseReference user_ref = FirebaseDatabase.getInstance().getReference().child("Users");
    String phone, type = "";
    public FloatingActionButton fab;
    public static int flag = 0;
    user_data user_data;
    NavigationView navigationView;

    @SuppressLint({"CutPasteId", "RestrictedApi"})
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);


        user_data = new user_data(NAVActivity.this);
        navigationView = findViewById(R.id.nav_view);
        phone = user_data.getPHONE();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageView = findViewById(R.id.back_home);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Paper.init(this);

        fab = findViewById(R.id.fab);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Home_new_framgent();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                toolbar.setTitle("Home");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home, R.id.nav_cart, R.id.nav_search,
                R.id.nav_settings, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
///////////////////////////////////////// Profile Image //////////////////////////////////////////
        View headerview = navigationView.getHeaderView(0);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
                //type = getIntent().getExtras().getString("admin").toString();
                if (!intent.hasExtra("admin")) {

                    final TextView user_name_text_view = headerview.findViewById(R.id.user_profile_name);
                    final ImageView user_name_Image_view = headerview.findViewById(R.id.user_profile_image);

                    user_ref.child(phone).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String name = "";
                            String image = "";



                            if (snapshot.hasChild("image")){

                                name = snapshot.child("name").getValue().toString();
                                image = snapshot.child("image").getValue().toString();
                                user_name_text_view.setText(name);
                                Picasso.get().load(image).placeholder(R.drawable.profile_image).into(user_name_Image_view);
                            }
                            else {

                                Toast.makeText(NAVActivity.this, "You Should Complete Your Information .", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else {
                    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setDisplayShowHomeEnabled(false);
                    fab.setVisibility(View.GONE);
                }


        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    public void switchContent(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().
                remove(getSupportFragmentManager().findFragmentById(R.id.home_activity)).commit();

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}

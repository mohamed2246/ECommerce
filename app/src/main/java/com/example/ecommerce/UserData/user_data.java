package com.example.ecommerce.UserData;

import android.content.Context;
import android.content.SharedPreferences;

public class user_data {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;
    Context context;
    private static final String FILE_NAME = "myFile";
    private static final String FILE_PHONE = "user_phone";
    private static final String FILE_STUTS = "user_status";
    private static final String FILE_PASSWORD = "user_pass";


    public user_data( Context context) {
        this.context = context;
        sharedPreferences= context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveDate(String phone ,String password , boolean stuts){
        editor.putString(FILE_PHONE,phone);
        editor.putBoolean(FILE_STUTS ,stuts);
        editor.putString(FILE_PASSWORD ,password);
        editor.apply();
    }
    public String getPHONE(){
        String mphone = sharedPreferences.getString(FILE_PHONE,null) ;
        return mphone;
    }
    public String getPassword(){
        String mpass = sharedPreferences.getString(FILE_PASSWORD,null) ;
        return mpass;
    }


    public boolean isLogin (){
        return sharedPreferences.getBoolean(FILE_STUTS,false);
    }
}

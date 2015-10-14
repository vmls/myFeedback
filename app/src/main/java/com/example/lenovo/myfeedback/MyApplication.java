package com.example.lenovo.myfeedback;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.parse.Parse;

/**
 * Created by lenovo on 10/12/2015.
 */
public class MyApplication extends Application
{

    Context context;
    public static SharedPreferences pref;
    public static String NAME = "name";
    public static String PHONE = "phone";
    public static String EMAIL = "email";

    public static String NULL_STRING = "";

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Parse.initialize(this,getString(R.string.app_id_parse),getString(R.string.client_key));
        //pref = context.getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
    }
}
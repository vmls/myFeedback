package com.example.lenovo.myfeedback;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.parse.ParseUser;

/**
 * Created by lenovo on 10/15/2015.
 */
public class DispatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(DispatchActivity.this,"Dispatch",Toast.LENGTH_SHORT).show();
        if(ParseUser.getCurrentUser()!=null){
            startActivity(new Intent(DispatchActivity.this, DummyMain.class));
        }
        else{
            startActivity(new Intent(DispatchActivity.this, LogInOrSignUp.class));
        }
    }
}

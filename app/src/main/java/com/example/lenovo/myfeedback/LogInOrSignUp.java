package com.example.lenovo.myfeedback;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by lenovo on 10/15/2015.
 */
public class LogInOrSignUp extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_or_signup);
        Toast.makeText(LogInOrSignUp.this, "LogInOrSignUp", Toast.LENGTH_SHORT).show();
    }
}

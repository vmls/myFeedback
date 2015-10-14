package com.example.lenovo.myfeedback;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name_edit;
    private EditText phone_edit;
    private EditText email_edit;
    private Button submit_button;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(MyApplication.pref.getString(MyApplication.PHONE,MyApplication.NULL_STRING)==
                MyApplication.NULL_STRING) {

            setContentView(R.layout.sign_up_screen);
            name_edit = (EditText)findViewById(R.id.user_name);
            phone_edit = (EditText)findViewById(R.id.phone_no);
            email_edit = (EditText)findViewById(R.id.email_id);
            submit_button =(Button)findViewById(R.id.submit);
            submit_button.setOnClickListener(this);
        }
        else
        {

            intent = new Intent(this, ContactsLoadingActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.submit){

            String name = name_edit.getText().toString();
            String phone = phone_edit.getText().toString();
            String email = phone_edit.getText().toString();

            SharedPreferences.Editor editor = MyApplication.pref.edit();
            editor.putString(MyApplication.NAME, name);
            editor.putString(MyApplication.PHONE, phone);
            editor.putString(MyApplication.EMAIL, email);
            editor.commit();

            intent = new Intent(MainActivity.this, ContactsLoadingActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

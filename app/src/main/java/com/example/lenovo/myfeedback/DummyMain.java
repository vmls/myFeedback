package com.example.lenovo.myfeedback;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by lenovo on 10/15/2015.
 */
public class DummyMain extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummy_main);
        Toast.makeText(DummyMain.this, "DummyMain", Toast.LENGTH_SHORT).show();
    }
}

package com.edwardvanraak.materialbarcodescannerexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProductAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

         String testing = getIntent().getStringExtra("barcode");

        TextView info = (TextView)findViewById(R.id.info);
        info.setText(testing);

    }
}

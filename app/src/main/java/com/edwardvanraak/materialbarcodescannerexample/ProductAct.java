package com.edwardvanraak.materialbarcodescannerexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ProductAct extends AppCompatActivity {

    private DatabaseReference myRef;
    private FirebaseDatabase database;

    TextView txt_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_product);
        txt_result = (TextView) findViewById(R.id.info);

        String datatxt = getIntent().getStringExtra("barcode");
        postRequest(datatxt);
    }

    private void postRequest(String datatxt) {
        database = FirebaseDatabase.getInstance();
//        database.setPersistenceEnabled(true);
        myRef = database.getReference("users_item").child("product");
        myRef.push().setValue(datatxt);

        txt_result.setText(datatxt);

    }

}

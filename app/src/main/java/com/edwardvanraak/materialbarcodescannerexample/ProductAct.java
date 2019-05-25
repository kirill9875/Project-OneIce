package com.edwardvanraak.materialbarcodescannerexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class ProductAct extends AppCompatActivity {

    TextView txt_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_product);
        String datatxt = getIntent().getStringExtra("barcode");
    }

    public void compareKeys(){
        FirebaseDatabaseHelper getKey = new FirebaseDatabaseHelper();

        List<String> ourKey = getKey.takeKeysList(); // список ключей продуктов




    }


}

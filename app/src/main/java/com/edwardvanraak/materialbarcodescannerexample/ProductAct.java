package com.edwardvanraak.materialbarcodescannerexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

         String datatxt = getIntent().getStringExtra("barcode");

        try {
            parseJsontxt(datatxt);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void parseJsontxt(String datatxt) throws JSONException {
        JSONObject scanQRtoJson = new JSONObject(datatxt);
        long id = scanQRtoJson.getLong("orderID");

        String name = scanQRtoJson.getString("shopperName");
        String email = scanQRtoJson.getString("shopperEmail");
        String url = scanQRtoJson.getString("shopperURL");

        String contents = scanQRtoJson.getString("contents");
        TextView info = (TextView)findViewById(R.id.info);
        info.setText(contents);

        String date = scanQRtoJson.getString("data");

    }
}

package com.edwardvanraak.materialbarcodescannerexample.madels;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.edwardvanraak.materialbarcodescannerexample.FirebaseDatabaseHelper;
import com.edwardvanraak.materialbarcodescannerexample.MainActivity;

import com.edwardvanraak.materialbarcodescannerexample.R;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class NewProduct extends AppCompatActivity {


    private EditText mCustomer_editText;
    private EditText mTitle_editText;
    private EditText mDate_editText;
    private EditText mEmails_editTxt;
    private EditText mURL_editTxt;
    private TextView mDescriptions_TxtView;
    private EditText mDescriptor_editText;


    private String key;
    private String title;
    private String date;
    private String description;
    private String customer;
    private String email;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        //take intent
        key = getIntent().getStringExtra("key");
        title = getIntent().getStringExtra("title");
        date = getIntent().getStringExtra("date");
        description = getIntent().getStringExtra("description");
        customer = getIntent().getStringExtra("customer");
        email = getIntent().getStringExtra("email");
        url = getIntent().getStringExtra("url");


        //Name product
        mTitle_editText = (EditText) findViewById(R.id.title_editTxt);
        mTitle_editText.setText(title);
        //Customer
        mCustomer_editText = (EditText) findViewById(R.id.customer_editText);
        mCustomer_editText.setText(customer);

        mEmails_editTxt= ( EditText) findViewById(R.id.email_editText);
        mEmails_editTxt.setText(email);

        mURL_editTxt= ( EditText) findViewById(R.id.url_editText);
        mURL_editTxt.setText(url);

        mDescriptions_TxtView = (TextView)findViewById(R.id.descriptions_txtView);
        mDescriptor_editText = ( EditText) findViewById(R.id.descriptions_editText);
        mDescriptor_editText.setText(description);

        mDate_editText = (EditText) findViewById(R.id.date_editTxt);
        mDate_editText.setText(date);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.newproduct_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_save) {
            Product product = new Product();
            product.setTitle(mTitle_editText.getText().toString());
            product.setCustomer(mCustomer_editText.getText().toString());
            product.setShopperEmail(mEmails_editTxt.getText().toString());
            product.setShopperURL(mURL_editTxt.getText().toString());
            product.setDescription(mDescriptor_editText.getText().toString());
            product.setDate(mDate_editText.getText().toString());


            new FirebaseDatabaseHelper().addProduct(product, new FirebaseDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<Product> products, List<String> keys) {

                }

                @Override
                public void DataIsInserted() {

                    Toast.makeText(NewProduct.this, "Product Added", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(NewProduct.this, MainActivity.class);
                    i.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }

                @Override
                public void DataIsUpdated() {

                }

                @Override
                public void DataIsDeleted() {

                }
            });

        } /*else if (item.getItemId() == R.id.back_btn){
            Intent i = new Intent(NewProduct.this, MainActivity.class);
            i.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }*/

        return true;
    }
}

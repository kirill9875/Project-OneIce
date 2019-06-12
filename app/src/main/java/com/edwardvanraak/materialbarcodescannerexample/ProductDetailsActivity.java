package com.edwardvanraak.materialbarcodescannerexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.edwardvanraak.materialbarcodescannerexample.madels.NewProduct;
import com.edwardvanraak.materialbarcodescannerexample.madels.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class ProductDetailsActivity extends AppCompatActivity {

    private EditText mCustomer_editText;
    private EditText mTitle_editText;
    private EditText mEmails_editTxt;
    private EditText mURL_editTxt;
    private EditText mDescriptor_editText;
    private EditText mDate_editText;
    private TextView mDescriptions_TxtView;

    private String key;
    private String title;
    private String customer;
    private String url;
    private String email;
    private String description;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        key = getIntent().getStringExtra("key");
        title = getIntent().getStringExtra("title");
        date = getIntent().getStringExtra("date");
        customer = getIntent().getStringExtra("customer");

        description = getIntent().getStringExtra("description");
        email = getIntent().getStringExtra("email");
        url = getIntent().getStringExtra("url");

        mTitle_editText = (EditText) findViewById(R.id.title_editTxt1);
        mTitle_editText.setText(title);
        //Customer
        mCustomer_editText = (EditText) findViewById(R.id.customer_editText1);
        mCustomer_editText.setText(customer);

        mEmails_editTxt= ( EditText) findViewById(R.id.email_editText1);
        mEmails_editTxt.setText(email);

        mURL_editTxt= ( EditText) findViewById(R.id.url_editText1);
        mURL_editTxt.setText(url);

        mDescriptions_TxtView = (TextView)findViewById(R.id.descriptions_txtView1);
        mDescriptor_editText = ( EditText) findViewById(R.id.descriptions_editText1);
        mDescriptor_editText.setText(description);

        mDate_editText = (EditText) findViewById(R.id.date_editTxt1);
        mDate_editText.setText(date);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_details_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_item) {
            new FirebaseDatabaseHelper().deleteProduct(key, new FirebaseDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<Product> products, List<String> keys) {

                }
                @Override
                public void DataIsInserted() {

                }

                @Override
                public void DataIsUpdated() {

                }
                @Override
                public void DataIsDeleted() {

                    Toast.makeText(ProductDetailsActivity.this, "Product deleted", Toast.LENGTH_LONG).show();
                    finish(); return;

                }
            });

        } else if (item.getItemId() == R.id.save_item) {
            Product product = new Product();
            product.setTitle(mTitle_editText.getText().toString());
            product.setCustomer(mCustomer_editText.getText().toString());
            product.setShopperEmail(mEmails_editTxt.getText().toString());
            product.setShopperURL(mURL_editTxt.getText().toString());
            product.setDescription(mDescriptor_editText.getText().toString());
            product.setDate(mDate_editText.getText().toString());

            new FirebaseDatabaseHelper().updateProduct(key, product, new FirebaseDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<Product> products, List<String> keys) {

                }

                @Override
                public void DataIsInserted() {

                }

                @Override
                public void DataIsUpdated() {
                    Toast.makeText(ProductDetailsActivity.this, "product record has been updated successfullyy" , Toast.LENGTH_LONG).show();
                    finish(); return;

                }

                @Override
                public void DataIsDeleted() {

                }
            });
        }

        return true;
    }

}

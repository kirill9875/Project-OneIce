package com.edwardvanraak.materialbarcodescannerexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.edwardvanraak.materialbarcodescannerexample.madels.Product;

import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    private EditText mCustomer_editText;
    private EditText mDescriptor_editText;
    private EditText mTitle_editText;
    private EditText mDate_editText;
    private Button mUpdate_btn;
    private Button mBack_btn;
    private Button mDelete_btn;
    private TextView mTxtDescription;

    private String key;
    private String title;
    private String date;
    private String description;
    private String customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        //прием QR
        takeQR();

        key = getIntent().getStringExtra("key");
        title = getIntent().getStringExtra("title");
        date = getIntent().getStringExtra("date");
        description = getIntent().getStringExtra("description");
        customer = getIntent().getStringExtra("customer");

        mTitle_editText = (EditText) findViewById(R.id.title_editText);
        mTitle_editText.setText(title);


        mCustomer_editText = (EditText) findViewById(R.id.customer_editText);
        mCustomer_editText.setText(customer);
        mDate_editText = (EditText) findViewById(R.id.date_editText);
        mDate_editText.setText(date);

        mDescriptor_editText = ( EditText) findViewById(R.id.decription_editText);
        mDescriptor_editText.setText(description);

        mUpdate_btn = (Button) findViewById(R.id.update_btn);
        mBack_btn = (Button) findViewById(R.id.btn_back);
        mDelete_btn = (Button) findViewById(R.id.delete_btn);

        mUpdate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = new Product();
                product.setTitle(mTitle_editText.getText().toString());
                product.setDescription(mDescriptor_editText.getText().toString());
                product.setDate(mDate_editText.getText().toString());
                product.setCustomer(mCustomer_editText.getText().toString());

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

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });

        mDelete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
        mBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();return;
            }
        });

    }

    private String takeQR() {
        Intent intent = getIntent();
        String str = intent.getStringExtra("barcode");
        return str ;
    }
}

package com.edwardvanraak.materialbarcodescannerexample.madels;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.edwardvanraak.materialbarcodescannerexample.FirebaseDatabaseHelper;
import com.edwardvanraak.materialbarcodescannerexample.R;

import java.util.List;

public class NewProduct extends AppCompatActivity {


    private EditText mCustomer_editText;
    private EditText mDescriptor_editText;
    private EditText mTitle_editText;
    private EditText mDate_editText;
    private Button mAdd_btn;
    private Button mBack_btn;
    private TextView mTxtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        mTitle_editText = (EditText) findViewById(R.id.title_editText);
        mTxtDescription = (TextView) findViewById(R.id.description_textView);

        mCustomer_editText = (EditText) findViewById(R.id.customer_editText);
        mDate_editText = (EditText) findViewById(R.id.date_editText);
        mDescriptor_editText = ( EditText) findViewById(R.id.decription_editText);

        mAdd_btn = (Button) findViewById(R.id.update_btn);
        mBack_btn = (Button) findViewById(R.id.btn_back);

        mAdd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = new Product();
                product.setCustomer(mCustomer_editText.getText().toString());
                product.setDate(mDate_editText.getText().toString());
                product.setDescription(mDescriptor_editText.getText().toString());
                product.setTitle(mTitle_editText.getText().toString());

                new FirebaseDatabaseHelper().addProduct(product, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Product> products, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                        Toast.makeText(NewProduct.this, "Product Added", Toast.LENGTH_LONG).show();

                        finish();
                        return;
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });

        mBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });
    }
}

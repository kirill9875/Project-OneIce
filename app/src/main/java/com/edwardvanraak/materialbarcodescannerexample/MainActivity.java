package com.edwardvanraak.materialbarcodescannerexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.edwardvanraak.materialbarcodescannerexample.madels.Product;
import com.edwardvanraak.materialbarcodescannerexample.madels.RecyclerView_Config;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;


import java.util.List;

import static junit.framework.Assert.assertNotNull;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    public static final String BARCODE_KEY = "BARCODE";

    private Barcode barcodeResult;
//    private TextView result;

    private static final String TAG = "PostDetailActivity";


    private DatabaseReference myRef;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_products);

        workFirebase();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        result = (TextView) findViewById(R.id.barcodeResult);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        assertNotNull(result);
        assertNotNull(fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startScan();
            }
        });
        if(savedInstanceState != null){
            Barcode restoredBarcode = savedInstanceState.getParcelable(BARCODE_KEY);
            if(restoredBarcode != null){
//                result.setText(restoredBarcode.rawValue);
                barcodeResult = restoredBarcode;
            }
        }
    }

    private void workFirebase() {
        new FirebaseDatabaseHelper().readProducts(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Product> products, List<String> keys) {
                new RecyclerView_Config().setConfig(mRecyclerView,MainActivity.this, products, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdeted() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }

    private void showCode(String txt) {
//        myRef = FirebaseDatabase.getInstance().getReference();
//
//        myRef.child("users_item").child("product").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                String value = dataSnapshot.getValue(String.class);
//                result.setText(value + "112");
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                result.setText("errrrrrrrrrrroor");
//                // Failed to read value
//
//            }
//        });

        Intent intent = new Intent(MainActivity.this, ProductAct.class);

        intent.putExtra("barcode",txt);
        startActivity(intent);
    }




    private void startScan() {

        final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                .withActivity(MainActivity.this)
                .withEnableAutoFocus(true)
                .withBleepEnabled(true)
                .withBackfacingCamera()
                .withCenterTracker()
                .withText("Scanning...")
                .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                    @Override
                    public void onResult(Barcode barcode) {
                        barcodeResult = barcode;
                        String txt = barcode.rawValue;
                        showCode(txt);
                    }
                })
                .build();
        materialBarcodeScanner.startScan();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BARCODE_KEY, barcodeResult);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != MaterialBarcodeScanner.RC_HANDLE_CAMERA_PERM) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }
        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startScan();
            return;
        }
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error")
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(android.R.string.ok, listener)
                .show();
    }


}

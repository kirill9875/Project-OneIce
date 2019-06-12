package com.edwardvanraak.materialbarcodescannerexample;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.edwardvanraak.materialbarcodescannerexample.madels.NewProduct;
import com.edwardvanraak.materialbarcodescannerexample.madels.Product;
import com.edwardvanraak.materialbarcodescannerexample.madels.RecyclerView_Config;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static junit.framework.Assert.assertNotNull;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefernceProduct;

//    private Toolbar toolbar;

    private String productName;
    private String id;
    private String shopperName;
    private String shopperEmail;
    private String shopperURL;
    private String description;
    private String date;

    public static final String BARCODE_KEY = "BARCODE";

    private Barcode barcodeResult;

    public List<String> keysLists;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        //add logo in Toolbar
//        getSupportActionBar().setIcon(getDrawable(R.mipmap.ic_launcher));

        readFirebase();
        System.out.println(new FirebaseDatabaseHelper());

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_products);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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

                barcodeResult = restoredBarcode;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Addproduct:
                startActivity(new Intent(this, NewProduct.class));
                return true;
            case R.id.deleteAll_item :
                new FirebaseDatabaseHelper().deleteAllProduct(new FirebaseDatabaseHelper.DataStatus() {
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

                        Toast.makeText(MainActivity.this, "All Product deleted", Toast.LENGTH_LONG).show();


                    }
                });
        }
        return super.onOptionsItemSelected(item);
    }

    private void readFirebase() {
        new FirebaseDatabaseHelper().readProducts(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Product> products, List<String> keys) {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                new RecyclerView_Config().setConfig(mRecyclerView,MainActivity.this, products, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
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
                        parseJSON(txt);
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

        public void parseJSON (String str){

        if (str != null){
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(str);
                id = jsonObject.getString("orderID");
                productName = jsonObject.getString("productName");
                shopperName = jsonObject.getString("shopperName");
                shopperEmail = jsonObject.getString("shopperEmail");
                shopperURL = jsonObject.getString("shopperURL");
                description = jsonObject.getString("description");
                date = jsonObject.getString("date");

                compareVal(id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(MainActivity.this, "QR code not find" , Toast.LENGTH_LONG).show();
        }
    }

    private void compareVal(final String id) {

        mDatabase = FirebaseDatabase.getInstance();
        mRefernceProduct = mDatabase.getReference("product");

        mRefernceProduct.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean check = false;
                for (DataSnapshot keNode : dataSnapshot.getChildren()){
                    String keys = keNode.getKey();
                    assert keys != null;

                    if (keys.equals(id)){
                        check = true;
                        Toast.makeText(MainActivity.this, "product record has been updated successfully" , Toast.LENGTH_LONG).show();
                        break;
                    }

                }

                if (check == false){
                    startProductAct();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void startProductAct() {
        Intent intent = new Intent(this, NewProduct.class);
        intent.putExtra("key",id);
        intent.putExtra("title",productName);
        intent.putExtra("customer",shopperName);
        intent.putExtra("email",shopperEmail);
        intent.putExtra("url",shopperURL);
        intent.putExtra("description",description);
        intent.putExtra("date",date);
        startActivity(intent);

    }

}

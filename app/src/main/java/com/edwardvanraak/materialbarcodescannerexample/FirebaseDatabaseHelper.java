package com.edwardvanraak.materialbarcodescannerexample;

import android.support.annotation.NonNull;

import com.edwardvanraak.materialbarcodescannerexample.madels.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FirebaseDatabaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefernceProduct;
    private List<Product> products = new ArrayList<>();
    List<String> keys = new ArrayList<>();



    public interface DataStatus{
        void DataIsLoaded (List<Product> products, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mRefernceProduct = mDatabase.getReference("product");
    }

    public void readProducts(final DataStatus dataStatus){
        mRefernceProduct.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                products.clear();

                for (DataSnapshot keNode : dataSnapshot.getChildren()){
                   keys.add(keNode.getKey()) ;
                   Product product = keNode.getValue(Product.class);
                   products.add(product); // значения по ключам типа json. в sout не выведет!

                }
                dataStatus.DataIsLoaded(products,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public List<Product> takeProductList() {
        return products;
    }


    public List<String> takeKeysList() {

        return keys;
    }

    public void addProduct(Product product,final DataStatus dataStatus){
        String key = mRefernceProduct.push().getKey();

        mRefernceProduct.child(key).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsInserted();

            }
        });
    }

    public void updateProduct(String key, Product product, final DataStatus dataStatus ){
        mRefernceProduct.child(key).setValue(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       dataStatus.DataIsUpdated();
                    }
                });
    }
    public void deleteProduct ( String key, final DataStatus dataStatus){
        mRefernceProduct.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                      dataStatus.DataIsDeleted();
                    }
                });
    }

    public void deleteAllProduct ( final DataStatus dataStatus){

        mRefernceProduct.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}

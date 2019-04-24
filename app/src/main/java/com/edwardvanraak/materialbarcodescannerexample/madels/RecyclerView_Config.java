package com.edwardvanraak.materialbarcodescannerexample.madels;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edwardvanraak.materialbarcodescannerexample.R;

import java.util.List;

public class RecyclerView_Config {

    private Context mContext;
    private ProductAdapter mProductAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Product> products,List<String> keys){
        mContext= context;
        mProductAdapter = new ProductAdapter(products,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mProductAdapter);
    }


    class ProductItemView extends RecyclerView.ViewHolder{
        private TextView mTitle;
        private TextView mCustomer;
        private TextView mDescription;
        private TextView mDate;

        private String key;

        public ProductItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.product_list_item,parent,false));

            mTitle= (TextView) itemView.findViewById(R.id.title_txtView);
            mCustomer = (TextView) itemView.findViewById(R.id.customer_textView);
            mDate = (TextView) itemView.findViewById(R.id.date_textView);
            mDescription = (TextView) itemView.findViewById(R.id.doc_textView);

        }

        public void bind(Product product,String key){
            mTitle.setText(product.getTitle());
            mDescription.setText(product.getDescription());
            mDate.setText(product.getDate());
            mCustomer.setText(product.getCustomer());
            this.key = key;
        }
    }

    class ProductAdapter extends RecyclerView.Adapter<ProductItemView> {
        private List<Product> mProductList;
        private List<String> mKeys;

        public ProductAdapter(List<Product> mProductList, List<String> mKeys) {
            this.mProductList = mProductList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public ProductItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ProductItemView(viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductItemView productItemView, int i) {
            productItemView.bind(mProductList.get(i),mKeys.get(i));

        }

        @Override
        public int getItemCount() {
            return mProductList.size();
        }
    }
}

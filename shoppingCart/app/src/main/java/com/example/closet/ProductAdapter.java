package com.example.closet;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductAdapterViewHolder>{
    private String[] list;

    private final ProductAdapterOnClickHandler mHandler;

    public interface ProductAdapterOnClickHandler {
        void onClick(String IndividualProduct);
    }

    public ProductAdapter(ProductAdapterOnClickHandler clickHandler) {
        mHandler = (ProductAdapterOnClickHandler) clickHandler;
    }

    public class ProductAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        public final TextView mProduct;

        public ProductAdapterViewHolder(View view) {
            super(view);
            mProduct = (TextView) view.findViewById(R.id.product_data);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String IndividualProduct = list[adapterPosition];
            mHandler.onClick(IndividualProduct);
        }
    }



    @Override
    public ProductAdapter.ProductAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.activity_product_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new ProductAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductAdapterViewHolder productAdapterViewHolder, int i) {
        String weatherForThisDay = list[i];
        productAdapterViewHolder.mProduct.setText(weatherForThisDay);
    }

    @Override
    public int getItemCount() {
        if (null == list) return 0;
        return list.length;
    }

    public void setWeatherData(String[] weatherData) {
        list = weatherData;
        notifyDataSetChanged();
    }


}
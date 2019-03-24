package com.example.closet;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.closet.ProductAdapter.ProductAdapterOnClickHandler;

import org.json.JSONArray;
import org.json.JSONObject;
public class HomeFragment extends AppCompatActivity implements ProductAdapterOnClickHandler {
    private RecyclerView mRecyclerView;
    private ProductAdapter mProductAdapter;
    private String[] productList;
    private String[] CompleteProductList;
    public static final String EXTRA_TEXT =
            "com.example.closet.extra.MESSAGE";
    private static final String JSON_URL =
            "https://makeup-api.herokuapp.com/api/v1/products.json?product_category=liquid&product_type=foundation";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fragment);
        Toast.makeText(this, "successfull. ", Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_home_page);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_product);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mProductAdapter = new ProductAdapter(this);
        mRecyclerView.setAdapter(mProductAdapter);
        loadProducts();
    }

    private void loadProducts() {
        Log.d("TAG", "loading sravya");
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("TAG", "obj.toString()");
                productList = new String[response.length()];
                CompleteProductList = new String[response.length()];


                try {

                    Log.d("TAGGGG", response.toString());
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject product = response.getJSONObject(i);
                        String id = product.getString("id");
                        String brand = product.getString("brand");
                        String name = product.getString("name");
                        String price = product.getString("price");
                        String currency = product.getString("currency");
                        String image_link = product.getString("image_link");
                        String product_link = product.getString("product_link");
                        String website_link = product.getString("website_link");
                        String description = product.getString("description");
                        String rating = product.getString("rating");
                        String category = product.getString("category");



                        String e = name + " - " + brand + " - " + price;
                        String main = name+"sravya"+brand+"sravya"+price+"sravya"+currency+"sravya"+image_link+"sravya"+product_link+"sravya"+description+"sravya"+website_link+"sravya"+rating+"sravya"+category;
//                        mProductList.append(e);
//                        mProductList.append("\n\n\n");
                        productList[i] = e;
                        CompleteProductList[i] = main;

                    }
                    mProductAdapter.setWeatherData(productList);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG", "error sravya");

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void onClick(String IndividualProduct) {
        String text = "";
        String [] tokens = IndividualProduct.split(" - ");
        for(int i = 0; i<CompleteProductList.length; i++) {
            String[] tok = CompleteProductList[i].split("sravya");
            if(tokens[0].equals(tok[0])) {
                text = CompleteProductList[i];
            }
        }
        Context context = this;
//        Toast.makeText(context, IndividualProduct, Toast.LENGTH_SHORT).show();
        Class destinationClass = ProductActivity.class;
        Intent intentToStartProductActivity = new Intent(context, destinationClass);
        intentToStartProductActivity.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(intentToStartProductActivity);

    }
}

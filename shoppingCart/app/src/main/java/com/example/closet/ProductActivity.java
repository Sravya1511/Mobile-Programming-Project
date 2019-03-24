package com.example.closet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
public class ProductActivity extends AppCompatActivity {

    TextView name;
    TextView description;
    TextView price;
    TextView brand;
    ElegantNumberButton quantity;
    String details;
    String imgurl;
    String ProductID;
    Button addToCart, viewCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        ImageView iv = (ImageView) findViewById(R.id.iv);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        name = (TextView) findViewById(R.id.name);
        description = (TextView) findViewById(R.id.description);
        price = (TextView) findViewById(R.id.price);
        brand = (TextView) findViewById(R.id.brand);
        addToCart = (Button) findViewById(R.id.cartItem);
        viewCart = (Button) findViewById(R.id.ViewCart);
        quantity = (ElegantNumberButton) findViewById(R.id.productDetails_number_btn);
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                details = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                String[] tokens = details.split("sravya");
                System.out.println(tokens);
//                mProdDisplay.setText(details);
                name.setText("Name : " + tokens[0]);
                brand.setText("Brand : " + tokens[1]);
                price.setText(tokens[2]);
                description.setText("Description : " + tokens[6]);
                ProductID = tokens[7];


                imgurl = tokens[4];

                Log.d("Imageeeeeeeee", imgurl);
//                new DownloadImage(iv).execute(imgurl);
            }
        }
    addToCart.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
            String Name = name.getText().toString();
            String Price = price.getText().toString();
            String Brand = brand.getText().toString();
            String Quantity = quantity.getNumber().toString();
        addingToCartList(Name, Price, Brand, Quantity);
    }
    });

        viewCart.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        Intent intent = new Intent(ProductActivity.this, CartActivity.class);
        startActivity(intent);
    }
    });
}
    private void addingToCartList(final String  name, final String  price, final String brand, final String  quantity) {
        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", ProductID);
        cartMap.put("pname", name.toString());
        cartMap.put("brand", brand.toString());
        cartMap.put("price", price.toString());
        cartMap.put("quantity", quantity.toString());

        cartListRef.child("Cart Products").child(ProductID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ProductActivity.this, "added to cart", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}


     class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImage(ImageView imageView){
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlOfImage = strings[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL("http://msitmp.herokuapp.com" + urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap result){

            imageView.setImageBitmap(result);
        }
    }

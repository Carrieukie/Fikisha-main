package com.example.tabexperiment.OrdersFragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tabexperiment.R;
import com.google.gson.Gson;

public class CartViewActivity extends AppCompatActivity {
    Cart cart;
    TextView processing, courier, cost, paid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordered_cart_items);

        processing = findViewById(R.id.textView_processing);
        courier = findViewById(R.id.textView_Courier);
        cost = findViewById(R.id.textView_cost);
        paid = findViewById(R.id.textView_paid);

        Intent intent = getIntent();
        if(intent != null){
            cart = new Gson().fromJson(intent.getStringExtra("Cart"),Cart.class);
            bind(cart);
        }

    }

    private void bind(Cart cart) {
        processing.setText(cart.getApproved());
        courier.setText(cart.getCourier());
        cost.setText(cart.getCosts());
        paid.setText(cart.getPaid());
    }
}

package com.example.tabexperiment.OrdersFragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabexperiment.R;

import java.util.ArrayList;

public class CartViewAdapter extends RecyclerView.Adapter<CartViewAdapter.ViewHolder> {

    ArrayList<Order> arrayList;

    public CartViewAdapter(ArrayList<Order> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CartViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewAdapter.ViewHolder holder, int position) {
        Order order = arrayList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderType, orderDesc, orderFrom, cost;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderType = itemView.findViewById(R.id.textView_OrderType);
            orderDesc = itemView.findViewById(R.id.textView_OrderDesc);
            orderFrom = itemView.findViewById(R.id.textView_From);
            cost = itemView.findViewById(R.id.textView_orderCost);
        }

        void bind(Order order){
            orderType.setText(order.getType());
            orderDesc.setText(order.getDescription());
//            cost.setText(ord);
        }

    }
}

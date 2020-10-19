package com.example.tabexperiment.OrdersFragments;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.tabexperiment.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    ArrayList<Cart> cartArrayList = new ArrayList<>();
    public OrdersAdapter(){
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.carts,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Cart cart = cartArrayList.get(position);
        holder.bind(cart);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String obj = new Gson().toJson(cart);
                Intent intent = new Intent(holder.itemView.getContext(), CartViewActivity.class);
                intent.putExtra("Cart",obj);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cartArrayList == null){
            return 0;
        }
        return cartArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date,items,time, actiontext;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.textView_cartsDate);
            items = itemView.findViewById(R.id.textView_Cartsitems);
            time = itemView.findViewById(R.id.textView_CartOrderedtime);
            actiontext = itemView.findViewById(R.id.actiontext);
            cardView = itemView.findViewById(R.id.actioncard);
        }

        public void bind(final Cart cart){
            date.setText(cart.getDate());
            time.setText(getTime(cart.getTime()));
            String item = "";
            String Item = "";

            for (Order order: cart.getAllOrders()){
                item += order.getType() + ",";
            }
            item = item.replaceAll(",$","");
            Item = cart.getItems().equals("1") ? " Item " : " Items ";
            items.setText(cart.getItems()  +Item + "{" + item + "}");
            if (cart.getApproved().equals("false")){
                actiontext.setText("Accept Order");
            }else if(cart.getApproved().equals("Accepted")){
                actiontext.setText("Delivered");
            }else {
                actiontext.setText("Good Job");
            }

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cart.getApproved().equals("false")){
                        FirebaseFirestore.getInstance().collection("Orders").document(cart.getId()).update("approved","Accepted");
                        FirebaseFirestore.getInstance().collection("Orders").document(cart.getId()).update("courier","Karis");
                    }else if(cart.getApproved().equals("Accepted")){
                        FirebaseFirestore.getInstance().collection("Orders").document(cart.getId()).update("approved","Completed");
                    }
                }
            });
        }


        public String getTime(String timeStamp){
            Date date = new Date(Long.valueOf(timeStamp));
            DateFormat formatter = new SimpleDateFormat("hh:mm aa");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            String dateFormatted = formatter.format(date);
            return dateFormatted;
        }
    }

    public void setCartArrayList(ArrayList<Cart> cartArrayList) {
        this.cartArrayList = cartArrayList;
        notifyDataSetChanged();
    }

}

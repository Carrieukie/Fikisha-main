package com.example.tabexperiment.OrdersFragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabexperiment.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class Frag3 extends Fragment {

    RecyclerView recyclerView;
    View view;
    private OrdersAdapter loadingOrdersAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_fragment3, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recylerView_completedOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadingOrdersAdapter = new OrdersAdapter();
        recyclerView.setAdapter(loadingOrdersAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        final ArrayList<Cart> arrayList = new ArrayList<>();
        Query query = FirebaseFirestore.getInstance().collection("Orders");
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots ){
                    Log.d("OrdersrepoArralistData", documentSnapshot.toObject(Cart.class).toString());
                    if (documentSnapshot.toObject(Cart.class).approved.equals("Completed")){
                        Cart cart = documentSnapshot.toObject(Cart.class);
                        cart.setId(documentSnapshot.getId());
                        arrayList.add(cart);
                    }
                }
                loadingOrdersAdapter.setCartArrayList(arrayList);
            }
        });
    }
}

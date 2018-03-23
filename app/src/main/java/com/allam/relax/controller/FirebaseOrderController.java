package com.allam.relax.controller;

import com.allam.relax.controller.interfaces.OnCompleteListener;
import com.allam.relax.model.Order;
import com.allam.relax.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

/**
 * Relax
 * Created by Uranus on 23/03/2018 .
 */

public class FirebaseOrderController {
    private DatabaseReference mOrderReference;
    private String currentUid;

    public FirebaseOrderController() {
        currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mOrderReference = database.getReference("orders").child(currentUid);

    }

    public void getOrderFromFirebase(String oid, final OnCompleteListener<Order> completeListener){
        DatabaseReference orderRef = mOrderReference.child(oid);
        orderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order order = dataSnapshot.getValue(Order.class);
                completeListener.OnComplete(order, null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                completeListener.OnComplete(null, databaseError.getMessage());
            }
        });
    }

    public void uploadOrderToFirebase(final Order order, final OnCompleteListener<Order> completeListener){
        if(order.getOrderId() == null){
            completeListener.OnComplete(null, "Error saving order, try again later!");
            return;
        }

        DatabaseReference userRef = mOrderReference.child(order.getOrderId());
        userRef.setValue(order, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null){
                    completeListener.OnComplete(null, databaseError.getMessage());
                }else {
                    completeListener.OnComplete(order, null);
                }
            }
        });

    }

    public void uploadUserToFirebase(final Order order){
        if(order.getOrderId() == null){
            return;
        }
        DatabaseReference orderRef = mOrderReference.child(order.getOrderId());
        orderRef.setValue(order);
    }

}

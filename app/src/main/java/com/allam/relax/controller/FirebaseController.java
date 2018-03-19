package com.allam.relax.controller;

import com.allam.relax.interfaces.OnCompleteListener;
import com.allam.relax.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Uranus on 18/03/2018.
 */

public class FirebaseController {
    private DatabaseReference mUsersReference;

    public FirebaseController() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mUsersReference = database.getReference("users");
    }

    public void getUserFromFirebase(String uid, final OnCompleteListener completeListener){
        DatabaseReference userRef = mUsersReference.child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                completeListener.OnComplete(user, null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                completeListener.OnComplete(null, databaseError.getMessage());
            }
        });
    }

    public void uploadUserToFirebase(final User user, final OnCompleteListener completeListener){
        if(user.getUid() == null){
            completeListener.OnComplete(null, "Error saving user, try again later!");
            return;
        }
        DatabaseReference userRef = mUsersReference.child(user.getUid());
        userRef.setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null){
                    completeListener.OnComplete(null, databaseError.getMessage());
                }else {
                    completeListener.OnComplete(user, null);
                }
            }
        });

    }

    public void uploadUserToFirebase(final User user){
        if(user.getUid() == null){
            return;
        }
        DatabaseReference userRef = mUsersReference.child(user.getUid());
        userRef.setValue(user);
    }

}

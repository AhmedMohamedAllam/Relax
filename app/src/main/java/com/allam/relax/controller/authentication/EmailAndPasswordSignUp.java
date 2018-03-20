package com.allam.relax.controller.authentication;

import android.content.Context;

import com.allam.relax.controller.interfaces.OnCompleteLogin;
import com.allam.relax.model.User;

/**
 * Created by Uranus on 16/03/2018.
 */

public class EmailAndPasswordSignUp {
    private Context mContext;
    private FirebaseAuthentication mFirebaseLogin;
    public EmailAndPasswordSignUp(Context mContext) {
        this.mContext = mContext;
        this.mFirebaseLogin = new FirebaseAuthentication(mContext);
    }

    public void createAccountinFirebase(User user, OnCompleteLogin completeLogin) {
      mFirebaseLogin.createAccountWithEmailAndPassword(user,completeLogin);
    }


}

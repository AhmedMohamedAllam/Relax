package com.allam.relax.controller.authentication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.allam.relax.R;
import com.allam.relax.controller.interfaces.OnCompleteLogin;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

/**
 * Relax
 * Created by Uranus on 16/03/2018 .
 */

public class GoogleLogin {
    private Context mContext;
    private FirebaseAuthentication mFirebaseLogin;

    public GoogleLogin(Context mContext) {
        this.mContext = mContext;
        mFirebaseLogin = new FirebaseAuthentication(mContext);
    }

    public Intent getSignInIntentwithGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(mContext.getString(R.string.client_id))
                .requestEmail().build();

        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        return Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

    }

    public void SignInWithGoogle(Intent data, OnCompleteLogin completeLogin){
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = task.getResult(ApiException.class);
            mFirebaseLogin.firebaseAuthWithGoogle(account, completeLogin);
        } catch (ApiException e) {
            // Google Sign In failed, update UI appropriately
            String error = mContext.getString(R.string.google_signin_failed);
            completeLogin.onLoginError(error);
            Log.w("Relax", error, e);
        }
    }

    public void googleSignOut() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(mContext.getString(R.string.client_id))
                .requestEmail().build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(mContext, gso);
        mGoogleSignInClient.signOut();
    }
}

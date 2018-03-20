package com.allam.relax.controller.authentication;

import android.content.Context;

import com.allam.relax.R;
import com.allam.relax.controller.interfaces.OnCompleteLogin;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Relax
 * Created by Uranus on 16/03/2018 .
 */

public class FacebookLogin {

    private FirebaseAuthentication mFirebaseLogin;
    private Context mContext;



    public FacebookLogin(Context context) {
        mContext = context;
        mFirebaseLogin = new FirebaseAuthentication(mContext);
    }

    public void loginWithFacebook(LoginButton loginButton,CallbackManager mCallbackManager, final OnCompleteLogin completeLogin){
        // Initialize Facebook Login button
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken token = loginResult.getAccessToken();
                mFirebaseLogin.firebaseAuthWithFacebook(token, completeLogin);
            }

            @Override
            public void onCancel() {
                completeLogin.onLoginError(mContext.getString(R.string.facebook_login_canceld));
            }

            @Override
            public void onError(FacebookException error) {
                completeLogin.onLoginError(error.getMessage());
            }
        });
    }


}

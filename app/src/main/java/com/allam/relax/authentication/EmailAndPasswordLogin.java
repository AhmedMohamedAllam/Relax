package com.allam.relax.authentication;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.allam.relax.R;
import com.allam.relax.interfaces.OnCompleteLogin;
import com.allam.relax.model.User;

/**
 * Created by Uranus on 16/03/2018.
 */

public class EmailAndPasswordLogin {
    private FirebaseAuthentication mFirebaseLogin;
    private Context mContext;

    public EmailAndPasswordLogin(Context mContext) {
        mFirebaseLogin = new FirebaseAuthentication(mContext);
        this.mContext = mContext;
    }

    public void loginWithEmailAndPassword(final EditText mEmailEditText, final EditText mPasswordEditText, final OnCompleteLogin completeLogin) {
        if (!(Validation.isValidEmail(mContext, mEmailEditText) && Validation.isValidPassword(mContext, mPasswordEditText))) {
            completeLogin.onLoginError(mContext.getString(R.string.invalid_email_or_password));
            return;
        }
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        mFirebaseLogin.firebaseSignInWithEmailAndPassword(email, password, new OnCompleteLogin() {
            @Override
            public void onLoginSuccessfull(User user) {
                completeLogin.onLoginSuccessfull(user);
            }

            @Override
            public void onLoginError(String error) {
                completeLogin.onLoginError(error);
                //print error messeage to th email or password editText
                if (error.equals(mContext.getString(R.string.error_email_message))) {
                    Validation.setEditTextError(mEmailEditText, mContext.getString(R.string.print_error_email_message));
                } else if (error.equals(mContext.getString(R.string.error_password_message))) {
                    Validation.setEditTextError(mPasswordEditText, mContext.getString(R.string.print_error_password_message));
                } else {
                    Toast.makeText(mContext, error, Toast.LENGTH_LONG).show();
                    Validation.setEditTextError(mEmailEditText, "");
                    Validation.setEditTextError(mPasswordEditText, "");
                }
            }
        });

    }
}

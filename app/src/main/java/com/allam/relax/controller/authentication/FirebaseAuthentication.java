package com.allam.relax.controller.authentication;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.allam.relax.R;
import com.allam.relax.controller.FirebaseUserController;
import com.allam.relax.controller.interfaces.OnCompleteLogin;
import com.allam.relax.model.User;
import com.allam.relax.utiles.Utiles;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Relax
 * Created by Uranus on 16/03/2018 .
 */

class FirebaseAuthentication {
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private FirebaseUserController mFirebaseController;

    FirebaseAuthentication(Context mContext) {
        this.mContext = mContext;
        this.mAuth = FirebaseAuth.getInstance();
        mFirebaseController = new FirebaseUserController();

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle(mContext.getString(R.string.progress_dialog_loading));
        mProgressDialog.setMessage(mContext.getString(R.string.progress_dialog_signin_user_with_firebase));
        mProgressDialog.setCancelable(false);
    }

    void firebaseSignInWithEmailAndPassword(final String mUserEmail, String mPassword, final OnCompleteLogin completeLogin) {

        mAuth.signInWithEmailAndPassword(mUserEmail, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final String mUid;
                    if (mAuth.getCurrentUser() != null){
                        mUid = mAuth.getCurrentUser().getUid();
                    }else{
                        return;
                    }
                    mFirebaseController.getUserFromFirebase(mUid, new com.allam.relax.controller.interfaces.OnCompleteListener<User>() {
                        @Override
                        public void OnComplete(User user, String error) {
                                completeLogin.onLoginSuccessfull(user);
                        }
                    });
                } else {
                    if (task.getException() != null) {
                        completeLogin.onLoginError(task.getException().getMessage());
                    }
                }
            }
        });
    }

    void firebaseAuthWithGoogle(final GoogleSignInAccount googleAccount, final OnCompleteLogin completeLogin) {
        AuthCredential credential = GoogleAuthProvider.getCredential(googleAccount.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (mAuth.getCurrentUser() != null){
                        mFirebaseUser = mAuth.getCurrentUser();
                    }else{
                        return;
                    }
                    mFirebaseController.getUserFromFirebase(mFirebaseUser.getUid(), new com.allam.relax.controller.interfaces.OnCompleteListener<User>() {
                        @Override
                        public void OnComplete(User user, String error) {
                            if (user!= null){
                                completeLogin.onLoginSuccessfull(user);
                            }else{
                                User newUser = User.getUser().init(mFirebaseUser);
                                mFirebaseController.uploadUserToFirebase(newUser);
                                completeLogin.onLoginSuccessfull(newUser);
                            }
                        }
                    });
                } else {
                    if (task.getException() != null) {
                        String error = task.getException().getMessage();
                        Log.i("AAA",error);
                        completeLogin.onLoginError(error);
                    }
                }
            }
        });

    }

    void createAccountWithEmailAndPassword(final User user, final OnCompleteLogin completeLogin){

        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    completeLogin.onLoginSuccessfull(user); //inform that log in operation is successfull
                    user.setUid( mAuth.getCurrentUser().getUid());
                    mFirebaseController.uploadUserToFirebase(user, new com.allam.relax.controller.interfaces.OnCompleteListener<User>() {
                        @Override
                        public void OnComplete(User user, String error) {
                            if (error != null){
                                Utiles.showToast(mContext, mContext.getString(R.string.error_saving_user));
                            }
                        }
                    });

                } else {
                    if(task.getException() != null){
                        completeLogin.onLoginError(task.getException().getMessage());
                    }
                }
            }
        });

    }

    void firebaseAuthWithFacebook(AccessToken token, final OnCompleteLogin completeLogin) {
        mProgressDialog.show();
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressDialog.dismiss();
                        if (task.isSuccessful()) {
                            if (mAuth.getCurrentUser() != null){
                                mFirebaseUser = mAuth.getCurrentUser();
                            }else{
                                return;
                            }

                            mFirebaseController.getUserFromFirebase(mFirebaseUser.getUid(), new com.allam.relax.controller.interfaces.OnCompleteListener<User>() {
                                @Override
                                public void OnComplete(User user, String error) {
                                    if (user!= null){
                                        completeLogin.onLoginSuccessfull(user);
                                    }else{
                                        User newUser = User.getUser().init(mFirebaseUser);
                                        mFirebaseController.uploadUserToFirebase(newUser);
                                        completeLogin.onLoginSuccessfull(newUser);
                                    }
                                }
                            });

                        } else {
                            LoginManager.getInstance().logOut();
                            String error = "";
                            if(task.getException() != null){
                                error = task.getException().getMessage();
                            }
                          completeLogin.onLoginError(mContext.getString(R.string.facebook_login_failed )+ "\n" + error);
                        }
                    }
                });
    }

      /*
    private void sendVerificationEmail() {
        Toast.makeText(this, getString(R.string.sending_email_verification), Toast.LENGTH_SHORT).show();
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (task.isSuccessful()) {
                        mAuthProgressDialog.dismiss();
                        Toast.makeText(getBaseContext(),getString(R.string.verification_email_sent) + user.getEmail(),
                                Toast.LENGTH_SHORT).show();
                        // start loginActivity after sending verification email
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        Log.e(LOG_TAG, getString(R.string.verification_email_failed), task.getException());
                        Toast.makeText(getBaseContext(),
                                getString(R.string.verification_email_failed),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
*/
}

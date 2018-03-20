package com.allam.relax.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allam.relax.R;
import com.allam.relax.controller.authentication.FacebookLogin;
import com.allam.relax.controller.interfaces.OnCompleteLogin;
import com.allam.relax.controller.authentication.EmailAndPasswordLogin;
import com.allam.relax.controller.authentication.GoogleLogin;
import com.allam.relax.model.User;
import com.allam.relax.utiles.Utiles;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    private static final String LOG_TAG = SignUpActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseUser mFirebaseUser;
    private EditText mEmailEditText, mPasswordEditText;
    private Button mLoginButtonWithPassword;
    private com.google.android.gms.common.SignInButton mGoogleSignInButton;
    private TextView mSighnUpTextView;
    private ProgressDialog mProgressDialog;
    private static final int GOOGLE_SIGN_IN_INTENT = 101;
    private ImageView mLogoImageView;
    private User mCurrentUser;
    private LoginButton mFacebookLoginButton;
    private GoogleLogin mGoogleLogin;
    private EmailAndPasswordLogin mEmailAndPasswordLogin;
    private FacebookLogin mFacebookLogin;
    private CallbackManager mCallbackManager;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProgressDialog.dismiss();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeScreen();

        // Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //Facebook CallbackManager
        mCallbackManager = CallbackManager.Factory.create();

        // Login calsses
        mGoogleLogin = new GoogleLogin(LoginActivity.this);
        mEmailAndPasswordLogin = new EmailAndPasswordLogin(LoginActivity.this);
        mFacebookLogin = new FacebookLogin(LoginActivity.this);

        // Logo and its animation
        mLogoImageView = findViewById(R.id.login_logo_image);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        mLogoImageView.startAnimation(animation);

        //firebase listner if user signed in redirect to HomeActivity
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mFirebaseUser = firebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); // don't return to previous activity

                    startActivity(intent);
                    Utiles.showToast(LoginActivity.this, getString(R.string.logged_in_successfully));
                }
            }
        };

        mLoginButtonWithPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.show();
                mEmailAndPasswordLogin.loginWithEmailAndPassword(mEmailEditText, mPasswordEditText,
                        new OnCompleteLogin() {
                            @Override
                            public void onLoginSuccessfull(User user) {
                                Log.i(LOG_TAG, user.getUid() + " \n" +
                                        user.getDisplayName() + " \n" +
                                        user.getEmail() + " \n" +
                                        user.getAddress() + " \n" +
                                        user.getPhone());
                                mProgressDialog.dismiss();
                            }

                            @Override
                            public void onLoginError(String error) {
                                mProgressDialog.dismiss();
                            }
                        });
            }
        });

        mGoogleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.show();
                mGoogleLogin.googleSignOut();
                Intent signInIntent = mGoogleLogin.getSignInIntentwithGoogle();
                startActivityForResult(signInIntent, GOOGLE_SIGN_IN_INTENT);
            }
        });

        mSighnUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        // initiate Facebook Login button
        initiateFacebookLoginButton();

    }


    private void initiateFacebookLoginButton() {
        mFacebookLogin.loginWithFacebook(mFacebookLoginButton, mCallbackManager, new OnCompleteLogin() {
            @Override
            public void onLoginSuccessfull(User user) {
                Log.i(LOG_TAG, user.getUid() + " \n" +
                        user.getDisplayName() + " \n" +
                        user.getEmail() + " \n" +
                        user.getAddress() + " \n" +
                        user.getPhone());
                Utiles.showToast(LoginActivity.this, getString(R.string.facebook_login_successfull));
            }

            @Override
            public void onLoginError(String error) {
                Utiles.showToast(LoginActivity.this, getString(R.string.facebook_login_failed));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN_INTENT) {
            mGoogleLogin.SignInWithGoogle(data, new OnCompleteLogin() {
                @Override
                public void onLoginSuccessfull(User user) {
                    mProgressDialog.dismiss();
                    Log.i(LOG_TAG, user.getUid() + " \n" +
                            user.getDisplayName() + " \n" +
                            user.getEmail() + " \n" +
                            user.getAddress() + " \n" +
                            user.getPhone());
                    Toast.makeText(LoginActivity.this, getString(R.string.google_signin_successfull), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLoginError(String error) {
                    mProgressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        }

        mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private void initializeScreen() {
        mSighnUpTextView = findViewById(R.id.tv_sign_up);
        mEmailEditText = findViewById(R.id.edit_text_email);
        mPasswordEditText = findViewById(R.id.edit_text_password);
        mLoginButtonWithPassword = findViewById(R.id.login_button_with_password);
        mGoogleSignInButton = findViewById(R.id.login_with_google);
        mFacebookLoginButton = findViewById(R.id.login_with_facebook);


        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(getResources().getString(R.string.progress_dialog_loading));
        mProgressDialog.setMessage(getResources().getString(R.string.progress_dialog_signin_user_with_firebase));
        mProgressDialog.setCancelable(false);
    }


}

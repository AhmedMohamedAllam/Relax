package com.allam.relax.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allam.relax.R;
import com.allam.relax.controller.authentication.EmailAndPasswordSignUp;
import com.allam.relax.controller.Validation;
import com.allam.relax.controller.interfaces.OnCompleteLogin;
import com.allam.relax.utiles.Utiles;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import com.allam.relax.model.User;

import static com.allam.relax.model.User.getUser;

public class SignUpActivity extends AppCompatActivity {

    private static final String LOG_TAG = SignUpActivity.class.getSimpleName();
    private EditText mEditTextUsernameCreate, mEditTextEmailCreate, mEditTextPasswordCreate,
            mEditTextAddressCreate, mEditTextPhoneCreate;
    private Button mCreateAccountButton;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private TextView mSighnInTextView;
    private String mUserName, mUserEmail, mPassword, mAddress, mPhone;
    private ProgressDialog mAuthProgressDialog;
    private ImageView mLogoImageView;
    private EmailAndPasswordSignUp mEmailAndPasswordSignUp;


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
        mAuthProgressDialog.dismiss();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initializeScreen();
        mEmailAndPasswordSignUp = new EmailAndPasswordSignUp(SignUpActivity.this);

        mLogoImageView = findViewById(R.id.create_account_logo_image);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        mLogoImageView.startAnimation(animation);

        //Setup Firebase
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mFirebaseUser = firebaseAuth.getCurrentUser();

                if (mFirebaseUser != null) {
                    Utiles.showToast(SignUpActivity.this, getString(R.string.account_created_successfully));
                    //update User display name
                    UserProfileChangeRequest UPCR = new UserProfileChangeRequest.Builder().setDisplayName(mUserName).build();
                    mFirebaseUser.updateProfile(UPCR);
                    // start loginActivity after sending verification email
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Utiles.showToast(SignUpActivity.this, getString(R.string.error_occurred));
                }
            }
        };


        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccountinFirebase();
            }
        });


        mSighnInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


    }


    private void createAccountinFirebase() {
        User newUser = isUserDataValid();
        if(newUser == null){
            return;
        }
        mAuthProgressDialog.show();
        mEmailAndPasswordSignUp.createAccountinFirebase(newUser, new OnCompleteLogin() {
            @Override
            public void onLoginSuccessfull(User user) {
                mAuthProgressDialog.dismiss();
                mAuth.addAuthStateListener(mAuthStateListener);
            }

            @Override
            public void onLoginError(String error) {
                mAuthProgressDialog.dismiss();
                Toast.makeText(SignUpActivity.this, error, Toast.LENGTH_LONG).show();
            }
        });
    }


    private void initializeScreen() {
        mSighnInTextView = findViewById(R.id.tv_sign_in);
        mCreateAccountButton = findViewById(R.id.btn_create_account_final);
        mEditTextUsernameCreate = findViewById(R.id.edit_text_username_create);
        mEditTextEmailCreate = findViewById(R.id.edit_text_email_create);
        mEditTextPasswordCreate = findViewById(R.id.edit_text_password_create);
        mEditTextAddressCreate = findViewById(R.id.edit_text_address_create);
        mEditTextPhoneCreate = findViewById(R.id.edit_phone_create);


        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle(getResources().getString(R.string.progress_dialog_loading));
        mAuthProgressDialog.setMessage(getResources().getString(R.string.progress_dialog_creating_user_with_firebase));
        mAuthProgressDialog.setCancelable(false);
    }

    private User isUserDataValid(){
        if (Validation.isValidUserName(getBaseContext(), mEditTextUsernameCreate) &&
                Validation.isValidEmail(getBaseContext(), mEditTextEmailCreate) &&
                Validation.isValidPassword(getBaseContext(), mEditTextPasswordCreate) &&
                !Validation.isEditTextNull(getBaseContext(), mEditTextAddressCreate) &&
                Validation.isValidMobile(getBaseContext(), mEditTextPhoneCreate)) {
            mUserName = mEditTextUsernameCreate.getText().toString();
            mUserEmail = mEditTextEmailCreate.getText().toString();
            mPassword = mEditTextPasswordCreate.getText().toString();
            mAddress = mEditTextAddressCreate.getText().toString();
            mPhone = mEditTextPhoneCreate.getText().toString();
            return getUser().init(mUserName,mUserEmail, mPassword, mAddress, mPhone);
        } else {
            return null;
        }
    }



}

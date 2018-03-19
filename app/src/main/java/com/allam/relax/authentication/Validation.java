package com.allam.relax.authentication;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import com.allam.relax.R;

/**
 * Created by Uranus on 16/03/2018.
 */

public class Validation {

    public static boolean isEditTextNull(Context context, EditText editText){
        if(TextUtils.isEmpty(editText.getText())){
            setEditTextError(editText, context.getString(R.string.required_field));
            return true;
        }
        return false;
    }

    public static void setEditTextError(EditText editText, String errorMessage){
        editText.setError(errorMessage);
        editText.requestFocus();
    }

    public static boolean isValidEmail(Context context, EditText email) {
        if (!isEditTextNull(context, email)) {
            if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                return true;
            }else {
                setEditTextError(email, context.getString(R.string.invalid_email_address));
            }
        }

        return false;
    }

    public static  boolean isValidUserName(Context context, EditText userName) {
        boolean isNull = isEditTextNull(context, userName);
        boolean isLengthValid = userName.getText().toString().length() >= 3;
        if(!isLengthValid){
            setEditTextError(userName, context.getString(R.string.username_length_error));
        }

        return isLengthValid && !isNull;
    }

    public static  boolean isValidPassword(Context context, EditText password) {
        boolean isNull = isEditTextNull(context, password);
        boolean isLengthValid = password.getText().toString().length() >= 6;
        if(!isLengthValid){
            setEditTextError(password, context.getString(R.string.password_length_error));
        }

        return isLengthValid && !isNull;
    }

    public static boolean isValidMobile(Context context, EditText phone) {

        boolean isValid =  android.util.Patterns.PHONE.matcher(phone.getText()).matches();
        if(!isValid){
            setEditTextError(phone, context.getString(R.string.invalid_phone_number));
        }

        return isValid;
    }

}

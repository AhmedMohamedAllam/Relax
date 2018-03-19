package com.allam.relax.utiles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.allam.relax.R;
import com.allam.relax.activity.LoginActivity;
import com.allam.relax.activity.SignUpActivity;


/**
 * Created by Allam on 2/16/2017.
 */

public class Utiles {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}

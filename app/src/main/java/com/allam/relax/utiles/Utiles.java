package com.allam.relax.utiles;

import android.content.Context;
import android.widget.Toast;


public class Utiles {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}

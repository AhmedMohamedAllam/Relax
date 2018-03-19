package com.allam.relax.interfaces;

import com.allam.relax.model.User;

/**
 * Created by Uranus on 16/03/2018.
 */

public interface OnCompleteLogin {
    void onLoginSuccessfull(User user);
    void onLoginError(String error);
}

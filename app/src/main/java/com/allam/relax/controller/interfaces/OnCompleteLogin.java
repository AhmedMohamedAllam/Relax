package com.allam.relax.controller.interfaces;

import com.allam.relax.model.User;


public interface OnCompleteLogin {
    void onLoginSuccessfull(User user);
    void onLoginError(String error);
}

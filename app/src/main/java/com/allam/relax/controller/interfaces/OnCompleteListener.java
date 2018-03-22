package com.allam.relax.controller.interfaces;

import com.allam.relax.model.User;

import java.io.Serializable;

public interface OnCompleteListener<T> extends Serializable{
    void OnComplete(T obj, String error);
}

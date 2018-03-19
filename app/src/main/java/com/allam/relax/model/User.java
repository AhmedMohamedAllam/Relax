package com.allam.relax.model;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.Date;

/**
 * Created by Allam on 2/14/2017.
 */

public class User {

    private String mUid;
    private String mDisplayUid;
    private String mDisplayName;
    private String mEmail;
    private String mPassword;
    private String mAddress;
    private String mPhone;
    private Date mSubscriptionDate;




    public User(String displayName, String email, String address, String phone) {
        mDisplayName = displayName;
        mEmail = email;
        mAddress = address;
        mPhone = phone;
        mSubscriptionDate = new Date();
    }

    public User(String displayName, String email, String password, String address, String phone) {
        mDisplayName = displayName;
        mEmail = email;
        mPassword = password;
        mAddress = address;
        mPhone = phone;
        mSubscriptionDate = new Date();
    }

    public User(GoogleSignInAccount googleAccount, String uid){
        mUid = uid;
        mDisplayName = googleAccount.getDisplayName();
        mEmail = googleAccount.getEmail();
        mSubscriptionDate = new Date();
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public String getDisplayUid() {
        return mDisplayUid;
    }

    public void setDisplayUid(String displayUid) {
        mDisplayUid = displayUid;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public Date getSubscriptionDate() {
        return mSubscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        mSubscriptionDate = subscriptionDate;
    }




}

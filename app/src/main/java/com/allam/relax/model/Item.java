package com.allam.relax.model;

/**
 * Relax
 * Created by Uranus on 21/03/2018 .
 */

public class Item {
    private String mItemId;
    private String mName;
    private String mCount;
    private String mPrice;
    private String mLocation;
    private String mNote;

    public Item(String itemId, String name, String count, String price, String location, String note) {
        mItemId = itemId;
        mName = name;
        mCount = count;
        mPrice = price;
        mLocation = location;
        mNote = note;
    }

    public Item() {

    }

    public String getItemId() {
        return mItemId;
    }

    public void setItemId(String itemId) {
        mItemId = itemId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getCount() {
        return mCount;
    }

    public void setCount(String count) {
        mCount = count;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }
}

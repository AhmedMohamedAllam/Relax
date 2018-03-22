package com.allam.relax.model;

import java.io.Serializable;

/**
 * Relax
 * Created by Uranus on 21/03/2018 .
 */

public class Item implements Serializable {
    private String mItemId;
    private String mName;
    private String mQuantity;
    private String mPrice;
    private String mMarket;
    private String mNote;

    public Item( String name, String count, String price, String location, String note) {
        mName = name;
        mQuantity = count;
        mPrice = price;
        mMarket = location;
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

    public String getQuantity() {
        return mQuantity;
    }

    public void setQuantity(String quantity) {
        mQuantity = quantity;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getMarket() {
        return mMarket;
    }

    public void setMarket(String market) {
        mMarket = market;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }
}

package com.allam.relax.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Relax
 * Created by Uranus on 23/03/2018 .
 */

public class Order {
    private String mOrderId;
    private ArrayList<Item> mOrder;
    private Date mOrderDate;

    public Order() {

    }

    public Order(ArrayList<Item> order) {
        mOrder = order;
        mOrderId = UUID.randomUUID().toString();
        mOrderDate = new Date();
    }

    public ArrayList<Item> getOrder() {
        return mOrder;
    }

    public void setOrder(ArrayList<Item> order) {
        mOrder = order;
    }

    public String getOrderId() {
        return mOrderId;
    }

    public void setOrderId(String orderId) {
        mOrderId = orderId;
    }

    public Date getOrderDate() {
        return mOrderDate;
    }

    public void setOrderDate(Date orderDate) {
        mOrderDate = orderDate;
    }
}

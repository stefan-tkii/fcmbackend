package com.firebasemessaging.models;

import java.io.Serializable;

public class OrderProductContent implements Serializable
{
   
    private static final long serialVersionUID = 1L;
    private String productId;
    private double price;
    private int quantity;

    public OrderProductContent()
    {

    }

    public OrderProductContent(String productId, double price, int quantity) 
    {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductId() 
    {
        return this.productId;
    }

    public void setProductId(String productId) 
    {
        this.productId = productId;
    }

    public double getPrice() 
    {
        return this.price;
    }

    public void setPrice(double price) 
    {
        this.price = price;
    }

    public int getQuantity() 
    {
        return this.quantity;
    }

    public void setQuantity(int quantity) 
    {
        this.quantity = quantity;
    }

}
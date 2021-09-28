package com.firebasemessaging.models;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order implements Serializable
{

    private static final long serialVersionUID = 1L;
    private String orderId;
    private String address;
    private String paymentId;
    private String paymentMethodId;
    private List<OrderProductContent> products;
    private String status;
    private long timestamp;

    public Order()
    {

    }

    public Order(String orderId, String address, String paymentId, String paymentMethodId, List<OrderProductContent> products, 
    String status, long timestamp) 
    {
        this.orderId = orderId;
        this.address = address;
        this.paymentId = paymentId;
        this.paymentMethodId = paymentMethodId;
        this.products = products;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getOrderId() 
    {
        return this.orderId;
    }

    public void setOrderId(String orderId) 
    {
        this.orderId = orderId;
    }

    public String getAddress() 
    {
        return this.address;
    }

    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getPaymentId() 
    {
        return this.paymentId;
    }

    public void setPaymentId(String paymentId) 
    {
        this.paymentId = paymentId;
    }

    public String getPaymentMethodId() 
    {
        return this.paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) 
    {
        this.paymentMethodId = paymentMethodId;
    }

    public List<OrderProductContent> getProducts() 
    {
        return this.products;
    }

    public void setProducts(List<OrderProductContent> products) 
    {
        this.products = products;
    }

    public String getStatus() 
    {
        return this.status;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public long getTimestamp() 
    {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) 
    {
        this.timestamp = timestamp;
    }
 
}
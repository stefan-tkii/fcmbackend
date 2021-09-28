package com.firebasemessaging.models;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Product implements Serializable 
{

    private static final long serialVersionUID = 1L;
    private String productId;
    private String name;
    private String shortDesc;
    private String longDesc;
    private String imgUri;
    private Double price;
    private int orders;
    private String categoryId;
    private int inStock;

    public Product()
    {

    }

    public Product(String productId, String name, String shortDesc, String longDesc, String imgUri, Double price, int orders, String categoryId, int inStock)
    {
        this.productId = productId;
        this.name = name;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.imgUri = imgUri;
        this.price = price;
        this.orders = orders;
        this.categoryId = categoryId;
        this.inStock = inStock;
    }

    public String getProductId()
    {
        return productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getShortDesc()
    {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc)
    {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc()
    {
        return longDesc;
    }

    public void setLongDesc(String longDesc)
    {
        this.longDesc = longDesc;
    }

    public String getImgUri()
    {
        return imgUri;
    }

    public void setImgUri(String imgUri)
    {
        this.imgUri = imgUri;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public int getOrders()
    {
        return orders;
    }

    public void setOrders(int orders)
    {
        this.orders = orders;
    }

    public String getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(String categoryId)
    {
        this.categoryId = categoryId;
    }

    public int getInStock()
    {
        return inStock;
    }

    public void setInStock(int inStock)
    {
        this.inStock = inStock;
    }
    
}
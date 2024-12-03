package com.example.mall.model;

// 商品类，表示商城中的商品
public class Product {
    // 商品名称
    private String name;
    // 商品描述
    private String description;
    // 商品价格
    private double price;
    // 商品图片资源ID
    private int imageResId;

    // 构造方法，初始化商品
    public Product(String name, String description, double price, int imageResId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResId = imageResId;
    }

    // 获取商品名称
    public String getName() {
        return name;
    }

    // 获取商品描述
    public String getDescription() {
        return description;
    }

    // 获取商品价格
    public double getPrice() {
        return price;
    }

    // 获取商品图片资源ID
    public int getImageResId() {
        return imageResId;
    }
}
package com.example.mall.model;

import android.os.Parcel;
import android.os.Parcelable;

// 购物车项类，表示购物车中的单个商品项
public class CartItem implements Parcelable {

    // 商品名称
    private String name;
    // 商品价格
    private double price;
    // 商品图片资源ID
    private int imageResId;
    // 商品数量
    private int quantity;
    // 商品是否被选中
    private boolean isSelected;

    // 构造方法，初始化购物车项
    public CartItem(String name, double price, int imageResId, int quantity) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.quantity = quantity;
        this.isSelected = false; // 默认未选中
    }

    // 获取商品名称
    public String getName() {
        return name;
    }

    // 获取商品图片资源ID
    public int getImageResId() {
        return imageResId;
    }

    // 获取商品价格
    public double getPrice() {
        return price;
    }

    // 获取商品数量
    public int getQuantity() {
        return quantity;
    }

    // 判断商品是否被选中
    public boolean isSelected() {
        return isSelected;
    }

    // 设置商品是否被选中
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    // 增加商品数量
    public void increaseQuantity() {
        this.quantity++;
    }

    // 减少商品数量，如果数量大于0则减少
    public void decreaseQuantity() {
        if (this.quantity > 0) {
            this.quantity--;
        }
    }

    // Parcelable相关实现

    protected CartItem(Parcel in) {
        name = in.readString();
        price = in.readDouble();
        imageResId = in.readInt();
        quantity = in.readInt();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeInt(imageResId);
        dest.writeInt(quantity);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

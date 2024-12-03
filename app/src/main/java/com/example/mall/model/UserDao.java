package com.example.mall.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private DatabaseHelper dbHelper;

    public UserDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // 插入用户信息
    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USERNAME, username);
        values.put(DatabaseHelper.COLUMN_PASSWORD, password);
        long result = db.insert(DatabaseHelper.TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    // 获取用户ID
    public int getUserId(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, new String[]{DatabaseHelper.COLUMN_ID},
                DatabaseHelper.COLUMN_USERNAME + "=?", new String[]{username},
                null, null, null);
        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            cursor.close();
            db.close();
            return userId;
        } else {
            cursor.close();
            db.close();
            return -1;
        }
    }

    // 检查用户是否存在
    public boolean checkUserExists(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, null,
                DatabaseHelper.COLUMN_USERNAME + "=?", new String[]{username},
                null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // 验证用户登录信息
    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, null,
                DatabaseHelper.COLUMN_USERNAME + "=? AND " + DatabaseHelper.COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);
        boolean valid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return valid;
    }

    // 插入购物车条目
    public void insertCartItem(int userId, CartItem cartItem) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_ID, userId);
        values.put(DatabaseHelper.COLUMN_PRODUCT_NAME, cartItem.getName());
        values.put(DatabaseHelper.COLUMN_PRODUCT_PRICE, cartItem.getPrice());
        values.put(DatabaseHelper.COLUMN_PRODUCT_IMAGE_RES_ID, cartItem.getImageResId());
        values.put(DatabaseHelper.COLUMN_QUANTITY, cartItem.getQuantity());
        db.insert(DatabaseHelper.TABLE_CART_ITEMS, null, values);
        db.close();
    }

    // 获取购物车条目
    public List<CartItem> getCartItems(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<CartItem> cartItems = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_CART_ITEMS, null,
                DatabaseHelper.COLUMN_USER_ID + "=?", new String[]{String.valueOf(userId)},
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String productName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_NAME));
                double productPrice = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_PRICE));
                int productImageResId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_IMAGE_RES_ID));
                int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUANTITY));
                cartItems.add(new CartItem(productName, productPrice, productImageResId, quantity));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cartItems;
    }

    // 删除购物车条目
    public void deleteCartItem(int userId, CartItem cartItem) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_CART_ITEMS,
                DatabaseHelper.COLUMN_USER_ID + "=? AND " +
                        DatabaseHelper.COLUMN_PRODUCT_NAME + "=? AND " +
                        DatabaseHelper.COLUMN_PRODUCT_PRICE + "=? AND " +
                        DatabaseHelper.COLUMN_PRODUCT_IMAGE_RES_ID + "=? AND " +
                        DatabaseHelper.COLUMN_QUANTITY + "=?",
                new String[]{
                        String.valueOf(userId),
                        cartItem.getName(),
                        String.valueOf(cartItem.getPrice()),
                        String.valueOf(cartItem.getImageResId()),
                        String.valueOf(cartItem.getQuantity())
                });
        db.close();
    }

    // 获取用户收货地址
    public String getUserAddress(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, new String[]{DatabaseHelper.COLUMN_ADDRESS},
                DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)},
                null, null, null);
        if (cursor.moveToFirst()) {
            String address = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS));
            cursor.close();
            db.close();
            return address;
        } else {
            cursor.close();
            db.close();
            return "";
        }
    }

    // 更新用户收货地址
    public void updateUserAddress(int userId, String newAddress) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ADDRESS, newAddress);
        db.update(DatabaseHelper.TABLE_USERS, values, DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
        db.close();
    }

    // 插入订单条目
    public void insertOrderItem(int userId, CartItem cartItem) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_ID, userId);
        values.put(DatabaseHelper.COLUMN_PRODUCT_NAME, cartItem.getName());
        values.put(DatabaseHelper.COLUMN_PRODUCT_PRICE, cartItem.getPrice());
        values.put(DatabaseHelper.COLUMN_PRODUCT_IMAGE_RES_ID, cartItem.getImageResId());
        values.put(DatabaseHelper.COLUMN_QUANTITY, cartItem.getQuantity());
        db.insert(DatabaseHelper.TABLE_ORDER_HISTORY, null, values);
        db.close();
    }

    // 获取订单历史条目
    public List<CartItem> getOrderHistoryItems(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<CartItem> orderItems = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_ORDER_HISTORY, null,
                DatabaseHelper.COLUMN_USER_ID + "=?", new String[]{String.valueOf(userId)},
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String productName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_NAME));
                double productPrice = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_PRICE));
                int productImageResId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_IMAGE_RES_ID));
                int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUANTITY));
                orderItems.add(new CartItem(productName, productPrice, productImageResId, quantity));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return orderItems;
    }
}

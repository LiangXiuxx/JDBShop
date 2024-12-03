package com.example.mall.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mall.model.CartItem;
import com.example.mall.model.UserDao;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车ViewModel，用于管理购物车商品条目。
 * 继承自AndroidViewModel，以在配置更改时保持数据。
 */
public class CartViewModel extends AndroidViewModel {
    private MutableLiveData<List<CartItem>> cartItems; // LiveData，保存购物车商品列表
    private UserDao userDao; // 数据访问对象，用于与数据库交互
    private int userId; // 当前购物车关联的用户ID

    /**
     * CartViewModel的构造方法。
     * 初始化UserDao，并设置初始空的MutableLiveData用于cartItems。
     *
     * @param application 应用程序实例，传递给UserDao。
     */
    public CartViewModel(Application application) {
        super(application);
        userDao = new UserDao(application); // 使用应用程序上下文初始化UserDao
        cartItems = new MutableLiveData<>(new ArrayList<>()); // 使用空的ArrayList初始化cartItems
    }

    /**
     * 设置当前会话的用户ID，并从数据库加载购物车商品。
     *
     * @param userId 当前用户的ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
        loadCartItems(); // 加载购物车商品条目
    }

    /**
     * 获取购物车商品列表的LiveData。
     *
     * @return 购物车商品列表的LiveData
     */
    public LiveData<List<CartItem>> getCartItems() {
        return cartItems;
    }

    /**
     * 将商品添加到购物车。
     * 更新LiveData中的购物车列表，并将新条目插入到数据库。
     *
     * @param cartItem 要添加的购物车商品条目
     */
    public void addToCart(CartItem cartItem) {
        List<CartItem> currentItems = cartItems.getValue();
        currentItems.add(cartItem); // 添加到内存中的购物车列表
        cartItems.setValue(currentItems); // 更新LiveData中的购物车列表
        userDao.insertCartItem(userId, cartItem); // 将新条目插入数据库
    }

    /**
     * 从数据库加载购物车商品条目。
     * 更新LiveData中的购物车列表。
     */
    private void loadCartItems() {
        List<CartItem> loadedItems = userDao.getCartItems(userId); // 从数据库加载购物车商品
        cartItems.setValue(loadedItems); // 更新LiveData中的购物车列表
    }

    /**
     * 从购物车中移除指定的商品条目。
     * 更新LiveData中的购物车列表，并从数据库中删除对应的条目。
     *
     * @param items 要从购物车中移除的商品条目列表
     */
    public void removeItems(List<CartItem> items) {
        List<CartItem> currentItems = cartItems.getValue();
        if (currentItems != null) {
            currentItems.removeAll(items); // 从内存中的购物车列表中移除指定商品条目
            cartItems.setValue(currentItems); // 更新LiveData中的购物车列表
            for (CartItem item : items) {
                userDao.deleteCartItem(userId, item); // 从数据库中删除对应的购物车条目
            }
        }
    }

}

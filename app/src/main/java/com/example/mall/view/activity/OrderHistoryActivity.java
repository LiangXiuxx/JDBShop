package com.example.mall.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mall.R;
import com.example.mall.model.CartItem;
import com.example.mall.model.UserDao;
import com.example.mall.view.adapter.OrderAdapter;

import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOrderHistory;
    private List<CartItem> orderHistoryItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        // 初始化视图
        recyclerViewOrderHistory = findViewById(R.id.recyclerView_order_history);

        // 获取当前用户的历史订单数据
        UserDao userDao = new UserDao(this);
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "未知用户");
        int userId = userDao.getUserId(username);
        orderHistoryItems = userDao.getOrderHistoryItems(userId);

        // 设置RecyclerView的布局管理器和适配器
        recyclerViewOrderHistory.setLayoutManager(new LinearLayoutManager(this));
        OrderAdapter adapter = new OrderAdapter(this, orderHistoryItems);
        recyclerViewOrderHistory.setAdapter(adapter);
    }
}
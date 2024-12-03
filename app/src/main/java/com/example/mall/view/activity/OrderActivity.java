package com.example.mall.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mall.R;
import com.example.mall.model.CartItem;
import com.example.mall.model.UserDao;
import com.example.mall.view.adapter.OrderAdapter;
import com.example.mall.view.fragment.HomeFragment;

import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOrder;
    private TextView tvTotalPrice;
    private Button btnPay;
    private List<CartItem> orderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // 初始化视图
        recyclerViewOrder = findViewById(R.id.recyclerView_order);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnPay = findViewById(R.id.btnPay);

        // 获取传递过来的订单商品信息
        orderItems = getIntent().getParcelableArrayListExtra("ORDER_ITEMS");

        // 设置RecyclerView的布局管理器和适配器
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(this));
        OrderAdapter adapter = new OrderAdapter(this, orderItems);
        recyclerViewOrder.setAdapter(adapter);

        // 计算总价
        double totalPrice = calculateTotalPrice(orderItems);
        tvTotalPrice.setText(String.format("总价：%.2f 元", totalPrice));

        // 设置支付按钮点击事件
        btnPay.setOnClickListener(v -> {
            // 处理支付逻辑（示例中仅显示支付成功的 Toast 提示）
            Toast.makeText(OrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();

            // 将订单条目插入订单历史中
            UserDao userDao = new UserDao(OrderActivity.this);
            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            String username = sharedPreferences.getString("username", "未知用户");
            int userId = userDao.getUserId(username);

            for (CartItem item : orderItems) {
                userDao.insertOrderItem(userId, item);
            }

            // 清空购物车条目
            //userDao.deleteCartItems(userId);

            // 返回到首页界面
            Intent intent = new Intent(OrderActivity.this, HomeFragment.class);
            startActivity(intent);
            finish(); // 结束当前订单界面
        });
    }

    // 计算总价的方法
    private double calculateTotalPrice(List<CartItem> items) {
        double totalPrice = 0.0;
        for (CartItem item : items) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        return totalPrice;
    }
}
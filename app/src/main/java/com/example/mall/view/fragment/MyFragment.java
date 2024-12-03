package com.example.mall.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mall.R;
import com.example.mall.view.activity.OrderHistoryActivity;
import com.example.mall.model.UserDao;
//我的界面Fragment
public class MyFragment extends Fragment {

    private TextView tvUsername;
    private EditText etAddress;
    private Button btnSaveAddress;
    private Button btnViewOrderHistory;

    private SharedPreferences sharedPreferences;
    private UserDao userDao;
    private int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);

        // 初始化视图
        tvUsername = view.findViewById(R.id.tvUsername);
        etAddress = view.findViewById(R.id.etAddress);
        btnSaveAddress = view.findViewById(R.id.btnSaveAddress);
        btnViewOrderHistory = view.findViewById(R.id.btnViewOrderHistory);

        // 获取SharedPreferences和UserDao实例
        sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userDao = new UserDao(getContext());

        // 获取当前用户信息
        String username = sharedPreferences.getString("username", "未知用户");
        userId = userDao.getUserId(username);

        // 显示用户信息
        tvUsername.setText("用户名：" + username);

        // 加载并显示保存的地址
        String savedAddress = userDao.getUserAddress(userId);
        etAddress.setText(savedAddress);

        // 设置保存地址按钮的点击监听器
        btnSaveAddress.setOnClickListener(v -> {
            String newAddress = etAddress.getText().toString();
            if (!newAddress.isEmpty()) {
                // 保存地址到数据库
                userDao.updateUserAddress(userId, newAddress);
                Toast.makeText(getContext(), "地址已保存", Toast.LENGTH_SHORT).show();
                // 更新编辑框中的地址
                etAddress.setText(newAddress);
            } else {
                Toast.makeText(getContext(), "请输入地址", Toast.LENGTH_SHORT).show();
            }
        });

        // 设置查看订单历史按钮的点击监听器
        btnViewOrderHistory.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), OrderHistoryActivity.class);
            startActivity(intent);
        });

        return view;
    }

}

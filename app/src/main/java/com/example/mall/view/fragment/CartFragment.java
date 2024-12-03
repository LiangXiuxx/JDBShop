package com.example.mall.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mall.R;
import com.example.mall.model.CartItem;
import com.example.mall.view.activity.OrderActivity;
import com.example.mall.view.adapter.CartAdapter;
import com.example.mall.viewmodel.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private CartViewModel cartViewModel;
    private TextView tvTotalPrice;
    private CheckBox cbSelectAll;
    private Button btnCheckout;
    private Button btnDelete;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // 初始化 RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CartAdapter cartAdapter = new CartAdapter();
        recyclerView.setAdapter(cartAdapter);

        // 初始化视图
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        cbSelectAll = view.findViewById(R.id.selectAllCheckBox);
        btnCheckout = view.findViewById(R.id.btnCheckout);
        btnDelete = view.findViewById(R.id.btnDelete);

        // 初始化 ViewModel
        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
        cartViewModel.getCartItems().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                cartAdapter.setCartItems(cartItems); // 更新 RecyclerView 的数据集
                updateTotalPrice(cartAdapter.getSelectedItems()); // 更新总价显示
            }
        });


        // 处理单个商品的选择
        cartAdapter.setOnCartItemChangeListener(new CartAdapter.OnCartItemChangeListener() {
            @Override
            public void onCartItemChanged() {
                updateTotalPrice(cartAdapter.getSelectedItems());
            }
        });

        // 处理全选复选框
        cbSelectAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cartAdapter.selectAllItems();
            } else {
                cartAdapter.deselectAllItems();
            }
            updateTotalPrice(cartAdapter.getSelectedItems());
        });

        // 处理结算按钮点击事件
        btnCheckout.setOnClickListener(v -> {
            List<CartItem> selectedItems = cartAdapter.getSelectedItems();
            if (!selectedItems.isEmpty()) {
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                intent.putParcelableArrayListExtra("ORDER_ITEMS", new ArrayList<>(selectedItems));
                startActivity(intent);
            }
        });

        // 处理删除按钮点击事件
        btnDelete.setOnClickListener(v -> {
            cartAdapter.removeSelectedItems();
            updateTotalPrice(cartAdapter.getSelectedItems());
        });

        return view;
    }

    // 更新总价的方法
    private void updateTotalPrice(List<CartItem> selectedItems) {
        double totalPrice = 0.0;
        for (CartItem item : selectedItems) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        tvTotalPrice.setText(String.format("总价：%.2f 元", totalPrice));
    }
}

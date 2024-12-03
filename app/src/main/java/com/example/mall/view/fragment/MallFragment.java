package com.example.mall.view.fragment;
//商城浏览Fragment
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mall.view.adapter.MallProductAdapter;
import com.example.mall.model.Product;
import com.example.mall.R;

import java.util.ArrayList;
import java.util.List;

public class MallFragment extends Fragment {

    private RecyclerView recyclerView;//有限的屏幕里展示大量内容（产品），实时动态刷新缓存
    private MallProductAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mall, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 columns

        //产品列表
        List<Product> products = new ArrayList<>();
        // 添加产品（产品名，产品描述，价格，图片）
        products.add(new Product("复古色水洗牛仔裤",null, 55.54, R.drawable.kuzi01));
        products.add(new Product("美式短袖ootd",null, 38.8, R.drawable.duanxiu01));
        products.add(new Product("春季游玩编织草帽", null,18.8, R.drawable.maozi));
        products.add(new Product("男士设计感工装裤", null,66.8, R.drawable.kuzi02));
        products.add(new Product("撞色彩色白牛仔裤",null, 65.54, R.drawable.kuzi03));
        products.add(new Product("回力绿色板鞋厚底",null, 79.9, R.drawable.xiezi));
        products.add(new Product("味滋源海苔肉松卷",null, 9.9, R.drawable.weiziyuan));
        products.add(new Product("衬衫水洗复古棕",null, 9.9, R.drawable.chenshan01));
        adapter = new MallProductAdapter(products);
        recyclerView.setAdapter(adapter);

        return view;
    }
}

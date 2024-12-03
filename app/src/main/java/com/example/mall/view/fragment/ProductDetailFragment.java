//商品详情界面Fragment
package com.example.mall.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mall.model.CartItem;
import com.example.mall.viewmodel.CartViewModel;
import com.example.mall.R;

public class ProductDetailFragment extends Fragment {

    private static final String ARG_NAME = "product_name";//产品名
    private static final String ARG_PRICE = "product_price";//产品价格
    private static final String ARG_IMAGE_RES_ID = "product_image_res_id";//产品图片资源id
    private static final String ARG_DESCRIPTION = "product_description";//产品描述

    private String productName;
    private double productPrice;
    private int productImageResId;
    private String productDescription;
    private CartViewModel cartViewModel;

    public static ProductDetailFragment newInstance(String name, double price, int imageResId, String description) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putDouble(ARG_PRICE, price);
        args.putInt(ARG_IMAGE_RES_ID, imageResId);
        args.putString(ARG_DESCRIPTION, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        if (getArguments() != null) {
            productName = getArguments().getString(ARG_NAME);
            productPrice = getArguments().getDouble(ARG_PRICE);
            productImageResId = getArguments().getInt(ARG_IMAGE_RES_ID);
            productDescription = getArguments().getString(ARG_DESCRIPTION);
        }

        ImageView productImage = view.findViewById(R.id.productImage);//产品图片
        TextView productNameText = view.findViewById(R.id.productName);//产品名
        TextView productPriceText = view.findViewById(R.id.productPrice);//产品价格
        TextView productDescriptionText = view.findViewById(R.id.productDescription);//产品描述
        NumberPicker quantityPicker = view.findViewById(R.id.quantityPicker);//产品数量选择
        Button addToCartButton = view.findViewById(R.id.addToCartButton);//添加到购物车按钮

        productImage.setImageResource(productImageResId);//产品图片id
        productNameText.setText(productName);//产品名
        productPriceText.setText(String.format("¥%.2f", productPrice));//价格，两位浮点小数
        productDescriptionText.setText(productDescription);//产品名称

        quantityPicker.setMinValue(1);//数量1-100，可修改成填数字的形式
        quantityPicker.setMaxValue(100);

        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = quantityPicker.getValue();
                cartViewModel.addToCart(new CartItem(productName, productPrice, productImageResId, quantity));
                Toast.makeText(getContext(), "商品已成功添加到购物车", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}

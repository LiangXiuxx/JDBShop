package com.example.mall.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mall.model.Product;
import com.example.mall.R;
import com.example.mall.view.fragment.ProductDetailFragment;

import java.util.List;

/**
 * 商城商品适配器类，用于管理和显示商城中的商品。
 */
public class MallProductAdapter extends RecyclerView.Adapter<MallProductAdapter.ViewHolder> {

    // 存储商品列表
    private List<Product> productList;

    public MallProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 加载商品项的布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mall_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 获取当前位置的商品
        Product product = productList.get(position);

        // 设置商品名称
        holder.productName.setText(product.getName());
        // 设置商品价格
        holder.productPrice.setText(String.format("¥%.2f", product.getPrice()));
        // 设置商品图片
        holder.productImage.setImageResource(product.getImageResId());

        // 设置商品项点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = (FragmentActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, ProductDetailFragment.newInstance(
                                product.getName(), product.getPrice(), product.getImageResId(), product.getDescription()))
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    /**
     * ViewHolder类，用于缓存商品项的视图。
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage; // 商品图片
        TextView productName; // 商品名称
        TextView productPrice; // 商品价格

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 初始化视图组件
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
        }
    }
}

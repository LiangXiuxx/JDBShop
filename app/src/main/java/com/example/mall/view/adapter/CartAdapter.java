package com.example.mall.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mall.model.CartItem;
import com.example.mall.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 购物车适配器类，用于管理和显示购物车中的商品项。
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    // 存储购物车商品项的列表
    private List<CartItem> cartItems = new ArrayList<>();
    // 存储选中商品项的集合
    private Set<CartItem> selectedItems = new HashSet<>();
    // 购物车商品项改变的监听器
    private OnCartItemChangeListener onCartItemChangeListener;

    /**
     * 设置购物车商品项列表，并通知适配器刷新视图。
     *
     * @param cartItems 要显示的购物车商品项列表。
     */
    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        notifyDataSetChanged();
    }

    /**
     * 设置购物车商品项改变的监听器。
     *
     * @param listener 监听器实例。
     */
    public void setOnCartItemChangeListener(OnCartItemChangeListener listener) {
        this.onCartItemChangeListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 加载购物车商品项的布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 获取当前位置的购物车商品项
        CartItem cartItem = cartItems.get(position);

        // 设置商品名称
        holder.productName.setText(cartItem.getName());
        // 设置商品价格
        holder.productPrice.setText(String.format("¥%.2f", cartItem.getPrice()));
        // 设置商品图片
        holder.productImage.setImageResource(cartItem.getImageResId());
        // 设置商品数量
        holder.productQuantity.setText(String.valueOf(cartItem.getQuantity()));

        // 清除之前的选中状态监听器以防止触发
        holder.selectCheckBox.setOnCheckedChangeListener(null);
        // 设置当前的选中状态
        holder.selectCheckBox.setChecked(selectedItems.contains(cartItem));
        // 设置选中状态改变的监听器
        holder.selectCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.add(cartItem);
            } else {
                selectedItems.remove(cartItem);
            }
            notifyDataSetChanged();
            if (onCartItemChangeListener != null) {
                onCartItemChangeListener.onCartItemChanged();
            }
        });

        // 设置增加数量按钮的点击事件
        holder.increaseButton.setOnClickListener(v -> {
            cartItem.increaseQuantity();
            holder.productQuantity.setText(String.valueOf(cartItem.getQuantity()));
            if (onCartItemChangeListener != null) {
                onCartItemChangeListener.onCartItemChanged();
            }
        });

        // 设置减少数量按钮的点击事件
        holder.decreaseButton.setOnClickListener(v -> {
            if (cartItem.getQuantity() > 1) {
                cartItem.decreaseQuantity();
                holder.productQuantity.setText(String.valueOf(cartItem.getQuantity()));
                if (onCartItemChangeListener != null) {
                    onCartItemChangeListener.onCartItemChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    /**
     * 获取选中的商品项列表。
     *
     * @return 选中的商品项列表。
     */
    public List<CartItem> getSelectedItems() {
        return new ArrayList<>(selectedItems);
    }

    /**
     * 选中所有商品项。
     */
    public void selectAllItems() {
        selectedItems.clear();
        selectedItems.addAll(cartItems);
        notifyDataSetChanged();
    }

    /**
     * 取消选中所有商品项。
     */
    public void deselectAllItems() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    /**
     * ViewHolder类，用于缓存购物车商品项的视图。
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox selectCheckBox; // 选中复选框
        ImageView productImage; // 商品图片
        TextView productName; // 商品名称
        TextView productPrice; // 商品价格
        TextView productQuantity; // 商品数量
        Button increaseButton; // 增加数量按钮
        Button decreaseButton; // 减少数量按钮

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 初始化视图组件
            selectCheckBox = itemView.findViewById(R.id.selectCheckBox);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            increaseButton = itemView.findViewById(R.id.increaseButton);
            decreaseButton = itemView.findViewById(R.id.decreaseButton);
        }
    }

    /**
     * 购物车商品项改变的监听器接口。
     */
    public interface OnCartItemChangeListener {
        void onCartItemChanged();
    }
    //删除选中商品
    public void removeSelectedItems() {
        cartItems.removeAll(selectedItems); // 从数据集中移除选中的商品
        selectedItems.clear();
        notifyDataSetChanged(); // 更新视图
    }

}
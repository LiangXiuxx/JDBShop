package com.example.mall.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.example.mall.R;
import java.util.List;

// 图像分页适配器类，用于在ViewPager中显示图片
public class ImagePagerAdapter extends PagerAdapter {

    // 上下文对象
    private Context context;
    // 图片资源ID列表
    private List<Integer> images;

    // 构造方法，初始化适配器
    public ImagePagerAdapter(Context context, List<Integer> images) {
        this.context = context;
        this.images = images;
    }

    // 实例化页卡
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // 加载页卡布局
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_image_pager, container, false);

        // 设置图片
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(images.get(position));

        // 添加页卡视图到容器中
        container.addView(view);

        return view;
    }

    // 销毁页卡
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    // 返回页卡数量
    @Override
    public int getCount() {
        return images.size();
    }

    // 判断页卡是否与指定视图对象关联
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}

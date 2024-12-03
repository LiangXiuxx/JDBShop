package com.example.mall.view.fragment;
//首页Fragment
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.mall.view.adapter.ImagePagerAdapter;
import com.example.mall.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private ViewPager viewPager;
    private RecyclerView recyclerView;

    private static final long DELAY_MS = 500; // 延迟时间
    private static final long PERIOD_MS = 3000; // 每次切换的时间间隔
    private Timer timer;
    private int currentPage = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 初始化 ViewPager
        viewPager = view.findViewById(R.id.viewPager);
        setupViewPager();


        return view;
    }

    private void setupViewPager() {
        // 轮播图片
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.kuzi03);
        images.add(R.drawable.shangyi01);
        images.add(R.drawable.duanxiu01);

        // 建立并设置ViewPager适配器
        ImagePagerAdapter adapter = new ImagePagerAdapter(getContext(), images);
        viewPager.setAdapter(adapter);
        // 自动轮播功能
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == images.size()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer(); // 初始化计时器器
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_MS, PERIOD_MS);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) {
            timer.cancel(); // 销毁计时器
        }
    }


}

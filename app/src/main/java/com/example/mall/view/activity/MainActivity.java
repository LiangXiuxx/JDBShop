package com.example.mall.view.activity;
//主界面
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mall.view.fragment.CartFragment;
import com.example.mall.model.DatabaseHelper;
import com.example.mall.view.fragment.HomeFragment;
import com.example.mall.view.fragment.MallFragment;
import com.example.mall.view.fragment.MyFragment;
import com.example.mall.viewmodel.CartViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.mall.R;
public class MainActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    private Fragment currentFragment; // 当前显示的Fragment
    private CartViewModel cartViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // 加载首页 Fragment
        loadFragment(new HomeFragment());
        // 底部导航栏点击事件
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.nav_home) {
                    currentFragment = new HomeFragment();//首页
                } else if (menuItem.getItemId() == R.id.nav_mall) {
                    currentFragment = new MallFragment();//商场
                } else if (menuItem.getItemId() == R.id.nav_cart) {
                    currentFragment = new CartFragment();//购物车
                } else if (menuItem.getItemId() == R.id.nav_my) {
                    currentFragment = new MyFragment();//我的
                }
                loadFragment(currentFragment); // 加载选中的Fragment
                return true;
            }
        });

        // 加载默认显示的Fragment，默认为首页
        currentFragment = new HomeFragment(); // 创建HomeFragment实例
        loadFragment(currentFragment); // 加载默认Fragment

// 确保 MainActivity 加载 HomeFragment
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }

        dbHelper = new DatabaseHelper(this);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        // 获取传递的用户ID
        Intent intent = getIntent();
        int userId = intent.getIntExtra("USER_ID", -1);
        if (userId != -1) {
            cartViewModel.setUserId(userId);
        }
    }
    // 加载Fragment的方法
    private boolean loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 替换Fragment容器中的内容为选中的Fragment
        transaction.replace(R.id.fragment_container, fragment);
        // 提交事务并返回提交是否成功
        transaction.commit();
        return false;
    }
    // 加载菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    // 处理菜单项点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_cart) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new CartFragment())
                    .addToBackStack(null)
                    .commit();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}

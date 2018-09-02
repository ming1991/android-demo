package com.example.materialdesign;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;


/**
 * 1.ToolBar的使用
 https://blog.csdn.net/mchenys/article/details/51533689
 */
public class MainActivity extends AppCompatActivity {

    private Button mBtnShowSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        // 显示导航按钮
        toolbar.setNavigationIcon(R.drawable.icon_back);

        setSupportActionBar(toolbar);

        //隐藏标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //back键的点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You clicked Navigation  back", Toast.LENGTH_SHORT).show();
            }
        });

        //显示Snackbar
        mBtnShowSnackbar = findViewById(R.id.btn_show_snackbar);
        mBtnShowSnackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar snackbar = Snackbar.make(mBtnShowSnackbar, "已删除一个会话", Snackbar.LENGTH_SHORT).setAction("撤销", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).setCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {

                                switch (event) {

                                    case Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE:
                                    case Snackbar.Callback.DISMISS_EVENT_MANUAL:
                                    case Snackbar.Callback.DISMISS_EVENT_SWIPE:
                                    case Snackbar.Callback.DISMISS_EVENT_TIMEOUT:
                                        //TODO 网络操作
                                        Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                        break;
                                    case Snackbar.Callback.DISMISS_EVENT_ACTION:
                                        Toast.makeText(MainActivity.this, "撤销了删除操作", Toast.LENGTH_SHORT).show();
                                        break;

                                }
                            }

                            @Override
                            public void onShown(Snackbar snackbar) {
                                super.onShown(snackbar);
                                Log.i("ming007", "onShown");
                            }
                        });
                //click的字体颜色
                snackbar.setActionTextColor(Color.GREEN);
                //设置背景颜色
                View view = snackbar.getView();
                view.setBackgroundColor(Color.BLUE);
                //内容的字体颜色与大小
                TextView tvSnackbarText = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tvSnackbarText.setText("hello world");
                tvSnackbarText.setTextColor(Color.RED);
                tvSnackbarText.setTextSize(30);
                snackbar.show();

            }
        });

        //FloatingActionButton的点击事件
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "FloatingActionButton 被点击", Toast.LENGTH_SHORT).show();
            }
        });

        //跳转到AppBarLayout界面
        Button btn_show_appBarLayout = findViewById(R.id.btn_show_appBarLayout);
        btn_show_appBarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AppBarLayoutActivity.class);
                startActivity(intent);
            }
        });

        // 跳转到collapsingToolbarLayout界面
        Button btn_show_collapsingToolbarLayout = findViewById(R.id.btn_show_collapsingToolbarLayout);
        btn_show_collapsingToolbarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CollapsingToolbarLayoutActivity.class);
                startActivity(intent);
            }
        });

        //跳转到textInputLayout界面
        Button btn_show_textInputLayout = findViewById(R.id.btn_show_textInputLayout);
        btn_show_textInputLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TextInputLayoutActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * 创建menu菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);

        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return true;
    }

    /**
     * menu按钮的点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(this, "You clicked Backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "You clicked Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }


    /**
     * menu菜单打开
     *
     * @param featureId
     * @param menu
     * @return
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        //Toast.makeText(this, "onMenuOpened", Toast.LENGTH_SHORT).show();
        return super.onMenuOpened(featureId, menu);
    }

    /**
     * menu菜单关闭
     *
     * @param featureId
     * @param menu
     */
    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        super.onPanelClosed(featureId, menu);
        //Toast.makeText(this, "Closed", Toast.LENGTH_SHORT).show();
    }


}

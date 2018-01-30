package com.example.a14512.discover.modules.travel.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseSwipeBackActivity;
import com.example.a14512.discover.modules.travel.WebActivity;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/1/27
 */

public class TravelKnowledgeActivity extends BaseSwipeBackActivity {

    private ImageView mImgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_knowledge);
        initView();
        onClick();
    }

    private void onClick() {
        mImgBack.setOnClickListener(v -> finish());
    }

    private void initView() {
        mImgBack = findViewById(R.id.img_toolbar_left);
        TextView title = findViewById(R.id.tv_toolbar_title);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ListView listView = findViewById(R.id.list_view_travel);

        setSupportActionBar(toolbar);
        setStatusBarColor(R.color.mainToolbar);
        title.setText("旅游小知识");

        ArrayList<String> titles = new ArrayList<>();
        titles.add("旅游小妙招");
        titles.add("突发情况自救手册");
        titles.add("山城重庆");
        titles.add("知名景点");
        titles.add("区域分布");
        titles.add("平安出行留意事项");
        listView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, titles));
        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            switch (position) {
                case 0:
                    startIntentActivity(this, new WebActivity(), "title", titles.get(position),
                            "url", "file:///android_asset/travel/travel_tips/index.html");
                    break;
                case 1:
                    startIntentActivity(this, new WebActivity(), "title", titles.get(position),
                            "url", "file:///android_asset/travel/emergency_self-help_manual/index.html");
                    break;
                case 2:
                    startIntentActivity(this, new WebActivity(), "title", titles.get(position),
                            "url", "file:///android_asset/travel/chongqing/index.html");
                    break;
                case 3:
                    startIntentActivity(this, new WebActivity(), "title", titles.get(position),
                            "url", "file:///android_asset/travel/famous_scenic/index.html");
                    break;
                case 4:
                    startIntentActivity(this, new WebActivity(), "title", titles.get(position),
                            "url", "file:///android_asset/travel/areal_distribution/index.html");
                    break;
                case 5:
                    startIntentActivity(this, new WebActivity(), "title", titles.get(position),
                            "url", "file:///android_asset/travel/safety_travel_notes/index.html");
                    break;
                default:
                    break;
            }
        });
    }
}

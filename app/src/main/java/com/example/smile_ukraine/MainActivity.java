package com.example.smile_ukraine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ViewUtils;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    //private Toolbar toolbar;
    private Button button_settings;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*ImageButton btn_settings = (ImageButton) findViewById(R.id.button_sett);
        //повесили на него листенера
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Settings_activity.class);
                startActivity(intent);
            }
        });*/

        tabLayout = findViewById(R.id.tabLayoult);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.setBackgroundColor(Color.TRANSPARENT);
/*        toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);*/

        //getSupportActionBar().setTitle("Whattsapp");
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(viewPagerAdapter.getCount() - 2);

    }
}
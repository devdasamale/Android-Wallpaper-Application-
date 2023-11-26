package com.devdas.wallpaper_161123.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.devdas.wallpaper_161123.R;
import com.devdas.wallpaper_161123.adapter.ViewPagerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    public static final String[] category = new String[]{"Animals", "Cars", "Cultural","Nature", "Sky", "Flowers", "Heart", "Love",  "Motivational"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, initializationStatus -> {
            AdView adView  = new AdView(this);
            adView.setAdUnitId(getString(R.string.Banner));
            adView.setAdSize(new AdSize(300, 50));
            LinearLayout adContainer = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            adContainer.addView(adView);adView.loadAd(adRequest);
        });

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer);
        NavigationView navigation = findViewById(R.id.navigation);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigation.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            if (item.getItemId() == R.id.policy) {
//                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://google.com")));
                new AlertDialog.Builder(this).setMessage("This is a prototype of a wallpaper app made by an student, If you have queries message on the account given below \n \n  Instgram: ig_devdas \n Email: devdas.amale.2002@gmail.com").setPositiveButton("Dismiss", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();


            } else if (item.getItemId() == R.id.contact) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.instagram.com/ig_devdas/?utm_source=ig_web_button_share_sheet&igshid=OGQ5ZDc2ODk2ZA==")));
            } else if (item.getItemId() == R.id.share) {
                startActivity(new Intent(Intent.ACTION_SEND).setType("text/Plain").putExtra(Intent.EXTRA_TEXT, "Let me recommend this great app. Download Now - https://play.google.com/store/apps/details?id=" + getPackageName()));
            } else if (item.getItemId() == R.id.about) {
                new AlertDialog.Builder(this).setMessage("In this App you can Set, Download & Share Wallpapers \n\n Developer : Devdas Amale (BE Student) \n\n Email: devdas.amale.2002@gmail.com").setPositiveButton("Dismiss", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
            }
            return false;
        });
        viewPager.setAdapter(new ViewPagerAdapter(this));
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(category[position])).attach();

    }
}

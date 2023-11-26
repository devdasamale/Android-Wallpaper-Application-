package com.devdas.wallpaper_161123.activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.Manifest.permission.SET_WALLPAPER;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.devdas.wallpaper_161123.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.Random;

public class PreviewActivity extends AppCompatActivity {

    public static String wallpaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        MobileAds.initialize(this, initializationStatus -> {
            AdView adView  = new AdView(this);
            adView.setAdUnitId(getString(R.string.Banner));
            adView.setAdSize(new AdSize(300, 50));
            LinearLayout adContainer = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            adContainer.addView(adView);adView.loadAd(adRequest);
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(view -> finish());

        String permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permission = READ_MEDIA_IMAGES;
        } else {
            permission = READ_EXTERNAL_STORAGE;
        }

        ImageView imageView = findViewById(R.id.img);
        Picasso.get().load(wallpaper).placeholder(R.mipmap.ic_launcher_square).fit().into(imageView);
        findViewById(R.id.setwall).setOnClickListener(view -> {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
            if (ContextCompat.checkSelfPermission(this, SET_WALLPAPER) == PackageManager.PERMISSION_GRANTED) {
                Picasso.get().load(wallpaper).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        try {
                            wallpaperManager.setBitmap(bitmap);
                            Toast.makeText(PreviewActivity.this, "Wallpaper Set Successfully", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Toast.makeText(PreviewActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
            } else {
                ActivityCompat.requestPermissions(this, new String[]{SET_WALLPAPER, WRITE_EXTERNAL_STORAGE, permission}, 222);
            }
        });

        findViewById(R.id.download).setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                Picasso.get().load(wallpaper).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        String title = "Wallpaper_" + new Random().nextInt(10000);
                        String des = "Download from" + getString(R.string.app_name);
                        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, des);
                        Toast.makeText(PreviewActivity.this, "Downloaded", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
            } else {
                ActivityCompat.requestPermissions(this, new String[]{SET_WALLPAPER, WRITE_EXTERNAL_STORAGE, permission}, 222);
            }
        });

        findViewById(R.id.shareImg).setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                Picasso.get().load(wallpaper).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        String title = "Wallpaper_" + new Random().nextInt(10000);
                        String des = "Download from" + getString(R.string.app_name);
                        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, des);
                        Intent intent = new Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_STREAM, Uri.parse(path)).putExtra(Intent.EXTRA_TEXT, "Download the app - https://play.google.com/store/apps/details?id=" + getPackageName());
                        startActivity(Intent.createChooser(intent, "Share via"));
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
            } else {
                ActivityCompat.requestPermissions(this, new String[]{SET_WALLPAPER, WRITE_EXTERNAL_STORAGE, permission}, 222);
            }

        });
    }
}
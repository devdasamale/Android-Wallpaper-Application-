package com.devdas.wallpaper_161123.adapter;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.devdas.wallpaper_161123.R;

public class Loader extends Dialog {
    public Loader(@NonNull Context context) {
        super(context);
        setContentView(R.layout.loader);
        setCancelable(false);
    }
}

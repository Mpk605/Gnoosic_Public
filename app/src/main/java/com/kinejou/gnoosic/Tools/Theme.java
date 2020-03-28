package com.kinejou.gnoosic.Tools;

import android.app.Activity;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.kinejou.gnoosic.R;

public class Theme {
    public static int getTheme(Activity activity) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        String theme = sp.getString("listTheme", "-1");
        switch (theme) {
            case "LightTheme":
                return R.style.AppTheme;
            case "DarkTheme":
                return R.style.DarkTheme;
            case "UltraDarkTheme":
                return R.style.UltraDarkTheme;
            case "UnicornTheme":
                return R.style.UnicornTheme;
            default:
                return R.style.AppTheme;
        }
    }
}

package com.example.fightersoft;

import android.app.Activity;
import android.content.Intent;

public class Utils {

    private static int sTheme;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_WHITE = 1;
    public final static int THEME_BLUE = 2;

    public static void changeTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetTheme(Activity activity) {

        switch (sTheme)
        {
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.FirstTheme);
                break;
            case THEME_WHITE:
                activity.setTheme(R.style.SecondTheme);
                break;
            case THEME_BLUE:
                activity.setTheme(R.style.ThirdTheme);
                break;
        }

    }

}

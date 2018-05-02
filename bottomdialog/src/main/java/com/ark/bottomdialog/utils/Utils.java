package com.ark.bottomdialog.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.ColorUtils;
import android.util.TypedValue;
import android.widget.Button;

/**
 * Created by Muhammed Noufal Koorikkadan on 01/05/2018
 * mail us : noufal.ark@gmail.com
 */
public class Utils {
    /**
     * To check is color Light or Dark mode
     *
     * @param color integer value for color
     * @return boolean value
     */
    private static boolean isLightMode(@ColorInt int color) {
        if (color == Color.BLACK) return false;
        else if (color == Color.WHITE || color == Color.TRANSPARENT) return true;
        final double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return darkness < 0.4;
    }

    /**
     * To decide which color want to use in button based on background color
     *
     * @param color integer value for color
     * @return integer value of color
     */
    public static int buttonTextColor(@ColorInt int color) {
        if (isLightMode(color))
            return Color.BLACK;
        else return Color.WHITE;
    }

    /**
     * To fetch accent color
     *
     * @param context context as parameter
     * @return typed value
     */
    public static int getAccentColor(Context context) {
        int colorAttr;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            colorAttr = android.R.attr.colorAccent;
        } else {
            colorAttr = context.getResources().getIdentifier("colorAccent", "attr", context.getPackageName());
        }
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(colorAttr, outValue, true);
        return outValue.data;
    }

    public static void setButton(@ColorInt int bgColor, @ColorInt int color, Button button, boolean colored) {
        if (!colored) {
            if (bgColor!=-1)
                color = bgColor;
            else
                color = Color.parseColor("#ffffff");
        }

        int selectedColor = isLightMode(color) ?
                ColorUtils.blendARGB(color, Color.parseColor("#000000"), 0.15f) :
                ColorUtils.blendARGB(color, Color.parseColor("#FFFFFF"), 0.20f);

        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                new int[]{selectedColor, selectedColor});
        GradientDrawable drawable2 = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                new int[]{color, color});

        drawable.setCornerRadius(dpToPx(2));
        drawable2.setCornerRadius(dpToPx(2));

        StateListDrawable button1bg = new StateListDrawable();
        button1bg.addState(new int[] {android.R.attr.state_pressed}, drawable);
        button1bg.addState(new int[] {}, drawable2);
        button1bg.setExitFadeDuration(250);

        button.setBackgroundDrawable(button1bg);
    }

    public static int getTextColor(int color) {
        return isLightMode(color) ? Color.parseColor("#DE000000") : Color.parseColor("#FFFFFFFF");
    }

    public static int getTextColorSec(int color) {
        return isLightMode(color) ? Color.parseColor("#8A000000") : Color.parseColor("#B3FFFFFF");
    }

    public static int getThemeBgColor(Context context) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.windowBackground, typedValue, true)) {
            return typedValue.data;
        }
        return -1;
    }

    private static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}

package pl.orzechowski.trellomanagmentapp.helpers;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;

import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import pl.orzechowski.trellomanagmentapp.R;

public class MHelper {

    public static int convertDpToPx(Context context, int value) {
        if (context == null) return value;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(value * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static float convertDpToPx(Context context, float value) {
        if (context == null) return value;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(value * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static void hideKeyboard(Activity activity) {
        if (activity == null) return;
        InputMethodManager inputManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager == null || activity.getCurrentFocus() == null) return;
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static int getColorRes(Context context, int position) {
        if (context == null) return 0;

        switch (position) {
            case 0:
                return ContextCompat.getColor(context, R.color.t_red);
            case 1:
                return ContextCompat.getColor(context, R.color.t_orange);
            case 2:
                return ContextCompat.getColor(context, R.color.t_green);
            default:
                return ContextCompat.getColor(context, R.color.white);
        }
    }
}

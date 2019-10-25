package pl.orzechowski.trellomanagmentapp.base;

import android.animation.LayoutTransition;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;
import pl.orzechowski.trellomanagmentapp.R;
import pl.orzechowski.trellomanagmentapp.views.NSTextView;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MBindingAdapter {

    @BindingAdapter("android:is_visible_animated_quick")
    public static void setAnimatedVisibilityQuick(View target, boolean isVisible) {
        target.setVisibility(isVisible ? VISIBLE : View.GONE);
        Animation animation = AnimationUtils.loadAnimation(target.getContext(), R.anim.fade_in);
        animation.setStartOffset(0);
        if (isVisible) {
            target.startAnimation(animation);
        }
    }

    @BindingAdapter("android:is_visible_animated")
    public static void setAnimatedVisibility(View target, boolean isVisible) {
        ((ViewGroup) target).getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        ViewGroup.LayoutParams params = target.getLayoutParams();
        params.height = isVisible ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;
        target.setLayoutParams(params);
        target.requestLayout();
    }

    @BindingAdapter("android:recyclerView_adapter")
    public static void setAdapter(RecyclerView target, RecyclerView.Adapter adapter) {
        target.setAdapter(adapter);
    }

    @BindingAdapter("app:ns_font")
    public static void setFont(NSTextView target, @StringRes int res) {
        target.setFont(target.getContext().getString(res));
    }

    @BindingAdapter("android:recyclerView_layoutManager")
    public static void setLayoutManager(RecyclerView target, RecyclerView.LayoutManager lm) {
        if (target.getLayoutManager() == null) {
            if (lm.getClass() == LinearLayoutManager.class) {
                ((LinearLayoutManager) lm).setSmoothScrollbarEnabled(true);
            }
            target.setLayoutManager(lm);
        }
    }

    @BindingAdapter("android:m_background_color")
    public static void setBackgroundColor(TextView target, int colorRes) {
        target.setBackgroundColor(colorRes);
    }

    @BindingAdapter("android:m_text_color")
    public static void setMTextColor(TextView target, Integer colorRes) {
        target.setTextColor(ContextCompat.getColor(target.getContext(), colorRes));
    }

    @BindingAdapter("android:image_res")
    public static void setImageResource(ImageView target, Integer backgroundRes) {
        if (backgroundRes == null) return;
        target.setImageResource(backgroundRes);
    }

    @BindingAdapter("android:lottie_play_visibility")
    public static void playLottieInvisible(LottieAnimationView target, Integer visibility) {
        if (visibility == null) visibility = View.GONE;
        if (visibility == VISIBLE) {
            target.playAnimation();
        } else if (visibility == INVISIBLE || visibility == View.GONE) {
            target.cancelAnimation();
        }

        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(100);
        TransitionManager.beginDelayedTransition((ViewGroup) target.getRootView(), autoTransition);
        target.setVisibility(visibility);
    }
}

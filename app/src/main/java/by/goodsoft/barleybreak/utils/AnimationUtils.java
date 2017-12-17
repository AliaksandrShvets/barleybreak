package by.goodsoft.barleybreak.utils;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by Aleksandr Shvets on 06.12.2017.
 */

public class AnimationUtils {

    public static final int TRANSLATE_DURATION = 200;
    public static final int SCALE_DURATION = 1000;
    public static final int SCALE_OFFSET = 200;

    public static TranslateAnimation getTranslateAnimation(
            float xFrom, float xTo, float yFrom, float yTo) {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                xFrom - xTo, 0, yFrom - yTo, 0);
        translateAnimation.setDuration(TRANSLATE_DURATION);
        return translateAnimation;
    }

    public static AnimationSet getScaleAnimation(float from, float to, int startOffset) {
        AnimationSet animationSet = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(from, to, from, to,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, from/2);
        scaleAnimation.setInterpolator(new DecelerateInterpolator());
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1f);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(SCALE_DURATION);
        animationSet.setStartOffset(startOffset * SCALE_OFFSET);
        return animationSet;
    }
}

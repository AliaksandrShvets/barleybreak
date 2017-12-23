package by.goodsoft.barleybreak.utils;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by Aleksandr Shvets
 * on 06.12.2017.
 */

public class AnimationUtils {

    private static final int TRANSLATE_DURATION = 200;
    private static final int SCALE_DURATION = 1000;
    private static final int SCALE_OFFSET = 200;
    private static final float SCALE_FROM = 3f;
    private static final float SCALE_TO = 1f;

    public static TranslateAnimation getTranslateAnimation(float xFrom, float xTo, float yFrom, float yTo) {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                xFrom - xTo, 0, yFrom - yTo, 0);
        translateAnimation.setDuration(TRANSLATE_DURATION);
        return translateAnimation;
    }

    public static AnimationSet getScaleAnimation(View view, int startOffset) {
        AnimationSet animationSet = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(SCALE_FROM, SCALE_TO, SCALE_FROM, SCALE_TO,
                view.getX() + view.getWidth() / 2, view.getY() + view.getHeight() / 2);
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

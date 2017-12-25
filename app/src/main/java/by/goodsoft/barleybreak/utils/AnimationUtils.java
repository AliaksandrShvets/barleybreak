package by.goodsoft.barleybreak.utils;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import by.goodsoft.barleybreak.callbacks.SimpleCallback;

/**
 * Created by Aleksandr Shvets
 * on 06.12.2017.
 */

public class AnimationUtils {

    private static final int MAIN_ACTIVITY_DURATION = 600;

    public static void animateMainScreen(View top, View center, View bottom, boolean isEmersion, boolean isFirstEnter, final SimpleCallback callback) {
        if (isFirstEnter) {
            ScaleAnimation scaleAnimation = new ScaleAnimation(2, 1, 2, 1,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
            AnimationSet set = new AnimationSet(false);
            set.addAnimation(scaleAnimation);
            set.addAnimation(alphaAnimation);
            set.setDuration(MAIN_ACTIVITY_DURATION);
            center.startAnimation(set);
        }

        float topY = -top.getHeight();
        TranslateAnimation translateTopAnimation = new TranslateAnimation(0, 0,
                isEmersion ? topY : 0, isEmersion ? 0 : topY);
        translateTopAnimation.setDuration(MAIN_ACTIVITY_DURATION);
        translateTopAnimation.setFillAfter(true);
        top.startAnimation(translateTopAnimation);

        float bottomY = top.getY() + top.getHeight();
        TranslateAnimation translateBottomAnimation = new TranslateAnimation(0, 0,
                isEmersion ? bottomY : 0, isEmersion ? 0 : bottomY);
        translateBottomAnimation.setDuration(MAIN_ACTIVITY_DURATION);
        translateBottomAnimation.setFillAfter(true);
        bottom.startAnimation(translateBottomAnimation);

        if (callback != null) {
            translateBottomAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    callback.onCallback();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }

    private static final int TRANSLATE_DURATION = 200;

    public static TranslateAnimation getTranslateAnimation(float xFrom, float xTo, float yFrom, float yTo) {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                xFrom - xTo, 0, yFrom - yTo, 0);
        translateAnimation.setDuration(TRANSLATE_DURATION);
        return translateAnimation;
    }

    private static final int SCALE_DURATION = 1000;
    private static final int SCALE_OFFSET = 200;
    private static final float SCALE_FROM = 3f;
    private static final float SCALE_TO = 1f;

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

package by.goodsoft.barleybreak.utils;

import android.view.animation.TranslateAnimation;

/**
 * Created by Aleksandr Shvets on 06.12.2017.
 */

public class AnimationUtils {

    public static final int TRANSLATE_DURATION = 200;

    public static TranslateAnimation getTranslateAnimation(float xFrom, float xTo, float yFrom, float yTo) {
        TranslateAnimation translateAnimation = new TranslateAnimation(xFrom - xTo, 0, yFrom - yTo, 0);
        translateAnimation.setDuration(TRANSLATE_DURATION);
        return translateAnimation;
    }
}

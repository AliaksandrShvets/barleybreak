package by.goodsoft.barleybreak;

import android.app.Application;

/**
 * Created by Aleksandr Shvets
 * on 26.12.2017.
 */

public class BarleyBreakApp extends Application {

    private static BarleyBreakApp instance;
    private boolean activityStarted = false;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static BarleyBreakApp getInstance() {
        return instance;
    }

    public void setActivityStarted() {
        activityStarted = true;
    }

    public boolean isActivityStarted() {
        return activityStarted;
    }
}

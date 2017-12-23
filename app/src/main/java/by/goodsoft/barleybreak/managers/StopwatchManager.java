package by.goodsoft.barleybreak.managers;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import by.goodsoft.barleybreak.callbacks.StopwatchCallback;

/**
 * Created by Aleksandr Shvets
 * on 16.12.2017.
 */

public class StopwatchManager {

    private static final int ONE_SECOND_IN_MILLIS = 1000;

    private static Timer timer;
    private StopwatchCallback callback;

    private boolean isStarted = false;
    private int seconds = 0;
    private int millis = 0;
    private int startMillis = 0;

    public StopwatchManager(StopwatchCallback callback) {
        this.callback = callback;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void startTimer() {
        isStarted = true;
        startMillis = (int) new Date().getTime() % 1000;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seconds++;
                callback.onSecondUpdate(seconds);
            }
        }, ONE_SECOND_IN_MILLIS - millis, ONE_SECOND_IN_MILLIS);
    }

    public void stopTimer() {
        if (isStarted) {
            isStarted = false;
            timer.cancel();
            millis = (int) ((new Date().getTime() - startMillis + millis) % 1000);
        }
    }
}

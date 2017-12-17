package by.goodsoft.barleybreak.activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import by.goodsoft.barleybreak.R;
import by.goodsoft.barleybreak.callbacks.FieldActionCallback;
import by.goodsoft.barleybreak.callbacks.StopwatchCallback;
import by.goodsoft.barleybreak.managers.ItemsManager;
import by.goodsoft.barleybreak.managers.StopwatchManager;

import static by.goodsoft.barleybreak.managers.ItemsManager.FIELD_SIZE;

/**
 * Created by Aleksandr Shvets on 01.12.2017.
 */

public class GameActivity extends Activity {

    public static final String B_RANK = "bRank";

    private FrameLayout field;
    private TextView timer;
    private TextView steps;

    private int rank;

    private ItemsManager itemsManager;
    private StopwatchManager stopwatchManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initViews();
        initField();
        initBgStaticField();
        initTimer();
    }

    private void initViews() {
        rank = getIntent().getIntExtra(B_RANK, 3);
        timer = findViewById(R.id.ag_timer);
        field = findViewById(R.id.ag_field);
        steps = findViewById(R.id.ag_steps);
        steps.setTypeface(Typeface.createFromAsset(getAssets(), "hind_siliguri_light.ttf"));
    }

    private void initField() {
        itemsManager = new ItemsManager(this, field, rank, new FieldActionCallback() {
            @Override
            public void onWin() {
                Toast.makeText(GameActivity.this, "WIN", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onSwap(int count) {
                steps.setText(String.valueOf(count));
            }
        });
    }

    private void initBgStaticField() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int fieldSize = (int) (metrics.widthPixels * FIELD_SIZE);
        int fieldLeft = (metrics.widthPixels - fieldSize) / 2;
        int fieldTop = (metrics.heightPixels - metrics.widthPixels) / 2;
        int itemSize = fieldSize / rank;
        ItemsManager.generateStaticItems(getLayoutInflater(), findViewById(R.id.ag_field_static), rank, itemSize, fieldLeft, fieldTop);
    }

    private void initTimer() {
        timer.setTypeface(Typeface.createFromAsset(getAssets(), "hind_siliguri_light.ttf"));
        updateTime(0);
        stopwatchManager = new StopwatchManager(new StopwatchCallback() {
            @Override
            public void onSecondUpdate(int seconds) {
                updateTime(seconds);
            }
        });
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stopwatchManager.isStarted()) {
                    stopwatchManager.stopTimer();
                } else {
                    stopwatchManager.startTimer();
                }
            }
        });
    }

    private void updateTime(final int seconds) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timer.setText(String.format("%02d:%02d", seconds / 60, seconds % 60));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(0,0);
        stopwatchManager.startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
        stopwatchManager.stopTimer();
    }
}

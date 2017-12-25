package by.goodsoft.barleybreak.activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import by.goodsoft.barleybreak.R;
import by.goodsoft.barleybreak.callbacks.FieldActionCallback;
import by.goodsoft.barleybreak.callbacks.StopwatchCallback;
import by.goodsoft.barleybreak.managers.ItemsManager;
import by.goodsoft.barleybreak.managers.StopwatchManager;

import static by.goodsoft.barleybreak.items.Item.FONT_HIND_SILIGURI_LIGHT_TTF;

/**
 * Created by Aleksandr Shvets
 * on 01.12.2017.
 */

public class GameActivity extends Activity {

    public static final String B_RANK = "bRank";
    public static final float FIELD_SIZE = 0.85f;

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
        steps.setTypeface(Typeface.createFromAsset(getAssets(), FONT_HIND_SILIGURI_LIGHT_TTF));
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
                if (stopwatchManager != null && !stopwatchManager.isStarted()) {
                    stopwatchManager.startTimer();
                }
                steps.setText(String.valueOf(count));
            }
        });
    }

    private void initBgStaticField() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int fieldSize = (int) (metrics.widthPixels * FIELD_SIZE);
        int itemSize = fieldSize / rank;
        int fieldLeft = (metrics.widthPixels - fieldSize) / 2;
        int fieldTop = (metrics.heightPixels - metrics.widthPixels) / 2;
        ItemsManager.generateStaticItems(getLayoutInflater(), findViewById(R.id.ag_field_static), rank, itemSize, fieldLeft, fieldTop);
    }

    private void initTimer() {
        timer.setTypeface(Typeface.createFromAsset(getAssets(), FONT_HIND_SILIGURI_LIGHT_TTF));
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
                timer.setText(String.format(Locale.getDefault(), "%02d:%02d", seconds / 60, seconds % 60));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
        stopwatchManager.stopTimer();
    }
}

package by.goodsoft.barleybreak.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import by.goodsoft.barleybreak.R;
import by.goodsoft.barleybreak.utils.RandomUtils;

/**
 * Created by Aleksandr Shvets on 01.12.2017.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.am_start_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });
    }
}

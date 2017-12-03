package by.goodsoft.barleybreak.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import by.goodsoft.barleybreak.R;
import by.goodsoft.barleybreak.managers.ItemsManager;

/**
 * Created by Aleksandr Shvets on 01.12.2017.
 */

public class GameActivity extends Activity {

    ItemsManager itemsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        itemsManager = new ItemsManager(this, (FrameLayout) findViewById(R.id.ag_field), 4);
    }
}

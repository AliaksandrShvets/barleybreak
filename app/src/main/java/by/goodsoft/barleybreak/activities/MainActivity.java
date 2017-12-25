package by.goodsoft.barleybreak.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import by.goodsoft.barleybreak.BarleyBreakApp;
import by.goodsoft.barleybreak.R;
import by.goodsoft.barleybreak.adapters.FieldPagerAdapter;
import by.goodsoft.barleybreak.callbacks.SimpleCallback;
import by.goodsoft.barleybreak.fragments.FieldFragment;
import by.goodsoft.barleybreak.utils.AnimationUtils;

/**
 * Created by Aleksandr Shvets
 * on 01.12.2017.
 */

public class MainActivity extends FragmentActivity {

    public static final float SCALE_INDICATOR = 0.8f;

    private ViewPager fieldPager;
    private FieldPagerAdapter pagerAdapter;
    private TextView startGameView;
    private TextView[] indicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initPager();
    }

    private void initViews() {
        startGameView = findViewById(R.id.am_start_game);
        startGameView.setTypeface(Typeface.createFromAsset(getAssets(), "hind_siliguri_light.ttf"));
        startGameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimationUtils.animateMainScreen(findViewById(R.id.am_top_container), fieldPager,
                        startGameView, false, !BarleyBreakApp.getInstance().isActivityStarted(), new SimpleCallback() {
                            @Override
                            public void onCallback() {
                                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                                intent.putExtra(GameActivity.B_RANK,
                                        ((FieldFragment) pagerAdapter.getItem(fieldPager.getCurrentItem())).getRank());
                                startActivity(intent);
                            }
                        });
            }
        });
    }

    private void initPager() {
        indicators = new TextView[]{findViewById(R.id.am_indicator_8), findViewById(R.id.am_indicator_15), findViewById(R.id.am_indicator_24)};
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setTypeface(Typeface.createFromAsset(getAssets(), "hind_siliguri_light.ttf"));
            indicators[i].setScaleX(SCALE_INDICATOR);
            indicators[i].setScaleY(SCALE_INDICATOR);
            indicators[i].setTag(i);
            indicators[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fieldPager.setCurrentItem((int) view.getTag());
                }
            });
        }

        pagerAdapter = new FieldPagerAdapter(getSupportFragmentManager());
        fieldPager = findViewById(R.id.am_pager);
        fieldPager.setAdapter(pagerAdapter);
        fieldPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                pagerAdapter.animateOffset(position, positionOffsetPixels);
                indicators[position].setScaleX(SCALE_INDICATOR + (1 - SCALE_INDICATOR) * (1 - positionOffset));
                indicators[position].setScaleY(SCALE_INDICATOR + (1 - SCALE_INDICATOR) * (1 - positionOffset));
                if (position + 1 < indicators.length) {
                    indicators[position + 1].setScaleX(SCALE_INDICATOR + (1 - SCALE_INDICATOR) * positionOffset);
                    indicators[position + 1].setScaleY(SCALE_INDICATOR + (1 - SCALE_INDICATOR) * positionOffset);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnimationUtils.animateMainScreen(findViewById(R.id.am_top_container), fieldPager,
                startGameView, true, !BarleyBreakApp.getInstance().isActivityStarted(), null);
        BarleyBreakApp.getInstance().setActivityStarted();
    }
}

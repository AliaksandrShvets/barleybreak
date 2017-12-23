package by.goodsoft.barleybreak.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import by.goodsoft.barleybreak.fragments.FieldFragment;
import by.goodsoft.barleybreak.R;
import by.goodsoft.barleybreak.adapters.FieldPagerAdapter;

/**
 * Created by Aleksandr Shvets
 * on 01.12.2017.
 */

public class MainActivity extends FragmentActivity {

    ViewPager fieldPager;
    FieldPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        ((TextView) findViewById(R.id.am_start_game)).setTypeface(Typeface.createFromAsset(getAssets(), "hind_siliguri_light.ttf"));
        findViewById(R.id.am_start_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra(GameActivity.B_RANK, ((FieldFragment) pagerAdapter.getItem(fieldPager.getCurrentItem())).getRank());
                startActivity(intent);
            }
        });

        pagerAdapter = new FieldPagerAdapter(getSupportFragmentManager());
        fieldPager = findViewById(R.id.am_pager);
        fieldPager.setAdapter(pagerAdapter);
        fieldPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                pagerAdapter.animate(position, positionOffsetPixels);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}

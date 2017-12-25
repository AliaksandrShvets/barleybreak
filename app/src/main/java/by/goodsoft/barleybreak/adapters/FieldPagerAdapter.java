package by.goodsoft.barleybreak.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import by.goodsoft.barleybreak.fragments.FieldFragment;

/**
 * Created by Aleksandr Shvets
 * on 16.12.2017.
 */

public class FieldPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<FieldFragment> fieldFragments = new ArrayList<>();

    public FieldPagerAdapter(FragmentManager fm) {
        super(fm);
        fieldFragments.add(FieldFragment.newInstance(3));
        fieldFragments.add(FieldFragment.newInstance(4));
        fieldFragments.add(FieldFragment.newInstance(5));
    }

    @Override
    public Fragment getItem(int position) {
        return fieldFragments.get(position);
    }

    @Override
    public int getCount() {
        return fieldFragments.size();
    }

    public void animateOffset(int pos, int offset) {
        fieldFragments.get(pos).animateOffsetX(offset, true);
        if (fieldFragments.size() > pos + 1) {
            fieldFragments.get(pos + 1).animateOffsetX(offset, false);
        }
    }
}
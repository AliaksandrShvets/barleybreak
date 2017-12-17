package by.goodsoft.barleybreak;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import static by.goodsoft.barleybreak.managers.ItemsManager.FIELD_SIZE;
import static by.goodsoft.barleybreak.managers.ItemsManager.generateStaticItems;

/**
 * Created by Aleksandr Shvets on 16.12.2017.
 */

public class FieldFragment extends Fragment {

    private static final String B_PAGE_NUMBER = "bRank";

    View view;
    List<View> views;
    DisplayMetrics metrics;
    int rank;

    int fieldSize;
    int fieldLeft;
    int fieldTop;
    int itemSize;

    public static FieldFragment newInstance(int rank) {
        FieldFragment pageFragment = new FieldFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(B_PAGE_NUMBER, rank);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rank = getArguments().getInt(B_PAGE_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initFieldMetrics();
        return getField(inflater);
    }

    private void initFieldMetrics() {
        metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        fieldSize = (int) (metrics.widthPixels * FIELD_SIZE);
        fieldLeft = (metrics.widthPixels - fieldSize) / 2;
        fieldTop = (metrics.heightPixels - metrics.widthPixels) / 2;
        itemSize = fieldSize / rank;
    }

    private View getField(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_field_page, null);
        views = generateStaticItems(inflater, view, rank, itemSize, fieldLeft, fieldTop);
        return view;
    }

    public int getRank() {
        return rank;
    }

    public void setX(int x) {
        for (int i = 0; i < rank; i++) {
            for (int j = 0; j < rank; j++) {
                views.get(i * rank + j).setX(itemSize * j + fieldLeft - x * (1 - (i + j) / (rank * 2f)) + x);
            }
        }
    }
}
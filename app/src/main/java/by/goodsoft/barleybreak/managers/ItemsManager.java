package by.goodsoft.barleybreak.managers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import by.goodsoft.barleybreak.R;
import by.goodsoft.barleybreak.callbacks.FieldActionCallback;
import by.goodsoft.barleybreak.callbacks.ItemActionCallback;
import by.goodsoft.barleybreak.items.Item;
import by.goodsoft.barleybreak.utils.AnimationUtils;
import by.goodsoft.barleybreak.utils.RandomUtils;

/**
 * Created by Aleksandr Shvets
 * on 21.10.2017.
 */

public class ItemsManager {

    private List<Item> items = new ArrayList<>();
    private Item lastItem;
    private FieldActionCallback callback;
    private int rank;
    private int swapCount = 0;

    public ItemsManager(Activity activity, FrameLayout container, int rank, FieldActionCallback callback) {
        this.rank = rank;
        this.callback = callback;
        initItems(activity, container);
    }

    private void initItems(Activity activity, FrameLayout container) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        for (int i = 0; i < rank * rank; i++) {
            lastItem = new Item(
                    activity.getLayoutInflater().inflate(R.layout.item_view, null, false),
                    metrics,
                    rank,
                    i,
                    new ItemActionCallback() {
                        @Override
                        public void onClick(Item item) {
                            swap(item);
                        }
                    });
            container.addView(lastItem.getView());
            items.add(lastItem);
        }
        lastItem.getView().setVisibility(View.INVISIBLE);
        callback.onSwap(0);
        mix();
    }

    private void mix() {
        List<Integer> randomList = RandomUtils.getRandomList(items.size());
        for (int i = 0; i < randomList.size(); i++) {
            items.get(i).setPosition(randomList.get(i));
            if (i < randomList.size() - 1)
                items.get(i).getView().startAnimation(AnimationUtils.getScaleAnimation(items.get(i).getView(), (randomList.get(i) / rank + randomList.get(i) % rank)));
        }
    }

    private void swap(Item swapItem) {
        if (isItemsNearby(lastItem.getXPosition(), swapItem.getXPosition(), lastItem.getYPosition(), swapItem.getYPosition())) {
            int lastPosition = lastItem.getPosition();
            lastItem.setPosition(swapItem.getPosition(), false);
            swapItem.setPosition(lastPosition, true);
            swapCount++;
            callback.onSwap(swapCount);
            checkAllElementsInTheirPositions();
        }
    }

    private boolean isItemsNearby(int x1, int x2, int y1, int y2) {
        return (Math.abs(x1 - x2) + Math.abs(y1 - y2)) == 1;
    }

    private void checkAllElementsInTheirPositions() {
        for (int i = 0; i < items.size(); i++) {
            if (i != items.get(i).getPosition()) {
                return;
            }
        }
        callback.onWin();
    }

    public static List<View> generateStaticItems(LayoutInflater inflater, View view, int rank, int itemSize, int fieldLeft, int fieldTop) {
        List<View> views = new ArrayList<>();
        for (int i = 0; i < rank; i++) {
            for (int j = 0; j < rank; j++) {
                View item = inflater.inflate(R.layout.item_view, null);
                item.findViewById(R.id.iv_container).setBackgroundResource(R.drawable.item_unnamed);
                item.setLayoutParams(new FrameLayout.LayoutParams(itemSize, itemSize));
                item.setX(itemSize * j + fieldLeft);
                item.setY(itemSize * i + fieldTop);
                ((FrameLayout) view).addView(item);
                views.add(item);

            }
        }
        return views;
    }
}

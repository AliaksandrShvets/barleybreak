package by.goodsoft.barleybreak.managers;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import by.goodsoft.barleybreak.R;
import by.goodsoft.barleybreak.activities.GameActivity;
import by.goodsoft.barleybreak.callbacks.ItemActionCallback;
import by.goodsoft.barleybreak.items.Item;
import by.goodsoft.barleybreak.utils.RandomUtils;

/**
 * Created by Aleksandr Shvets on 21.10.2017.
 */

public class ItemsManager {

    private Activity activity;
    private List<Item> items;
    private Item lastItem;
    private int rank;
    private int size;

    public ItemsManager(Activity activity, FrameLayout container, int rank) {
        this.activity = activity;
        this.rank = rank;
        size = activity.getResources().getDisplayMetrics().widthPixels / rank;
        initItems(activity, container);
    }

    private void initItems(Activity activity, FrameLayout container) {
        items = new ArrayList<>();
        for (int i = 0; i < rank * rank; i++) {
            lastItem = new Item(
                    activity.getLayoutInflater().inflate(R.layout.view_item, null, false),
                    size,
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
        mix();
    }

    public void mix() {
        List<Integer> randomList = RandomUtils.getRandomList(items.size());
        for (int i = 0; i < randomList.size(); i++) {
            items.get(i).setPosition(randomList.get(i));
        }
    }

    private void swap(Item swapItem) {
        if (isItemsNearby(lastItem.getXPosition(), swapItem.getXPosition(), lastItem.getYPosition(), swapItem.getYPosition())) {
            Item temp = lastItem.clone();
            lastItem.setPosition(swapItem.getPosition());
            swapItem.setPosition(temp.getPosition());
            if (isAllElementsInTheirPositions()) {
                Toast.makeText(activity, "WIN", Toast.LENGTH_LONG).show();
                activity.finish();
            }
        }
    }

    private boolean isItemsNearby(int x1, int x2, int y1, int y2) {
        return (Math.abs(x1 - x2) + Math.abs(y1 - y2)) == 1;
    }

    private boolean isAllElementsInTheirPositions() {
        for (int i = 0 ; i < items.size(); i++) {
            if (i != items.get(i).getPosition()) {
                return false;
            }
        }
        return true;
    }
}

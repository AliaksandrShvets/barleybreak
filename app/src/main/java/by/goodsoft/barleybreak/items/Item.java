package by.goodsoft.barleybreak.items;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Random;

import by.goodsoft.barleybreak.R;
import by.goodsoft.barleybreak.callbacks.ItemActionCallback;

/**
 * Created by Aleksandr Shvets on 20.10.2017.
 */

public class Item implements Cloneable {

    private static final int itemBgResources[] = {R.drawable.item_1, R.drawable.item_2, R.drawable.item_3, R.drawable.item_4};

    private View view;
    private int size;
    private int rank;
    private int position;
    private int xPosition;
    private int yPosition;
    private ItemActionCallback callback;

    public Item(View view, int size, int rank, int position, ItemActionCallback callback) {
        this.view = view;
        this.size = size;
        this.rank = rank;
        this.callback = callback;
        setPosition(position);
        initView();
    }

    private void initView() {
        FrameLayout.LayoutParams itemParam = new FrameLayout.LayoutParams(size, size);
        view.setLayoutParams(itemParam);
        view.findViewById(R.id.vi_container).setBackgroundResource(itemBgResources[new Random().nextInt(4)]);
        ((TextView) view.findViewById(R.id.vi_text)).setText((position + 1) + "");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onClick(Item.this);
            }
        });
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
        xPosition = position % rank;
        yPosition = position / rank;
        view.setX(xPosition * size);
        view.setY(yPosition * size);
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public View getView() {
        return view;
    }

    @Override
    public Item clone() {
        try {
            return (Item) super.clone();
        } catch (Exception e) {
            return null;
        }
    }
}

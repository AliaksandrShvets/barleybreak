package by.goodsoft.barleybreak.items;

import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import by.goodsoft.barleybreak.R;
import by.goodsoft.barleybreak.callbacks.ItemActionCallback;
import by.goodsoft.barleybreak.utils.AnimationUtils;

import static by.goodsoft.barleybreak.managers.ItemsManager.FIELD_SIZE;

/**
 * Created by Aleksandr Shvets on 20.10.2017.
 */

public class Item implements Cloneable {

    private View view;
    private int itemSize;
    private int fieldLeft;
    private int fieldTop;
    private int rank;
    private int position;
    private int xPosition;
    private int yPosition;
    private DisplayMetrics metrics;
    private ItemActionCallback callback;

    public Item(View view, DisplayMetrics metrics, int rank, int position, ItemActionCallback callback) {
        this.view = view;
        this.rank = rank;
        this.metrics = metrics;
        this.callback = callback;
        int fieldSize = (int) (metrics.widthPixels * FIELD_SIZE);
        fieldLeft = (metrics.widthPixels - fieldSize) / 2;
        fieldTop = (metrics.heightPixels - metrics.widthPixels) / 2;
        itemSize = fieldSize / rank;
        setPosition(position);
        initView();
    }

    private void initView() {
        FrameLayout.LayoutParams itemParam = new FrameLayout.LayoutParams(itemSize, itemSize);
        view.setLayoutParams(itemParam);
        ((TextView) view.findViewById(R.id.iv_text)).setTextSize(itemSize/5);
        ((TextView) view.findViewById(R.id.iv_text)).setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), "hind_siliguri_light.ttf"));
        ((TextView) view.findViewById(R.id.iv_text)).setText((position + 1) + "");
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
        setPosition(position, false);
    }

    public void setPosition(int position, boolean animateMovement) {
        this.position = position;
        xPosition = position % rank;
        yPosition = position / rank;
        if (animateMovement) {
            view.startAnimation(AnimationUtils.getTranslateAnimation(view.getX(), xPosition * itemSize + fieldLeft, view.getY(), yPosition * itemSize + fieldTop));
        }
        view.setX(xPosition * itemSize + fieldLeft);
        view.setY(yPosition * itemSize + fieldTop);
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

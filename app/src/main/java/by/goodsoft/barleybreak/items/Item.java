package by.goodsoft.barleybreak.items;

import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import by.goodsoft.barleybreak.R;
import by.goodsoft.barleybreak.callbacks.ItemActionCallback;
import by.goodsoft.barleybreak.utils.AnimationUtils;

import static by.goodsoft.barleybreak.activities.GameActivity.FIELD_SIZE;

/**
 * Created by Aleksandr Shvets
 * on 20.10.2017.
 */

public class Item {

    public static final String FONT_HIND_SILIGURI_LIGHT_TTF = "hind_siliguri_light.ttf";

    private View view;
    private int rank;
    private int position;
    private ItemActionCallback callback;

    private int itemSize;
    private int fieldLeft;
    private int fieldTop;
    private int xPosition;
    private int yPosition;

    public Item(View view, DisplayMetrics metrics, int rank, int position, ItemActionCallback callback) {
        this.view = view;
        this.rank = rank;
        this.callback = callback;
        int fieldSize = (int) (metrics.widthPixels * FIELD_SIZE);
        itemSize = fieldSize / rank;
        fieldLeft = (metrics.widthPixels - fieldSize) / 2;
        fieldTop = (metrics.heightPixels - metrics.widthPixels) / 2;
        setPosition(position);
        initView();
    }

    private void initView() {
        FrameLayout.LayoutParams itemParam = new FrameLayout.LayoutParams(itemSize, itemSize);
        view.setLayoutParams(itemParam);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onClick(Item.this);
            }
        });
        TextView itemText = view.findViewById(R.id.iv_text);
        itemText.setTextSize(itemSize/5);
        itemText.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), FONT_HIND_SILIGURI_LIGHT_TTF));
        itemText.setText(String.valueOf(position + 1));
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
            view.startAnimation(AnimationUtils.getTranslateAnimation(
                    view.getX(), xPosition * itemSize + fieldLeft,
                    view.getY(), yPosition * itemSize + fieldTop));
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
}

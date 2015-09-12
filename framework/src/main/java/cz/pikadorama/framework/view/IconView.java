package cz.pikadorama.framework.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import cz.pikadorama.framework.R;
import cz.pikadorama.framework.util.Views;

// FIXME: not rounded yet
public class IconView extends RelativeLayout {

    private ImageView image;
    private TextView text;

    public IconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.icon_view, this);
        this.image = Views.require(view, R.id.image);
        this.text = Views.require(view, R.id.text);
    }

    public void setText(String text) {
        this.image.setVisibility(View.INVISIBLE);
        this.text.setVisibility(View.VISIBLE);
        this.text.setText(text.substring(0, 1));
        this.text.setBackgroundColor(getRandomColor());
    }

    public void setImage() {
        this.image.setVisibility(View.VISIBLE);
        this.text.setVisibility(View.INVISIBLE);
    }

    private int getRandomColor() {
        TypedArray colors = getResources().obtainTypedArray(R.array.material_colors_500);
        try {
            int index = (int) (Math.abs(new Random().nextLong()) % colors.length());
            return colors.getColor(index, 0);
        } finally {
            colors.recycle();
        }
    }

}

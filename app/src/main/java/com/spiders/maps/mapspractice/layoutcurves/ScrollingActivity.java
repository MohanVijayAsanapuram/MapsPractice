package com.spiders.maps.mapspractice.layoutcurves;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.spiders.maps.mapspractice.R;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FrameLayout ll=(FrameLayout) findViewById(R.id.frameLayout);

        /*ShapeDrawable done = new ShapeDrawable();
        done.getPaint().setColor(Color.GREEN);

        ShapeDrawable remaining = new ShapeDrawable(new RectShape());
        remaining.getPaint().setColor(Color.RED);

        LayerDrawable composite = new LayerDrawable(new Drawable[]{done, remaining});
        composite.setLayerInset(1, 0, 100, 100, 0);

        ll.setBackgroundDrawable(composite);*/


       /* ArcShape shape = new ArcShape(270, 180);
        ShapeDrawable shapeDrawable = new ShapeDrawable(shape);
        shapeDrawable.getPaint().setColor(getResources().getColor(R.color.colorAccent));
        ll.setBackgroundDrawable(shapeDrawable);*/

        /*layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ll.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ll.setX(-layout.getWidth());
                ll.getLayoutParams().width = layout.getWidth() * 2;
                ll.requestLayout();
            }
        });*/

        ArcShape shape = new ArcShape(0, 180);
        ShapeDrawable shapeDrawable = new ShapeDrawable(shape);
        shapeDrawable.getPaint().setColor(Color.BLACK);
        ll.setBackground(shapeDrawable);

    }
}

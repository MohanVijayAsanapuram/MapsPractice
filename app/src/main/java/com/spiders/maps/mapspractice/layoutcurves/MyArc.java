package com.spiders.maps.mapspractice.layoutcurves;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.shapes.RectShape;

/**
 * Created by user on 21-07-2016.
 */

/**
 * Creates an arc shape. The arc shape starts at a specified
 * angle and sweeps clockwise, drawing slices of pie.
 * The arc can be drawn to a Canvas with its own draw() method,
 * but more graphical control is available if you instead pass
 * the ArcShape to a {@link android.graphics.drawable.ShapeDrawable}.
 */
public class MyArc extends RectShape {

    private float mStart;
    private float mSweep;

    /**
     * ArcShape constructor.
     *
     * @param startAngle the angle (in degrees) where the arc begins
     * @param sweepAngle the sweep angle (in degrees). Anything equal to or
     *                   greater than 360 results in a complete circle/oval.
     */
    public MyArc(float startAngle, float sweepAngle) {
        mStart = startAngle;
        mSweep = sweepAngle;
    }


    @Override
    public void draw(Canvas canvas, Paint paint) {
        super.draw(canvas, paint);
    }
}

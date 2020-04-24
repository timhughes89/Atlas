package com.timsimonhughes.atlas.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.timsimonhughes.atlas.R;

import androidx.appcompat.widget.AppCompatImageView;

public class RoundedImageView extends AppCompatImageView {

    private float cornerRadius = 18;
    private int borderWidth = 24;

    private Path mPath;
    private Paint mBorderPaint;
    private RectF mRectF;

    public RoundedImageView(Context context) {
        super(context);
        init();
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPath = new Path();
        mBorderPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int canvasWidth = getWidth();
        int canvasHeight = getHeight();

        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(borderWidth);
        mBorderPaint.setColor(getResources().getColor(R.color.color_on_surface));

        mRectF = new RectF(0,0, canvasWidth, canvasHeight);
        mPath.addRoundRect(mRectF, cornerRadius, cornerRadius, Path.Direction.CW);
        canvas.clipPath(mPath);
        canvas.drawPath(mPath, mBorderPaint);
    }

}

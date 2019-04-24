package com.timsimonhughes.atlas.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.timsimonhughes.atlas.R;

import androidx.annotation.Nullable;

public class PlanetOrbitView extends View {

    private Path mPath;
    private Paint mPaint;

    public PlanetOrbitView(Context context) {
        super(context);
        init();
    }

    public PlanetOrbitView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlanetOrbitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PlanetOrbitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        mPaint.setStrokeWidth(4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }
}

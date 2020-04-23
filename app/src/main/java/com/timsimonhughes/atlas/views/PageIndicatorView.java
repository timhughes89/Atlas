package com.timsimonhughes.atlas.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.timsimonhughes.atlas.R;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class PageIndicatorView extends View {

    private static final int DEFAULT_CHOSEN_CIRCLE_COLOR = Color.parseColor("#ffffff");
    private static final int DEFAULT_CIRCLE_COLOR = Color.parseColor("#000000");
    private static final int DEFAULT_DISTANCE_BETWEEN_CIRCLES_IN_DP = 5, DEFAULT_CIRCLE_RADIUS_IN_DP = 5, DEFAULT_NUMBER_OF_CIRCLES = 0;

    private Paint inactiveCirclePaint, activeCirclePaint;

    private float circleRadiusInPX;
    private float distanceBetweenCircleInPX;

    private float[] circleLocations;
    private float yLocation;

    private int activeCircleColor;
    private int inactiveCircleColor;
    private int numberOfCircles;

    private int currentIndex = 0;

//    private LifeCycleObserver lifeCycleObserver;

    public PageIndicatorView(Context context) {
        super(context);
        init(context, null);
    }

    public PageIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PageIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(21)
    public PageIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributes) {

        if (attributes != null) {
            TypedArray attributeArray = context.obtainStyledAttributes(attributes, R.styleable.PageIndicatorView);

            circleRadiusInPX =
                    attributeArray.getDimension(
                            R.styleable.PageIndicatorView_circle_radius,
                            dpToPx(context, DEFAULT_CIRCLE_RADIUS_IN_DP));

            distanceBetweenCircleInPX =
                    attributeArray.getDimension(
                            R.styleable.PageIndicatorView_distance_between_circles,
                            dpToPx(context, DEFAULT_DISTANCE_BETWEEN_CIRCLES_IN_DP));

            numberOfCircles =
                    attributeArray.getInt(
                            R.styleable.PageIndicatorView_number_of_circles,
                            DEFAULT_NUMBER_OF_CIRCLES);

            activeCircleColor =
                    attributeArray.getColor(
                            R.styleable.PageIndicatorView_active_circle_color,
                            DEFAULT_CHOSEN_CIRCLE_COLOR);

            inactiveCircleColor =
                    attributeArray.getColor(
                            R.styleable.PageIndicatorView_inactive_circle_color,
                            DEFAULT_CIRCLE_COLOR);

            attributeArray.recycle();

//            activeCircleRadius = circleRadiusInPX * 2;

        } else {

            circleRadiusInPX = dpToPx(context, DEFAULT_CIRCLE_RADIUS_IN_DP);
            distanceBetweenCircleInPX = dpToPx(context, DEFAULT_DISTANCE_BETWEEN_CIRCLES_IN_DP);
            numberOfCircles = DEFAULT_NUMBER_OF_CIRCLES;
            activeCircleColor = DEFAULT_CHOSEN_CIRCLE_COLOR;
            inactiveCircleColor = DEFAULT_CIRCLE_COLOR;
        }

        if (inactiveCirclePaint == null) {
            inactiveCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            inactiveCirclePaint.setColor(inactiveCircleColor);
            inactiveCirclePaint.setStyle(Paint.Style.FILL);
        }

        if (activeCirclePaint == null) {
            activeCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            activeCirclePaint.setColor(activeCircleColor);
            activeCirclePaint.setStyle(Paint.Style.FILL);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (circleLocations != null && circleLocations.length != 0) {
            for (int i = 0; i < numberOfCircles; ++i) {
                if (i == currentIndex) {
                    canvas.drawCircle(circleLocations[i], yLocation, circleRadiusInPX, activeCirclePaint);
                } else {
                    canvas.drawCircle(circleLocations[i], yLocation, circleRadiusInPX, inactiveCirclePaint);
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        boolean isMatchParentFlagOn = MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY;
        measureIndicesOfIndicatorStations(isMatchParentFlagOn);

        int measuredWidth = numberOfCircles > 0 ? measureWidth(widthMeasureSpec) : 0;
        int measuredHeight = numberOfCircles > 0 ? measureHeight(heightMeasureSpec) : 0;

        yLocation = measuredHeight / 2;

        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    private int measureHeight(int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result;
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) (circleRadiusInPX * 2 + getPaddingTop() + getPaddingBottom());
        }

        return result;
    }

    private int measureWidth(int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result;
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) (circleLocations[numberOfCircles-1] + circleRadiusInPX + getPaddingRight());
        }

        return result;
    }

    private void measureIndicesOfIndicatorStations(boolean shouldAddScreenOffset) {
        if (numberOfCircles > 0 && circleLocations == null) {
            circleLocations = new float[numberOfCircles];

            float xCounter = getPaddingLeft() + circleRadiusInPX;
            float spaceBetweenCircles = circleRadiusInPX * 2 + distanceBetweenCircleInPX;

            for (int index = 0; index < numberOfCircles; index++) {
                circleLocations[index] = xCounter;

                xCounter += spaceBetweenCircles;
            }

            if (shouldAddScreenOffset) {
                int screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
                int widthOfView = (int) (circleLocations[numberOfCircles-1] + circleRadiusInPX + getPaddingRight());
                int spaceToAddFromEachSide = (screenWidth - widthOfView) / 2;

                for (int index = 0; index < circleLocations.length; index++) {
                    circleLocations[index] += spaceToAddFromEachSide;
                }
            }
        }
    }

    @Override
    public void requestLayout() {
        circleLocations = null;
        super.requestLayout();
    }

//    @Override
//    protected void onDetachedFromWindow() {
//        if (lifeCycleObserver != null) {
//            lifeCycleObserver.onDetached();
//            lifeCycleObserver = null;
//        }
//
//        super.onDetachedFromWindow();
//    }

    private static float dpToPx(final Context context, final float value) {
        return value * context.getResources().getDisplayMetrics().density;
    }

    public void setCurrentIndex(int index) {
        this.currentIndex = index;
        invalidate();
    }

    int getNumberOfCircles() {
        return numberOfCircles;
    }

    public void setNumberOfCircles(int numberOfCircles) {
        this.numberOfCircles = numberOfCircles;
    }

    public void setChosenCircleColor(@NonNull Context context, @ColorRes int color) {
        this.activeCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.activeCirclePaint.setColor(ContextCompat.getColor(context, color));
        this.activeCirclePaint.setStyle(Paint.Style.FILL);
    }

    public void setActiveCircleColor() {
        // TODO: Logic to add active circle color
    }

    public void setInactiveCircleColor() {
        // TODO: Logic to add inactive circle color
    }

    public void setDefaultCircleColor(@NonNull Context context, @ColorRes int color) {
        this.inactiveCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.inactiveCirclePaint.setColor(ContextCompat.getColor(context, color));
        this.inactiveCirclePaint.setStyle(Paint.Style.FILL);
    }

    public void setCircleRadiusInDp(@NonNull Context context, float circleRadiusInDp) {
        this.circleRadiusInPX = dpToPx(context, circleRadiusInDp);
    }

    public void setDistanceBetweenCircleInDp(@NonNull Context context, float distanceBetweenCircleInPX) {
        this.distanceBetweenCircleInPX = dpToPx(context, distanceBetweenCircleInPX);
    }

    public void setupWithViewpager(ViewPager viewPager) {
        this.currentIndex = viewPager.getCurrentItem();
    }
}

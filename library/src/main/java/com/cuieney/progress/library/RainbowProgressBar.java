package com.cuieney.progress.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuieney on 17/2/21.
 */

public class RainbowProgressBar extends View {

    private float progress;
    private Paint paint;
    private Paint rainbowPaint;


    private int[] colors;
    private LinearGradient shader;

    private float measuredWidth;
    private float measuredHeight;


    private float progressHeight = dp2px(5);
    private int startColor = Color.parseColor("#00ff00");
    private int endColor = Color.parseColor("#0000ff");
    private int unreachedColor = Color.parseColor("#cccccc");
    private int max = 100;
    private float radius = dp2px(35);
    private int type;

    private int[] colorsRainbow;
    private int[] colorsRainbow1;
    private LinearGradient firstShader;
    private LinearGradient secondShader;
    private Paint firstPaint;
    private Paint secondPaint;
    private Matrix firstMatrix;
    private Matrix secondMatrix;
    private int mViewWidth = 0;
    private int mTranslate = 0;
    private boolean stopRainbow;

    private static final int CIRCLE_TYPE = 0;
    private static final int LINE_TYPE = 1;
    private static final int RAINBOW_TYPE = 2;

    public RainbowProgressBar(Context context) {
        super(context);
        init();
    }

    public RainbowProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.obtainStyledAttributes(attrs,
                R.styleable.RainbowProgressBar);
        max = attributes.getInteger(R.styleable.RainbowProgressBar_progress_max, 100);
        progress = attributes.getInteger(R.styleable.RainbowProgressBar_progress_current, 0);
        startColor = attributes.getColor(R.styleable.RainbowProgressBar_progress_start_color, startColor);
        endColor = attributes.getColor(R.styleable.RainbowProgressBar_progress_end_color, endColor);
        radius = attributes.getDimension(R.styleable.RainbowProgressBar_progress_radius, dp2px(35));
        progressHeight = attributes.getDimension(R.styleable.RainbowProgressBar_progress_height, dp2px(5));
        unreachedColor = attributes.getColor(R.styleable.RainbowProgressBar_progress_unreached_color, unreachedColor);
        type = attributes.getInteger(R.styleable.RainbowProgressBar_progress_type, 1);

        attributes.recycle();
        init();
    }

    public RainbowProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);

        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(unreachedColor);
        paint.setStrokeWidth(progressHeight);
        paint.setStrokeCap(Paint.Cap.ROUND);

        rainbowPaint = new Paint();
        rainbowPaint.setAntiAlias(true);

        rainbowPaint.setStyle(Paint.Style.STROKE);
        rainbowPaint.setAntiAlias(true);
        rainbowPaint.setColor(startColor);
        rainbowPaint.setStrokeWidth(progressHeight);
        rainbowPaint.setStrokeCap(Paint.Cap.ROUND);

        colors = new int[max];
        initColors();



    }

    @Override
    protected int getSuggestedMinimumWidth() {
        int width = 0;
        if (type == CIRCLE_TYPE) {
            width = ((int) (radius * 2));
        } else if (type == LINE_TYPE) {
            width = getWidth();
        } else if(type == RAINBOW_TYPE){
            width = getWidth();
        }
        return width;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getWidth();
            if (mViewWidth > 0) {
                firstPaint = getPaint();
                firstShader = new LinearGradient(0, 0, getMeasuredWidth(), getHeight(),
                        colorsRainbow,
                        null, Shader.TileMode.CLAMP);
                firstPaint.setShader(firstShader);
                firstMatrix = new Matrix();

                secondPaint = getPaint();
                secondShader = new LinearGradient(-getMeasuredWidth(), 0, 0, getHeight(),
                        colorsRainbow,
                        null, Shader.TileMode.CLAMP);
                secondPaint.setShader(secondShader);
                secondMatrix = new Matrix();
            }
        }
    }



    public Paint getPaint() {
        Paint rainbowPaint = new Paint();
        rainbowPaint = new Paint();
        rainbowPaint.setAntiAlias(true);

        rainbowPaint.setStyle(Paint.Style.FILL);
        rainbowPaint.setAntiAlias(true);
        rainbowPaint.setColor(Color.parseColor("#ff0000"));
        rainbowPaint.setStrokeWidth(10);
        rainbowPaint.setStrokeCap(Paint.Cap.ROUND);


        return rainbowPaint;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        int height = 0;
        if (type == CIRCLE_TYPE) {
            height = ((int) (radius * 2));
        } else if (type == LINE_TYPE) {
            height = getHeight();
        } else if(type == RAINBOW_TYPE){
            height = getHeight();
        }
        return height;
    }

    private int measure(int measureSpec, boolean isWidth) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size);
                } else {
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (type == CIRCLE_TYPE) {
            drawCircle(canvas);
        } else if (type == LINE_TYPE) {
            drawLine(canvas);
        } else  if(type == RAINBOW_TYPE){
            if (!stopRainbow) {
                drawRainbow(canvas);
            }
        }
    }

    public void setStopRainbow(boolean stop){
        this.stopRainbow = stop;
    }

    private void drawRainbow(Canvas canvas){
        mTranslate += mViewWidth/20;
        if (mTranslate >= mViewWidth) {
            mTranslate = 0;
        }
        firstMatrix.setTranslate(mTranslate, 0);
        firstShader.setLocalMatrix(firstMatrix);
        secondMatrix.setTranslate(mTranslate, 0);
        secondShader.setLocalMatrix(secondMatrix);

        canvas.drawRect(new RectF(0,0,getWidth(),getHeight()), firstPaint);
        canvas.drawRect(new RectF(0,0,mTranslate,getHeight()), secondPaint);
        postInvalidateDelayed(20);
    }


    private void drawLine(Canvas canvas) {
        measuredWidth = getWidth();
        measuredHeight = getHeight();
        canvas.drawLine(0, 0, measuredWidth, 0, paint);
        float x1 = measuredWidth / max * progress;
        shader = new LinearGradient(0, 0, measuredWidth, 0, colors, null,
                Shader.TileMode.MIRROR);
        rainbowPaint.setShader(shader);
        canvas.drawLine(0, 0, x1, 0, rainbowPaint);
    }

    private void drawCircle(Canvas canvas) {
        measuredWidth = getWidth();
        measuredHeight = getHeight();
        float x = radius + progressHeight;
        float y = radius + progressHeight;
        float sweepAngle = 360f / max * progress;

        canvas.drawCircle(x, y, radius, paint);
        shader = new LinearGradient(x - radius, y - radius,
                x - radius + radius, y + radius, colors, null, Shader.TileMode.MIRROR);
        rainbowPaint.setShader(shader);
        RectF rect = new RectF(((int) (x - radius)),
                ((int) (y - radius)),
                ((int) (x + radius)),
                ((int) (y + radius)));
        canvas.drawArc(
                rect,
                -90, sweepAngle, false, rainbowPaint
        );
    }

    public void setProgress(int currentProgress) {
        this.progress = currentProgress;
        postInvalidate();

    }

    public void setMax(int max) {
        this.max = max;
    }

    private void initColors() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            int currentColor = getCurrentColor(i);
            colors[i] = currentColor;
            list.add(currentColor);
        }
        int size = colorsMap.size();
        colorsMap.put(size, list);
        index++;

        colorsRainbow = new int[4];
        colorsRainbow[0] = startColor;
        colorsRainbow[1] = endColor;
        colorsRainbow[2] = endColor;
        colorsRainbow[3] = startColor;


        colorsRainbow1 = new int[colorsRainbow.length];
        for (int i = 0; i < colorsRainbow.length; i++) {
            colorsRainbow1[i] = colorsRainbow[colorsRainbow.length-1-i];
        }


    }

    private int index;
    private Map<Integer, List<Integer>> colorsMap = new HashMap<>();

    private int[] list2Array(List<Integer> integers) {
        int[] color = new int[integers.size()];
        for (int i = 0; i < integers.size(); i++) {
            color[i] = integers.get(i);
        }
        return color;
    }

    private int getCurrentColor(int progress) {
        int alpha = 0;
        int red = 0;
        int green = 0;
        int blue = 0;

        if (progress == 0) {
            return startColor;
        }
        int[] startARGB = convertColor(startColor);
        int[] endARGB = convertColor(endColor);

        alpha = startARGB[0] - ((startARGB[0] - endARGB[0]) / max * progress);
        red = startARGB[1] - ((startARGB[1] - endARGB[1]) / max * progress);
        green = startARGB[2] - ((startARGB[2] - endARGB[2]) / max * progress);
        blue = startARGB[3] - ((startARGB[3] - endARGB[3]) / max * progress);

        return Color.argb(alpha, red, green, blue);
    }

    private int[] convertColor(int color) {
        int alpha = (color & 0xff000000) >>> 24;
        int red = (color & 0x00ff0000) >> 16;
        int green = (color & 0x0000ff00) >> 8;
        int blue = (color & 0x000000ff);
        return new int[]{alpha, red, green, blue};
    }

    private int dp2px(int value) {
        float v = getContext().getResources().getDisplayMetrics().density;
        return (int) (v * value + 0.5f);
    }

    private int sp2px(int value) {
        float v = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (v * value + 0.5f);
    }
}

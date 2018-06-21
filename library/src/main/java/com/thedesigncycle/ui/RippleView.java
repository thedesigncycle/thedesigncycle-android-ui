package com.thedesigncycle.ui;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

public class RippleView extends View {

    private float initialSize;
    private int color1, color2, solidColor, duration_ms, count, width, height, maxRadius, initialRadius, radiusInterval, cx, cy;


    private Paint paint;
    private List<RippleCircle> circles;
    private ValueAnimator sizeAnimator;


    private int gravity;
    private boolean useGradient;

    public RippleView(Context context) {
        super(context);
        gravity = Gravity.CENTER;
        useGradient = false;
        solidColor = Color.parseColor("#E54B4B");
        initialSize = 0.5f;
        count = 5;
        duration_ms = 1000;
    }

    public RippleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.RippleView,
                0, 0);


        try {
            gravity = a.getInt(R.styleable.RippleView_gravity, Gravity.CENTER);
            useGradient = a.getBoolean(R.styleable.RippleView_colorMode, false);
            color1 = a.getColor(R.styleable.RippleView_gradientColor1, Color.parseColor("#E54B4B"));
            color2 = a.getColor(R.styleable.RippleView_gradientColor2, Color.parseColor("#F97F23"));
            solidColor = a.getColor(R.styleable.RippleView_solidColor, color1);
            initialSize = a.getFloat(R.styleable.RippleView_smallCircleScale, 0.5f);
            count = a.getInt(R.styleable.RippleView_rippleCount, 5);
            duration_ms = a.getInt(R.styleable.RippleView_rippleDuration, 1000);
        } finally {
            a.recycle();
        }

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        circles = new ArrayList<RippleCircle>();


    }


    private void startAnimation() {

        radiusInterval = (int) Math.ceil(((float) ((maxRadius - initialRadius)) / count));

        if (sizeAnimator != null && sizeAnimator.isRunning()) {
            sizeAnimator.cancel();
        }

        sizeAnimator = ValueAnimator.ofInt(initialRadius + (radiusInterval * (count - 1)), maxRadius);
        sizeAnimator.setDuration(duration_ms);
        sizeAnimator.setInterpolator(new LinearInterpolator());
        sizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int radius = (int) valueAnimator.getAnimatedValue();


                if (radius >= maxRadius) {
                    resetAllCircles();
                }

                for (int i = count - 1; i >= 0; i--) {
                    RippleCircle circle = circles.get(i);
                    circle.radius = radius - (radiusInterval * i);
                    float alpha = 1 - (float) (circle.radius - initialRadius) / (maxRadius - initialRadius);
                    circle.setAlpha(alpha * 0.8f);

                }
                invalidate();


            }
        });
        sizeAnimator.setRepeatMode(ValueAnimator.RESTART);
        sizeAnimator.setRepeatCount(-1);
        sizeAnimator.start();


    }

    private void resetAllCircles() {
        for (RippleCircle circle : circles) {
            circle.reset();
        }
    }


    private void updateParameters() {
        if (width == 0 || height == 0) {
            return;
        }

        setGravity(gravity);
        if (useGradient)
            paint.setShader(new LinearGradient(0, 0, width, height, color1, color2, Shader.TileMode.CLAMP));
        else
            paint.setColor(solidColor);

        maxRadius = width / 2;
        initialRadius = (int) (width * initialSize * 0.5);

        circles.clear();

        for (int i = 1; i <= count; i++) {
            circles.add(new RippleCircle(i * (maxRadius / count)));
        }
        startAnimation();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;
        updateParameters();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        maxRadius = canvas.getWidth() / 2;
        initialRadius = (int) (canvas.getWidth() * initialSize * 0.5);

        for (RippleCircle circle : circles) {
            canvas.drawCircle(cx, cy, circle.radius, circle.paint);
        }

        canvas.drawCircle(cx, cy, initialRadius, paint);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        sizeAnimator.cancel();
    }

    public void setGradient(int color1, int color2) {
        this.color1 = color1;
        this.color2 = color2;
        useGradient = true;
        updateParameters();
    }

    public void setSolidColor(int color) {
        this.solidColor = color;
        useGradient = false;
        updateParameters();
    }

    public void setSmallCircleScale(float scale) {
        this.initialSize = scale;
        updateParameters();
    }

    public void setRippleDuration(int duration_ms) {
        this.duration_ms = duration_ms;
        updateParameters();
    }

    public void setRippleCount(int count) {
        this.count = count;
        updateParameters();
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;

        switch (gravity) {

            case Gravity.RIGHT:
            case Gravity.RIGHT | Gravity.TOP:
            case Gravity.RIGHT | Gravity.BOTTOM:
                cx = width;
                break;

            case Gravity.LEFT:
            case Gravity.LEFT | Gravity.TOP:
            case Gravity.LEFT | Gravity.BOTTOM:
                cx = 0;
                break;


            default:
                cx = width / 2;
                break;

        }

        switch (gravity) {
            case Gravity.BOTTOM:
            case Gravity.BOTTOM | Gravity.LEFT:
            case Gravity.BOTTOM | Gravity.RIGHT:
                cy = height;
                break;

            case Gravity.TOP:
            case Gravity.TOP | Gravity.LEFT:
            case Gravity.TOP | Gravity.RIGHT:
                cy = 0;
                break;

            default:
                cy = height / 2;
                break;


        }

    }

    private class RippleCircle {
        private int radius, startRadius;
        private Paint paint;

        RippleCircle(int radius) {
            this.radius = radius;//(int) (width * initialSize * 0.5);
            this.startRadius = radius;
            this.paint = new Paint(RippleView.this.paint);
        }

        void reset() {
            this.radius = startRadius;
            this.paint = new Paint(RippleView.this.paint);
        }

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        public Paint getPaint() {
            return paint;
        }

        public void setPaint(Paint paint) {
            this.paint = new Paint(paint);
        }

        public float getAlpha() {
            return ((float) this.paint.getAlpha()) / 255f;
        }

        public void setAlpha(float alpha) {
            this.paint.setAlpha((int) (alpha * 255));
        }
    }


}

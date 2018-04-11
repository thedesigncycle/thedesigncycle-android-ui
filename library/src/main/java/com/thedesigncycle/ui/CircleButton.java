package com.thedesigncycle.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CircleButton extends AppCompatButton {

    private float RADIUS, SHADOW_BLUR_RADIUS, SHADOW_X, SHADOW_Y;

    private float currentRadius, currentScale,
            currentShadowBlurRadius, currentShadowX,
            currentShadowY;

    private int buttonColor, shadowColor;

    private Paint buttonPaint;

    private Drawable icon;

    private ValueAnimator buttonPressAnimator, buttonReleaseAnimator;

    public CircleButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.CircleButton,
                0, 0);

        try {
            buttonColor = a.getColor(R.styleable.CircleButton_buttonColor, Color.RED);

            icon = a.getDrawable(R.styleable.CircleButton_icon);
            shadowColor = Color.argb(77, Color.red(buttonColor), Color.green(buttonColor), Color.blue(buttonColor));
        } finally {
            a.recycle();
        }

        init();
    }

    public CircleButton(Context context) {
        super(context);
        init();
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
        invalidate();
    }

    public void setButtonColor(int color) {
        buttonColor = color;
        shadowColor = Color.argb(77, Color.red(color), Color.green(color), Color.blue(color));
        invalidate();
    }

    private void init() {

        SHADOW_BLUR_RADIUS = 0.1f;
        SHADOW_X = 0;
        SHADOW_Y = 0.06f;
        currentScale = 1.0f;
        currentShadowBlurRadius = SHADOW_BLUR_RADIUS;
        currentShadowX = SHADOW_X;
        currentShadowY = SHADOW_Y;

        buttonPressAnimator = ValueAnimator.ofFloat(1, 0.9f);
        buttonPressAnimator.setDuration(100);
        buttonPressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                float animatedValue1to0 = (animatedValue - 0.9f) * 10;
                currentRadius = RADIUS * animatedValue;
                currentShadowY = SHADOW_Y * animatedValue1to0;
                currentShadowX = SHADOW_X * animatedValue1to0;
                currentShadowBlurRadius = SHADOW_BLUR_RADIUS * animatedValue1to0;
                computeShadow(buttonPaint);
                currentScale = animatedValue;
                invalidate();
            }
        });


        buttonReleaseAnimator = ValueAnimator.ofFloat(0.9f, 1);
        buttonReleaseAnimator.setDuration(100);
        buttonReleaseAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                float animatedValue0to1 = (animatedValue - 0.9f) * 10;
                currentRadius = RADIUS * animatedValue;
                currentShadowY = SHADOW_Y * animatedValue0to1;
                currentShadowX = SHADOW_X * animatedValue0to1;
                currentShadowBlurRadius = SHADOW_BLUR_RADIUS * animatedValue0to1;
                computeShadow(buttonPaint);
                currentScale = animatedValue;
                invalidate();
            }


        });


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        if (RADIUS == 0) {
            initValues(Math.min(width, height));
        }

        canvas.drawCircle(width / 2, height / 2, currentRadius, buttonPaint);

        if (icon != null) {
            float margin = 0.3f * (1 / currentScale);
            int diameter = Math.min(width, height);
            int deltaX = width > height ? (width - height) / 2 : 0;
            int deltaY = height > width ? (height - width) / 2 : 0;
            icon.setBounds((int) (margin * diameter) + deltaX,
                    (int) (margin * diameter) + deltaY,
                    (int) ((1 - margin) * diameter) + deltaX,
                    (int) ((1 - margin) * diameter) + deltaY);

            icon.draw(canvas);
        }
    }


    private void initValues(float width) {
        RADIUS = (width / 2) * (1f - Math.max(SHADOW_X * 2, SHADOW_Y * 2));
        currentRadius = RADIUS;
        buttonPaint = new Paint();
        buttonPaint.setAntiAlias(true);
        buttonPaint.setStyle(Paint.Style.FILL);
        buttonPaint.setColor(buttonColor);
        setLayerType(LAYER_TYPE_SOFTWARE, buttonPaint);
        computeShadow(buttonPaint);
    }


    private void computeShadow(Paint paint) {
        paint.setShadowLayer(currentRadius * currentShadowBlurRadius,
                currentRadius * currentShadowX,
                currentRadius * currentShadowY, shadowColor);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                buttonPressAnimator.start();

                break;

            case MotionEvent.ACTION_UP:
                buttonReleaseAnimator.start();
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void setBackground(Drawable background) {

    }


}

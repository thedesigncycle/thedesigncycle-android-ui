package com.thedesigncycle.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ToggleButton;

public class CircleToggleButton extends ToggleButton {

    private float RADIUS, SHADOW_BLUR_RADIUS, SHADOW_X, SHADOW_Y;

    private float currentRadius, currentScale,
            currentShadowBlurRadius, currentShadowX,
            currentShadowY;

    private int buttonColor, shadowColor, buttonColorChecked, shadowColorChecked, currentShadowColor;

    private Paint buttonPaint;

    private Drawable icon, iconChecked;

    private ValueAnimator buttonPressAnimator, buttonReleaseAnimator;

    public CircleToggleButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.CircleButtonToggle,
                0, 0);

        try {
            if (a.hasValue(R.styleable.CircleButtonToggle_buttonColorUnchecked)) {
                buttonColor = a.getColor(R.styleable.CircleButtonToggle_buttonColorUnchecked, Color.RED);
                shadowColor = Color.argb(77, Color.red(buttonColor), Color.green(buttonColor), Color.blue(buttonColor));
            }
            if (a.hasValue(R.styleable.CircleButtonToggle_iconUnchecked)) {
                icon = a.getDrawable(R.styleable.CircleButtonToggle_iconUnchecked);
            }
            if (a.hasValue(R.styleable.CircleButtonToggle_buttonColorChecked)) {
                buttonColorChecked = a.getColor(R.styleable.CircleButtonToggle_buttonColorChecked, Color.GREEN);
                shadowColorChecked = Color.argb(77, Color.red(buttonColorChecked), Color.green(buttonColorChecked), Color.blue(buttonColorChecked));
            }
            if (a.hasValue(R.styleable.CircleButtonToggle_iconChecked)) {
                iconChecked = a.getDrawable(R.styleable.CircleButtonToggle_iconChecked);
            }

        } finally {
            a.recycle();
        }

        init();
    }

    public CircleToggleButton(Context context) {
        super(context);
        init();
    }

    private void init() {

        SHADOW_BLUR_RADIUS = 0.1f;
        SHADOW_X = 0;
        SHADOW_Y = 0.06f;
        currentScale = 1.0f;
        currentShadowBlurRadius = SHADOW_BLUR_RADIUS;
        currentShadowX = SHADOW_X;
        currentShadowY = SHADOW_Y;
        currentShadowColor = isChecked() ? shadowColorChecked : shadowColor;

        buttonPressAnimator = ValueAnimator.ofFloat(1, 0.9f);
        buttonPressAnimator.setDuration(100);
        buttonPressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                float animatedValue1to0 = (animatedValue - 0.9f) * 10;
                setAlpha(((animatedValue - 0.9f) * 4) + 0.6f);
                currentRadius = RADIUS * animatedValue;
                currentShadowY = SHADOW_Y * animatedValue1to0;
                currentShadowX = SHADOW_X * animatedValue1to0;
                currentShadowBlurRadius = SHADOW_BLUR_RADIUS * animatedValue1to0;
                computeShadow(buttonPaint);
                currentScale = animatedValue;
                if (isChecked()) {
                    buttonPaint.setColor(buttonColorChecked);
                    currentShadowColor = shadowColorChecked;
                    computeShadow(buttonPaint);
                } else {
                    buttonPaint.setColor(buttonColor);
                    currentShadowColor = shadowColor;
                    computeShadow(buttonPaint);
                }
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
                setAlpha(((animatedValue - 0.9f) * 4) + 0.6f);
                currentRadius = RADIUS * animatedValue;
                currentShadowY = SHADOW_Y * animatedValue0to1;
                currentShadowX = SHADOW_X * animatedValue0to1;
                currentShadowBlurRadius = SHADOW_BLUR_RADIUS * animatedValue0to1;
                computeShadow(buttonPaint);
                currentScale = animatedValue;
                if (isChecked()) {
                    buttonPaint.setColor(buttonColorChecked);
                    currentShadowColor = shadowColorChecked;
                    computeShadow(buttonPaint);
                } else {
                    buttonPaint.setColor(buttonColor);
                    currentShadowColor = shadowColor;
                    computeShadow(buttonPaint);
                }
                invalidate();
            }


        });

    }

    private void computeShadow(Paint paint) {
        paint.setShadowLayer(currentRadius * currentShadowBlurRadius,
                currentRadius * currentShadowX,
                currentRadius * currentShadowY, currentShadowColor);

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

        if (iconChecked != null && isChecked()) {
            iconChecked.setBounds(getIconBounds(width, height));
            iconChecked.draw(canvas);
        }

        if (icon != null && !isChecked()) {
            icon.setBounds(getIconBounds(width, height));
            icon.draw(canvas);
        }
    }

    private Rect getIconBounds(int width, int height) {
        float margin = 0.3f * (1 / currentScale);
        int diameter = Math.min(width, height);
        int deltaX = width > height ? (width - height) / 2 : 0;
        int deltaY = height > width ? (height - width) / 2 : 0;
        return new Rect((int) (margin * diameter) + deltaX,
                (int) (margin * diameter) + deltaY,
                (int) ((1 - margin) * diameter) + deltaX,
                (int) ((1 - margin) * diameter) + deltaY);
    }


    private void initValues(float width) {
        RADIUS = (width / 2) * (1f - Math.max(SHADOW_X * 2, SHADOW_Y * 2));
        currentRadius = RADIUS;
        buttonPaint = new Paint();
        buttonPaint.setAntiAlias(true);
        buttonPaint.setStyle(Paint.Style.FILL);
        buttonPaint.setColor(isChecked() ? buttonColorChecked : buttonColor);
        setLayerType(LAYER_TYPE_SOFTWARE, buttonPaint);
        computeShadow(buttonPaint);
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


    public void setIconUnchecked(Drawable icon) {
        this.icon = icon;
        invalidate();
    }

    public void setButtonColorUnchecked(int color) {
        this.buttonColor = color;
        this.shadowColor = Color.argb(77, Color.red(color), Color.green(color), Color.blue(color));
        invalidate();
    }

    public void setIconChecked(Drawable icon) {
        this.iconChecked = icon;
        invalidate();
    }

    public void setButtonColorChecked(int color) {
        this.buttonColorChecked = color;
        this.shadowColorChecked = Color.argb(77, Color.red(color), Color.green(color), Color.blue(color));
        invalidate();
    }


    @Override
    public void setBackground(Drawable background) {

    }

}

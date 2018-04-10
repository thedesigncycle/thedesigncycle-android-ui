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

    private float baseRadius, currentRadius, scale;
    private int buttonColor, shadowColor;

    private float shadowBlurRadius, shadowX, shadowY;
    private float shadowBlurRadiusDynamic, shadowXDynamic, shadowYDynamic;
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
            buttonColor = a.getColor(R.styleable.CircleButton_buttonColor, Color.BLACK);

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

        shadowBlurRadius = 0.1f;
        shadowX = 0;
        shadowY = 0.06f;
        scale = 1.0f;
        shadowBlurRadiusDynamic = shadowBlurRadius;
        shadowXDynamic = shadowX;
        shadowYDynamic = shadowY;

        buttonPressAnimator = ValueAnimator.ofFloat(1, 0.9f);
        buttonPressAnimator.setDuration(100);
        buttonPressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                float animatedValue1to0 = (animatedValue - 0.9f) * 10;
                currentRadius = baseRadius * animatedValue;
                shadowYDynamic = shadowY * animatedValue1to0;
                shadowXDynamic = shadowX * animatedValue1to0;
                shadowBlurRadiusDynamic = shadowBlurRadius * animatedValue1to0;
                computeShadow(buttonPaint);
                scale = animatedValue;
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
                currentRadius = baseRadius * animatedValue;
                shadowYDynamic = shadowY * animatedValue0to1;
                shadowXDynamic = shadowX * animatedValue0to1;
                shadowBlurRadiusDynamic = shadowBlurRadius * animatedValue0to1;
                computeShadow(buttonPaint);
                scale = animatedValue;
                invalidate();
            }


        });


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        if (baseRadius == 0) {
            initValues(Math.min(width, height));
        }

        canvas.drawCircle(width / 2, height / 2, currentRadius, buttonPaint);

        if (icon != null) {
            float margin = 0.3f * (1 / scale);
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
        baseRadius = (width / 2) * (1f - Math.max(shadowX * 2, shadowY * 2));
        currentRadius = baseRadius;
        buttonPaint = new Paint();
        buttonPaint.setAntiAlias(true);
        buttonPaint.setStyle(Paint.Style.FILL);
        buttonPaint.setColor(buttonColor);
        setLayerType(LAYER_TYPE_SOFTWARE, buttonPaint);
        computeShadow(buttonPaint);
    }


    private void computeShadow(Paint paint) {
        paint.setShadowLayer(currentRadius * shadowBlurRadiusDynamic,
                currentRadius * shadowXDynamic,
                currentRadius * shadowYDynamic, shadowColor);
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

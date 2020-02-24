package com.example.customprogressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomProgressView extends View {
    private int backgroundColor = Color.GRAY;
    private int progressColor = Color.GREEN;
    private int progressTextColor = Color.RED;
    private int progressTextSize = 25;
    private int rectRound = 5;
    private boolean backgroundIsStroke = false;

    private Paint backgoundPaint;
    private Paint progressPaint;
    private Paint textPaint;

    private int curProgress;

    public void setCurProgress(int curProgress) {
        if(curProgress>maxProgress){
          this.curProgress=maxProgress;
        }else{
            this.curProgress = curProgress;
        }
        invalidate();
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    private int maxProgress;


    public CustomProgressView(Context context) {
        this(context, null);
    }

    public CustomProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressView);
        backgroundColor = typedArray.getColor(R.styleable.CustomProgressView_cpv_background_color, backgroundColor);
        progressColor = typedArray.getColor(R.styleable.CustomProgressView_cpv_progress_color, progressColor);
        progressTextColor = typedArray.getColor(R.styleable.CustomProgressView_cpv_text_color, progressTextColor);
        progressTextSize = typedArray.getDimensionPixelSize(R.styleable.CustomProgressView_cpv_text_size, progressTextSize);
        rectRound = typedArray.getDimensionPixelSize(R.styleable.CustomProgressView_cpv_rect_round, rectRound);
        backgroundIsStroke = typedArray.getBoolean(R.styleable.CustomProgressView_cpv_background_isstroke, backgroundIsStroke);
        typedArray.recycle();
        initBackgroundPaint();
        initProgressPaint();
        initTextPaint();
    }

    private void initBackgroundPaint() {
        backgoundPaint = new Paint();
        backgoundPaint.setAntiAlias(true);
        backgoundPaint.setColor(backgroundColor);
        if (backgroundIsStroke) {
            backgoundPaint.setStyle(Paint.Style.STROKE);
            backgoundPaint.setStrokeWidth(1);
        } else {
            backgoundPaint.setStyle(Paint.Style.FILL);
        }
    }

    private void initProgressPaint() {
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setColor(progressColor);

    }

    private void initTextPaint() {
       textPaint=new Paint();
       textPaint.setAntiAlias(true);
       textPaint.setColor(progressTextColor);
       textPaint.setTextSize(progressTextSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas, backgoundPaint);
        drawProgress(canvas, progressPaint);
        drawText(canvas,textPaint);
    }

    private void drawText(Canvas canvas, Paint textPaint) {
        Rect rect = new Rect();
        String text=curProgress+"%";
        textPaint.getTextBounds(text,0,text.length(),rect);
        int textWidth=rect.width();
        int dx=getWidth()/2-textWidth/2;
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        int dy= (int) ((fontMetrics.bottom-fontMetrics.top)/2-fontMetrics.bottom);
        int baseline=getHeight()/2+dy+10;
        canvas.drawText(text,dx,baseline,textPaint);
    }

    private void drawProgress(Canvas canvas, Paint progressPaint) {
        if (maxProgress == 0) {
            return;
        }
        float progressWidth=(getWidth()-10)*curProgress/maxProgress;
        RectF rectF = new RectF(10, 10,10+progressWidth , getHeight());
        canvas.drawRoundRect(rectF, rectRound, rectRound, progressPaint);
    }

    private void drawBackground(Canvas canvas, Paint backgoundPaint) {
        RectF rectF = new RectF(10, 10, getWidth() - 10, getHeight());
        canvas.drawRoundRect(rectF, rectRound, rectRound, backgoundPaint);
    }
}

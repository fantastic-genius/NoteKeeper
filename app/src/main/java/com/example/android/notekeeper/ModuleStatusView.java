package com.example.android.notekeeper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class ModuleStatusView extends View {
    public static final int EDIT_MODE_MODULE_COUNT = 7;
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;
    private float mSpacing;
    private float mShapeSize;
    private float mOutlineWidth;
    private Rect[] mModuleRectangle;
    private Paint mPaintOutline;
    private int mOutlineColor;
    private Paint mPaintFill;
    private int mFillColor;
    private float mRadius;
    private int mMaxHorizontalModule;


    public boolean[] getModuleStatus() {
        return mModuleStatus;
    }

    public void setModuleStatus(boolean[] moduleStatus) {
        mModuleStatus = moduleStatus;
    }

    private boolean[] mModuleStatus;

    public ModuleStatusView(Context context) {
        super(context);
        init(null, 0);
    }

    public ModuleStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ModuleStatusView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        if(isInEditMode())
            setupEditModuleValues();

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ModuleStatusView, defStyle, 0);

        a.recycle();

        mOutlineWidth = 6f;
        mShapeSize = 144f;
        mSpacing = 30f;
        mRadius = (mShapeSize - mOutlineWidth)/2;

        mOutlineColor = Color.BLACK;
        mPaintOutline = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintOutline.setStyle(Paint.Style.STROKE);
        mPaintOutline.setStrokeWidth(mOutlineWidth);
        mPaintOutline.setColor(mOutlineColor);

        mFillColor = getContext().getResources().getColor(R.color.pluralsight_orange);
        mPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFill.setStyle(Paint.Style.FILL);
        mPaintFill.setColor(mFillColor);
    }

    private void setupEditModuleValues() {
        boolean[] exampleModuleValues = new boolean[EDIT_MODE_MODULE_COUNT];

        int middle = EDIT_MODE_MODULE_COUNT/2;

        for(int i=0; i < middle; i++)
            exampleModuleValues[i] = true;

        setModuleStatus(exampleModuleValues);
    }

    private void setupModuleRectangle(int width) {
        int availableWidth = width - (getPaddingLeft() + getPaddingRight());
        int horizontalModuleThatCanFit = (int) (availableWidth / (mShapeSize + mSpacing));
        int maxHorizontalModule = Math.min(horizontalModuleThatCanFit, mModuleStatus.length);

        mModuleRectangle = new Rect[mModuleStatus.length];
        for (int moduleIndex = 0; moduleIndex < mModuleStatus.length; moduleIndex++){
            int column = moduleIndex % maxHorizontalModule;
            int row = moduleIndex / maxHorizontalModule;
            int x = getPaddingLeft() + (int) (column * (mShapeSize + mSpacing));
            int y = getPaddingTop() + (int) (row * (mShapeSize + mSpacing));
            mModuleRectangle[moduleIndex] = new Rect(x, y, x + (int) mShapeSize, y + (int) mShapeSize);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = 0;
        int desiredHeight = 0;

        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int availableWidth = specWidth - (getPaddingLeft() + getPaddingRight());
        int horizontalModuleThatCanFit = (int) (availableWidth/(mShapeSize + mSpacing));
        mMaxHorizontalModule = Math.min(horizontalModuleThatCanFit, mModuleStatus.length);


        desiredWidth = (int) (mMaxHorizontalModule * (mShapeSize + mSpacing) - mSpacing);
        desiredWidth += getPaddingLeft() + getPaddingRight();

        int rows = ((mModuleStatus.length - 1)/mMaxHorizontalModule) + 1;
        desiredHeight = (int) ((rows * (mShapeSize + mSpacing)) - mSpacing) ;
        desiredHeight += getPaddingTop() + getPaddingBottom();

        int width = resolveSizeAndState(desiredWidth, widthMeasureSpec, 0);
        int height = resolveSizeAndState(desiredHeight, heightMeasureSpec, 0);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        setupModuleRectangle(w);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int moduleIndex=0; moduleIndex < mModuleRectangle.length; moduleIndex++){
            float x = mModuleRectangle[moduleIndex].centerX();
            float y = mModuleRectangle[moduleIndex].centerY();

            if(mModuleStatus[moduleIndex])
                canvas.drawCircle(x, y, mRadius, mPaintFill);

            canvas.drawCircle(x, y, mRadius, mPaintOutline);
        }

    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */


}
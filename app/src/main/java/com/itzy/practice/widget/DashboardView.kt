package com.itzy.practice.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import org.jetbrains.anko.dip

class DashboardView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    private var mHeight: Int = 0
    private var mWidth: Int = 0
    private val mDefaultWidth = dip(300)
    private val mDefaultHeight = dip(150)
    private val mOffset = dip(30).toFloat()

    private lateinit var mOuterArcRect: RectF
    private val mOuterArcPaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#EEEEEE")
    }

    private lateinit var mInnerArcRect: RectF
    private val mInnerArcPaint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
    }

    init {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        mWidth = if (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.UNSPECIFIED) {
            MeasureSpec.getSize(widthMeasureSpec)
        } else {
            paddingLeft + mDefaultWidth + paddingRight
        }
        mHeight = if (heightMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.UNSPECIFIED) {
            MeasureSpec.getSize(heightMeasureSpec)
        } else {
            paddingTop + mDefaultHeight + paddingBottom
        }
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initRectF()
    }

    private fun initRectF() {
        mOuterArcRect = RectF().apply {
            left = (mWidth * 0.5f - mOffset) * -1.0f
            top = (mHeight - mOffset) * -1.0f
            right = mWidth * 0.5f - mOffset
            bottom = mHeight - mOffset
        }
        mInnerArcRect = RectF().apply {
            left = mWidth * -0.165f
            top = mHeight * -0.33f
            right = mWidth * 0.165f
            bottom = mHeight * 0.33f
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawOuterArc(canvas)
        drawInnerArc(canvas)
    }

    private fun drawOuterArc(canvas: Canvas) {
        canvas.save()
        canvas.translate(mWidth * 0.5f, mHeight.toFloat())
        canvas.drawArc(mOuterArcRect, 0f, -180f, false, mOuterArcPaint)
        canvas.restore()
    }

    private fun drawInnerArc(canvas: Canvas) {
        canvas.save()
        canvas.translate(mWidth * 0.5f, mHeight.toFloat())
        canvas.drawArc(mInnerArcRect, 0f, -180f, false, mInnerArcPaint)
        canvas.restore()
    }
}
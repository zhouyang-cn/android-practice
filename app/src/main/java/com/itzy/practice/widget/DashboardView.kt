package com.itzy.practice.widget

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp


class DashboardView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    private var mHeight: Int = 0
    private var mWidth: Int = 0
    private val mDefaultWidth = dip(300)
    private val mDefaultHeight = dip(150)

    private var mOuterArcRect = RectF()
    private val mOuterArcPaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#F6F6F6")
    }
    private var mMiddleArcRect = RectF()
    private var mInnerArcRect = RectF()
    private val mInnerArcPaint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
    }

    private val mGradientPaint = Paint()
    private val mScaleLinePaint = Paint()
    private val mOuterDesPaint = Paint()
    private val mScorePaint = Paint()
    private val mScoreUnitPaint = Paint()

    private val mArgbEvaluator = ArgbEvaluator()
    private val mStartColor = Color.parseColor("#3DBC9B")
    private val mMiddleColor = Color.parseColor("#FD8A25")
    private val mEndColor = Color.parseColor("#FF4747")

    private val mDesStr1 = "低迷"
    private val mDesColor1 = Color.parseColor("#3DBC9B")
    private val mDesPoint1 = PointF()

    private val mDesStr2 = "谨慎"
    private val mDesColor2 = Color.parseColor("#85A96D")
    private val mDesPoint2 = PointF()

    private val mDesStr3 = "平稳"
    private val mDesColor3 = Color.parseColor("#FD8A25")
    private val mDesPoint3 = PointF()

    private val mDesStr4 = "乐观"
    private val mDesColor4 = Color.parseColor("#FD6D3F")
    private val mDesPoint4 = PointF()

    private val mDesStr5 = "高涨"
    private val mDesColor5 = Color.parseColor("#FF4747")
    private val mDesPoint5 = PointF()

    private var score = 35

    init {
        val sweepGradient = SweepGradient(
            0f,
            0f,
            intArrayOf(mStartColor, mMiddleColor, mEndColor),
            floatArrayOf(0.5f, 0.75f, 1.0f)
        )
        mGradientPaint.apply {
            isAntiAlias = true
            shader = sweepGradient
            strokeWidth = 7.0f
            style = Paint.Style.STROKE
        }
        mScaleLinePaint.apply {
            isAntiAlias = true
            color = Color.BLACK
            strokeWidth = 5.0f
        }
        mOuterDesPaint.apply {
            isAntiAlias = true
            textSize = sp(10).toFloat()
        }
        mScorePaint.apply {
            isAntiAlias = true
            textSize = sp(38).toFloat()
        }
        mScoreUnitPaint.apply {
            isAntiAlias = true
            textSize = sp(16).toFloat()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        mWidth = if (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.UNSPECIFIED) {
            MeasureSpec.getSize(widthMeasureSpec)
        } else {
            mDefaultWidth
        }
        mHeight = if (heightMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.UNSPECIFIED) {
            MeasureSpec.getSize(heightMeasureSpec)
        } else {
            mDefaultHeight
        }
        if (mWidth != mHeight * 2) {
            mHeight = mWidth / 2
        }
        setMeasuredDimension(mWidth, mHeight)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initRectF()
        initOuterDesText()
    }

    private fun initRectF() {
        mOuterArcRect.apply {
            left = mWidth * -0.4f
            top = mHeight * -0.8f
            right = mWidth * 0.4f
            bottom = mHeight * 0.8f
        }
        mMiddleArcRect.apply {
            left = mWidth * -0.23f
            top = mHeight * -0.46f
            right = mWidth * 0.23f
            bottom = mHeight * 0.46f
        }
        mInnerArcRect.apply {
            left = mWidth * -0.165f
            top = mHeight * -0.33f
            right = mWidth * 0.165f
            bottom = mHeight * 0.33f
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawOuterArc(canvas)
        drawMiddleArc(canvas)
        drawInnerArc(canvas)
        drawScaleLine(canvas)
        drawOuterDesText(canvas)
        drawScoreText(canvas)
    }

    /**
     * 绘制外部灰色半圆
     */
    private fun drawOuterArc(canvas: Canvas) {
        canvas.apply {
            save()
            translate(mWidth * 0.5f, mHeight.toFloat())
            drawArc(mOuterArcRect, 180f, 180f, false, mOuterArcPaint)
            restore()
        }
    }

    /**
     * 绘制中间渐变色弧线
     */
    private fun drawMiddleArc(canvas: Canvas) {
        canvas.apply {
            save()
            translate(mWidth * 0.5f, mHeight.toFloat())
            drawArc(mMiddleArcRect, 180f, 180f, false, mGradientPaint)
            restore()
        }
    }

    /**
     * 绘制内部白色半圆
     */
    private fun drawInnerArc(canvas: Canvas) {
        canvas.apply {
            save()
            translate(mWidth * 0.5f, mHeight.toFloat())
            drawArc(mInnerArcRect, 180f, 180f, false, mInnerArcPaint)
            restore()
        }
    }

    /**
     * 绘制刻度线
     */
    private fun drawScaleLine(canvas: Canvas) {
        val startX = mWidth * 0.3f
        val startX2 = mWidth * 0.285f
        val stopX = mWidth * 0.33f
        val stopX2 = mWidth * 0.355f
        val startY = 0.0f
        val stopY = 0.0f
        canvas.apply {
            save()
            translate(mWidth * 0.5f, mHeight.toFloat())
            rotate(180.0f)
            for (degrees in 0..50) {
                if (degrees < 26) {
                    val color =
                        mArgbEvaluator.evaluate(degrees * 0.04f, mStartColor, mMiddleColor) as Int
                    mScaleLinePaint.color = color
                } else {
                    val color =
                        mArgbEvaluator.evaluate(
                            (degrees - 25) * 0.04f,
                            mMiddleColor,
                            mEndColor
                        ) as Int
                    mScaleLinePaint.color = color
                }

                if (degrees != 0 && degrees != 50 && degrees % 10 == 0) {
                    // 绘制长刻度线
                    drawLine(startX2, startY, stopX2, stopY, mScaleLinePaint)
                } else {
                    // 绘制短刻度线
                    drawLine(startX, startY, stopX, stopY, mScaleLinePaint)
                }
                rotate(3.6f)
            }
            restore()
        }
    }

    private fun drawScoreText(canvas: Canvas) {
        var textColor = if (score < 51) {
            mArgbEvaluator.evaluate(
                score * 0.02f,
                mStartColor,
                mMiddleColor
            ) as Int

        } else {
            mArgbEvaluator.evaluate(
                (score - 50) * 0.02f,
                mMiddleColor,
                mEndColor
            ) as Int
        }
        mScorePaint.color = textColor
        mScoreUnitPaint.color = textColor
        val scoreText = score.toString()
        val scoreWidth = mScorePaint.measureText(scoreText)
        val scoreUnitText = "/分"
        val y = dip(5) * -1.0f
        var xOffset = -0.75f
        if (scoreText.length < 2) {
            xOffset = -0.9f
        }
        val scoreX = scoreWidth * xOffset
        val scoreUnitX = scoreWidth * (1 + xOffset)
        canvas.apply {
            save()
            translate(mWidth * 0.5f, mHeight.toFloat())
            drawText(scoreText, scoreX, y, mScorePaint)
            drawText(scoreUnitText, scoreUnitX, y, mScoreUnitPaint)
            restore()
        }
    }

    private fun initOuterDesText() {
//        数学中是这样求坐标点的
//        圆心坐标：(x0,y0)
//        半径：r
//        角度：a
//        圆周率： PI
//        则圆上任一点为：（x1,y1）
//        x1  =  x0 + r * cos(a * PI/180)
//        y1  =  y0 + r * sin(a * PI/180)

        val radius = mWidth * 0.4f + dip(10)
        val desWidth = mOuterDesPaint.measureText(mDesStr1)
        mDesPoint1.apply {
            x = Math.round(Math.cos(Math.toRadians(195.0)) * radius).toFloat() - desWidth
            y = Math.round(Math.sin(Math.toRadians(195.0)) * radius).toFloat()
        }
        mDesPoint2.apply {
            x = Math.round(Math.cos(Math.toRadians(230.0)) * radius).toFloat() - desWidth
            y = Math.round(Math.sin(Math.toRadians(230.0)) * radius).toFloat()
        }

        mDesPoint3.apply {
            x = Math.round(Math.cos(Math.toRadians(270.0)) * radius).toFloat() - desWidth / 2
            y = Math.round(Math.sin(Math.toRadians(270.0)) * radius).toFloat()
        }

        mDesPoint4.apply {
            x = Math.round(Math.cos(Math.toRadians(310.0)) * radius).toFloat()
            y = Math.round(Math.sin(Math.toRadians(310.0)) * radius).toFloat()
        }

        mDesPoint5.apply {
            x = Math.round(Math.cos(Math.toRadians(345.0)) * radius).toFloat()
            y = Math.round(Math.sin(Math.toRadians(345.0)) * radius).toFloat()
        }

    }

    private fun drawOuterDesText(canvas: Canvas) {
        canvas.apply {
            save()
            translate(mWidth * 0.5f, mHeight.toFloat())
            mOuterDesPaint.color = mDesColor1
            drawText(mDesStr1, mDesPoint1.x, mDesPoint1.y, mOuterDesPaint)

            mOuterDesPaint.color = mDesColor2
            drawText(mDesStr2, mDesPoint2.x, mDesPoint2.y, mOuterDesPaint)

            mOuterDesPaint.color = mDesColor3
            drawText(mDesStr3, mDesPoint3.x, mDesPoint3.y, mOuterDesPaint)

            mOuterDesPaint.color = mDesColor4
            drawText(mDesStr4, mDesPoint4.x, mDesPoint4.y, mOuterDesPaint)

            mOuterDesPaint.color = mDesColor5
            drawText(mDesStr5, mDesPoint5.x, mDesPoint5.y, mOuterDesPaint)
            restore()
        }
    }


}
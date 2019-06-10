package com.itzy.practice.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.animation.addListener
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp

/**
 * 柱状图
 */
class BarChatView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    private var isBarValueShown = false
    private var maxBarData: Int? = 0
    private var highestBarHeight = 0f
        set(value) {
            field = value
            invalidate()
        }
    private var mBarWidth: Float = 0f

    private val greenColor = Color.parseColor("#3DBC9B")
    private val redColor = Color.parseColor("#FF4747")

    private val bottomOffset = dip(5).toFloat()

    private val mGreenTextPaint = Paint().apply {
        isAntiAlias = true
        color = greenColor
        textSize = sp(12f).toFloat()
    }

    private val mGreenBarPaint = Paint().apply {
        isAntiAlias = true
        color = greenColor
    }

    private val mRedTextPaint = Paint().apply {
        isAntiAlias = true
        color = redColor
        textSize = sp(12f).toFloat()
    }

    private val mRedBarPaint = Paint().apply {
        isAntiAlias = true
        color = redColor
    }

    private val mRangTextPaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#999999")
        textSize = sp(8f).toFloat()
    }

    // 底部下跌和上涨文字高度
    private var dropTextHeight = 0f

    private var mDropPath = Path()
    private var mRisePath = Path()
    private var dropWidth = dip(16).toFloat()
        set(value) {
            field = value
            invalidate()
        }
    private var riseWidth = dip(16).toFloat()
        set(value) {
            field = value
            invalidate()
        }

    private var horizontalBarBottomY = 0f
    private var horizontalBarTopY = 0f
    private var mData = listOf(0, 0, 0, 0, 0, 0, 0, 0)

    private var barBottomY = 0f
    private var mRangeTextBottomY = 0f

    private val rangeTextList = listOf(
        "<-10", "-10~-7", "-7~-3", "-3~-0",
        "0~3", "3~7", "7~10", ">10"
    )
    private var total = 0
    private var dropTotal = 0
    private var riseTotal = 0

    init {
        dropTextHeight = mGreenTextPaint.fontMetrics.bottom - mGreenTextPaint.fontMetrics.top
        horizontalBarBottomY = (bottomOffset + dropTextHeight + dip(5)) * -1f
        horizontalBarTopY = horizontalBarBottomY - dip(8)
        mRangeTextBottomY = horizontalBarTopY - dip(15)
        barBottomY = horizontalBarTopY - dip(30)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val height = if (heightMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.UNSPECIFIED) {
            MeasureSpec.getSize(heightMeasureSpec)
        } else {
            dip(200)
        }
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initBar()
    }

    private fun initBar() {
        // 一共8个柱，start和end不要间距，相邻两个柱的间距为15dp
        mBarWidth = (width - 7 * dip(15)) / 8f
        mGreenBarPaint.strokeWidth = mBarWidth
        mRedBarPaint.strokeWidth = mBarWidth

        dropWidth = width / 2f
        riseWidth = width / 2f
        highestBarHeight = height + barBottomY + dip(-30)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        with(canvas) {
            save()
            move2Bottom()
            drawDropAndRiseAmount(this)
            drawHorizontalBar(this)
            drawBar(this)
            restore()
        }
    }

    private fun drawDropAndRiseAmount(canvas: Canvas) {
        if (isBarValueShown) {
            val dropAmount = "下跌: $dropTotal"
            val riseAmount = "上涨: $riseTotal"
            val textWidth = mGreenTextPaint.measureText(riseAmount)
            with(canvas) {
                drawText(dropAmount, 0f, -bottomOffset, mGreenTextPaint)
                drawText(riseAmount, width - textWidth, -bottomOffset, mRedTextPaint)
            }
        }
    }

    /**
     * 绘制下跌和上涨横条
     */
    private fun drawHorizontalBar(canvas: Canvas) {
        mDropPath.apply {
            // 清空上次绘制用的path
            reset()
            moveTo(0f, horizontalBarBottomY)
            lineTo(0f, horizontalBarTopY)
            lineTo(dropWidth, horizontalBarTopY)
            lineTo(dropWidth - dip(8), horizontalBarBottomY)
            close()
        }
        mRisePath.apply {
            // 清空上次绘制用的path
            reset()
            moveTo(width.toFloat(), horizontalBarBottomY)
            lineTo(width.toFloat(), horizontalBarTopY)
            lineTo(width - riseWidth + dip(8), horizontalBarTopY)
            lineTo(width - riseWidth, horizontalBarBottomY)
            close()
        }
        canvas.drawPath(mDropPath, mGreenBarPaint)
        canvas.drawPath(mRisePath, mRedBarPaint)
    }

    private fun drawBar(canvas: Canvas) {
        for ((index, value) in mData.withIndex()) {
            val barX = if (index != 0) {
                (mBarWidth + dip(15)) * index + mBarWidth / 2
            } else {
                mBarWidth / 2
            }
            maxBarData?.let {
                // 绘制柱
                val barY =
                    if (value == 0) {
                        -1.0f + barBottomY
                    } else {
                        -1.0f * highestBarHeight * (value.toFloat() / it) + barBottomY
                    }
                canvas.drawLine(
                    barX,
                    barBottomY,
                    barX,
                    barY,
                    if (index < 4) mGreenBarPaint else mRedBarPaint
                )
                if (isBarValueShown) {
                    // 绘制柱顶部的文字
                    val barTopText = mData[index].toString()
                    val textPaint = if (index < 4) mGreenTextPaint else mRedTextPaint
                    val barTopTextWidth = textPaint.measureText(barTopText)
                    val barTopTextX = barX - barTopTextWidth / 2f
                    canvas.drawText(
                        barTopText,
                        barTopTextX,
                        barY - dip(5),
                        textPaint
                    )
                }
            }
            // 绘制区间文字
            val rangeText = rangeTextList[index]
            val rangeTextWidth = mRangTextPaint.measureText(rangeText)
            val rangeTextX = barX - rangeTextWidth / 2f
            canvas.drawText(rangeText, rangeTextX, mRangeTextBottomY, mRangTextPaint)


        }

    }

    /**
     * 便于计算，将画布移动到View的 “底部” 位置（width/2，height）
     */
    private fun Canvas.move2Bottom() {
        translate(0f, height.toFloat())
    }

    fun setData(data: List<Int>) {
        mData = data
        maxBarData = mData.max()
        total = 0
        dropTotal = 0
        riseTotal = 0
        isBarValueShown = false
        for ((index, value) in mData.withIndex()) {
            total += value
            if (index < 4) {
                dropTotal += value
            } else {
                riseTotal += value
            }
        }
        val animator = ObjectAnimator.ofFloat(
            this,
            "highestBarHeight",
            0f,
            highestBarHeight
        ).setDuration(1200)
        animator.addListener(onEnd = {
            isBarValueShown = true
        })
        animator.start()
        val dropWidth = width * (dropTotal.toFloat() / total)
        val animatorDropBar = ObjectAnimator.ofFloat(
            this,
            "dropWidth",
            width / 2f,
            dropWidth
        ).setDuration(1200)
        animatorDropBar.start()
        val riseWidth = width - dropWidth
        val animatorRiseBar = ObjectAnimator.ofFloat(
            this,
            "riseWidth",
            width / 2f,
            riseWidth
        ).setDuration(1200)
        animatorRiseBar.start()
    }
}
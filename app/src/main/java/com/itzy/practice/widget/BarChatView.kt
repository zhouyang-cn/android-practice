package com.itzy.practice.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
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


    private var barHeight = 0
        set(value) {
            field = value
            invalidate()
        }

    private val greenColor = Color.parseColor("#3DBC9B")
    private val redColor = Color.parseColor("#FF4747")

    private val mGreenTextPaint = Paint().apply {
        isAntiAlias = true
        color = greenColor
        textSize = sp(12f).toFloat()
    }

    private val mGreenBarPaint = Paint().apply {
        isAntiAlias = true
        color = greenColor
        strokeWidth = dip(50).toFloat()

    }

    private val mRedTextPaint = Paint().apply {
        isAntiAlias = true
        color = redColor
        textSize = sp(12f).toFloat()
    }

    private val mRedBarPaint = Paint().apply {
        isAntiAlias = true
        color = redColor
        strokeWidth = dip(1).toFloat()

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.apply {
            save()
            move2Bottom()
            drawBar(this)
            drawDropAndRiseAmount(this)
            restore()
        }
    }

    private fun drawDropAndRiseAmount(canvas: Canvas) {
        val dropAmount = "下跌: 3199"
        val riseAmount = "上涨: 2199"
        val textWidth = mRedTextPaint.measureText(riseAmount)
        canvas.apply {
            drawText(dropAmount, 0f, -10f, mGreenTextPaint)
            drawText(riseAmount, width - textWidth, -10f, mRedTextPaint)
        }
    }

    private fun drawBar(canvas: Canvas) {
        canvas.apply {
            drawLine(0f, 0f, 0f, dip(-1 * barHeight).toFloat(), mGreenBarPaint)
            drawLine(0f, 0f, 0f, dip(-1 * barHeight).toFloat(), mRedBarPaint)
        }
    }

    /**
     * 便于计算，将画布移动到View的 “底部” 位置（width/2，height）
     */
    private fun Canvas.move2Bottom() {
        translate(0f, height.toFloat())
    }

    fun startAnimator(height: Int) {
        val animator = ObjectAnimator.ofInt(this, "barHeight", 0, height)
            .setDuration(1200)
        animator.start()
    }
}
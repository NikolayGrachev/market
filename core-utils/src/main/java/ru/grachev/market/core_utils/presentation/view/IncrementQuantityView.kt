package ru.grachev.market.core_utils.presentation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import dev.grachev.core_resources.R
import ru.grachev.market.core_utils.presentation.toPx

class IncrementQuantityView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0): View(context, attrs, defStyleAttr, defStyleRes) {

    var onQuantityChanged: ((quantity: Int) -> Unit)? = null

    var quantity: Int = 0
        set(value) {
            field = value
            updateView(field)
        }

    private fun updateView(quantity: Int) {
        if (quantity > 0) {
            bodyColor = context.getColor(R.color.white)
            textColor = context.getColor(R.color.black)
            text = "$quantity шт."
        } else {
            bodyColor = context.getColor(R.color.button_default)
            textColor = context.getColor(R.color.white)
            text = context.getString(R.string.to_cart)
        }
        invalidate()
    }

    private var bodyColor: Int = context.getColor(R.color.button_default)
    private var textColor: Int = context.getColor(R.color.white)


    /**
     * Background
     */
    private val paint = Paint()
    private val drawingRect = RectF(clipBounds)

    /**
     * Text
     */
    private val fontPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var text = context.getString(R.string.to_cart)
    private var fontSize = 14.toPx(context)

    /**
     * Buttons +/-
     */
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPlusPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textMinusPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var circleColor: Int = context.getColor(R.color.button_default)
    private var circleTextColor: Int = context.getColor(R.color.white)
    private var radius = 0f
    private var minusX = 0f
    private var minusY = 0f
    private var plusX = 0f
    private var plusY = 0f

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        setWillNotDraw(false)

        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true

        fontPaint.textSize = fontSize.toFloat()
        fontPaint.style = Paint.Style.FILL

        circlePaint.color = circleColor

        isClickable = true
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        drawingRect.left = 0f
        drawingRect.top = 0f
        drawingRect.bottom = height.toFloat()
        drawingRect.right = width.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawButtonBackground()
        canvas.drawButtonText()

        if (quantity > 0) {
            canvas.drawIncDecButtons()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return true

        val isMinus = checkTouchInCircle(event.x, event.y, minusX, minusY, radius.toFloat())
        val isPlus = checkTouchInCircle(event.x, event.y, plusX, plusY, radius.toFloat())

        when (event.action) {
            MotionEvent.ACTION_UP -> {
                if (quantity == 0) {
                    quantity++
                    onQuantityChanged?.invoke(quantity)
                } else if (isMinus) {
                    quantity--
                    onQuantityChanged?.invoke(quantity)
                } else if (isPlus) {
                    quantity++
                    onQuantityChanged?.invoke(quantity)
                }

            }
        }

        return super.onTouchEvent(event)
    }

    private fun Canvas.drawIncDecButtons() {
        radius = height.toFloat()/2 - 10.toPx(context)

        minusX = radius.toFloat() + 20.toPx(context)
        minusY = height.toFloat()/2
        this.drawCircle(
            minusX,
            minusY,
            radius.toFloat(),
            circlePaint)

        textMinusPaint.color = circleTextColor
        textMinusPaint.strokeWidth = radius/8
        this.drawLine(
            minusX - radius/2,
            minusY,
            minusX + radius/2,
            minusY,
            textMinusPaint)

        plusX = width.toFloat() - radius.toFloat() - 20.toPx(context)
        plusY = height.toFloat()/2
        this.drawCircle(
            plusX,
            plusY,
            radius.toFloat(),
            circlePaint)

        this.drawTextInCenter(
            "+",plusX,plusY,circleTextColor,height.toFloat()/2,textPlusPaint)
    }

    private fun Canvas.drawButtonBackground() {
        paint.color = bodyColor
        this.drawRect(drawingRect, paint)
    }

    private fun Canvas.drawTextInCenter(text: String,
                                        xPos: Float,
                                        yPos: Float,
                                        color: Int,
                                        textSize: Float,
                                        paint: Paint) {
        paint.color = color
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = textSize

        val textYPos = yPos - (paint.descent() + paint.ascent()) / 2

        this.drawText(text, xPos, textYPos, paint)
    }

    private fun Canvas.drawButtonText() {
        fontPaint.color = textColor
        fontPaint.textAlign = Paint.Align.CENTER
        fontPaint.textSize = (height / 3).toFloat()

        val xPos = width / 2
        val yPos = (height / 2 - (fontPaint.descent() + fontPaint.ascent()) / 2).toInt()
        this.drawText(text, xPos.toFloat(), yPos.toFloat(), fontPaint)
    }

    private fun checkTouchInCircle(touchX: Float,
                                   touchY: Float,
                                   circleCenterX: Float,
                                   circleCenterY: Float,
                                   circleRadius: Float
                                   ): Boolean {
        return touchX < circleCenterX + circleRadius &&
                touchX > circleCenterX - circleRadius &&
                touchY < circleCenterY + circleRadius &&
                touchY > circleCenterY - circleRadius
    }
}
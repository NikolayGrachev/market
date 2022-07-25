package ru.grachev.market.core_utils.presentation.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import dev.grachev.core_resources.R
import ru.grachev.market.core_utils.getRefreshRate
import ru.grachev.market.core_utils.presentation.toPx


class AddToCartButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0): View(context, attrs, defStyleAttr, defStyleRes) {

    sealed interface State {
        object AddToCart: State
        object Processing: State
        object InCart: State
    }

    var onClick: ((state: State) -> Unit)? = null

    var state: State = State.AddToCart
        set(value) {
            field = value

            when (state) {
                State.AddToCart -> {
                    bodyColorId = R.color.button_default
                    textColorId = R.color.white
                    text = context.getString(R.string.to_cart)
                }
                State.InCart -> {
                    bodyColorId = R.color.white
                    textColorId = R.color.black
                    text = context.getString(R.string.in_cart)
                }
                State.Processing -> {
                    bodyColorId = R.color.white
                    textColorId = R.color.transparent
                    text = ""
                }
            }
            invalidate()
        }

    @ColorRes
    var shadowColorId: Int = R.color.button_default
        set(value) {
            field = value
            _shadowColor = context.getColor(value)
        }
    @ColorRes
    var bodyColorId: Int = R.color.button_default
        set(value) {
            field = value
            _bodyColor = context.getColor(value)
        }

    @ColorRes
    var textColorId: Int = R.color.white
        set(value) {
            field = value
            _textColor = context.getColor(value)
        }

    private var _shadowColor: Int = context.getColor(shadowColorId)
    private var _bodyColor: Int = context.getColor(bodyColorId)
    private var _textColor: Int = context.getColor(textColorId)

    /**
     * Background
     */
    private val paint = Paint()
    private val hasShadow = true
    private val drawingRect = RectF(clipBounds)
    private val cornerRadius = 7.toPx(context)
    private val shadowRadius = 5.toPx(context)

    /**
     * Text
     */
    private val fontPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var text = ""
    var fontSize = 14.toPx(context)

    /**
     * Animation
     */
    private val refreshRate = getRefreshRate(context)
    private val maxFrames = refreshRate * 3/4
    private var frameCounter = 0
    private var direction = 1

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        setWillNotDraw(false)

        _bodyColor = context.getColor(bodyColorId)
        _shadowColor = context.getColor(shadowColorId)

        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true

        drawingRect.left = 0f
        drawingRect.top = 0f

        fontPaint.textSize = fontSize.toFloat()
        fontPaint.style = Paint.Style.FILL

        isClickable = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawButtonBackground()
        canvas.drawButtonText()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        drawingRect.left = shadowRadius.toFloat()
        drawingRect.top = shadowRadius.toFloat()
        drawingRect.bottom = (height - shadowRadius).toFloat()
        drawingRect.right = (width - shadowRadius).toFloat()
    }

    override fun performClick(): Boolean {

        onClick?.invoke(state)

        when (state) {
            State.AddToCart -> {
                state = State.Processing
            }
            State.InCart -> {
                state = State.Processing
            }
            State.Processing -> {

            }
        }

        invalidate()

        return super.performClick()
    }

    // Gradient Shade colors
    private var colors = intArrayOf(
        ContextCompat.getColor(context,
            R.color.button_default),
        ContextCompat.getColor(context,
            R.color.pink),
        ContextCompat.getColor(context,
            R.color.button_default),
        ContextCompat.getColor(context,
            R.color.pink),
        ContextCompat.getColor(context,
            R.color.button_default),
    )

    private fun Canvas.drawButtonBackground() {
        if (hasShadow) {
            paint.setShadowLayer(shadowRadius.toFloat(), 0f, 0f, _shadowColor)
        } else {
            paint.setShadowLayer(0f, 0f, 0f, _shadowColor)
        }

        // processing animation
        if (state == State.Processing) {
            animateProcessingState()
        } else {
            frameCounter = 0
            direction = 1
            paint.shader = null
            paint.color = _bodyColor
        }

        this.drawRoundRect(drawingRect!!, cornerRadius.toFloat(), cornerRadius.toFloat(), paint)
    }

    private fun Canvas.drawButtonText() {
        fontPaint.color = _textColor
        fontPaint.textAlign = Paint.Align.CENTER
        fontPaint.textSize = (height / 2.5).toFloat()

        val xPos = width / 2
        val yPos = (height / 2 - (fontPaint.descent() + fontPaint.ascent()) / 2).toInt()
        this.drawText(text, xPos.toFloat(), yPos.toFloat(), fontPaint)
    }

    private fun animateProcessingState() {
        val df = (frameCounter / maxFrames) * measuredWidth.toFloat()

        if (frameCounter > maxFrames) {
            frameCounter = 0
            direction = 1
        } else if (frameCounter < 0) {
            direction = 1
        }

        frameCounter += direction

        paint.shader = LinearGradient(
            -measuredWidth.toFloat() + df,
            0f,
            measuredWidth.toFloat() + df,
            0f,
            colors,
            null,
            Shader.TileMode.CLAMP)

        invalidate()
    }
}
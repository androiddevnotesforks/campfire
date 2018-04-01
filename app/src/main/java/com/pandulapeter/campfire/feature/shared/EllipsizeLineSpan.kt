package com.pandulapeter.campfire.feature.shared

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.ColorInt
import android.text.style.LineBackgroundSpan
import android.text.style.ReplacementSpan
import kotlin.math.ceil

/**
 * Ellipsizes individual lines of a text.
 */
class EllipsizeLineSpan(@ColorInt private val color: Int? = null) : ReplacementSpan(), LineBackgroundSpan {
    private var layoutLeft = 0
    private var layoutRight = 0

    override fun drawBackground(canvas: Canvas, paint: Paint, left: Int, right: Int, top: Int, baseline: Int, bottom: Int, text: CharSequence, start: Int, end: Int, lnum: Int) {
        val clipRect = Rect()
        canvas.getClipBounds(clipRect)
        layoutLeft = clipRect.left
        layoutRight = clipRect.right
    }

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fontMetricsInt: Paint.FontMetricsInt?): Int {
        fontMetricsInt?.let {
            it.ascent = paint.getFontMetricsInt(it)
            it.leading = paint.getFontMetricsInt(it)
        }
        return Math.round(paint.measureText(text, start, start))
    }

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        color?.let { paint.color = it }
        val textWidth = paint.measureText(text, start, end)
        if (x + ceil(textWidth) < layoutRight) {
            canvas.drawText(text, start, end, x, y.toFloat(), paint)
        } else {
            val ellipsizeWidth = paint.measureText(ELLIPSIZE_CHARACTER)
            val newEnd = start + paint.breakText(text, start, end, true, layoutRight - x - ellipsizeWidth, null)
            canvas.drawText(text, start, newEnd, x, y.toFloat(), paint)
            canvas.drawText(ELLIPSIZE_CHARACTER, x + paint.measureText(text, start, newEnd), y.toFloat(), paint)
        }
    }

    companion object {
        private const val ELLIPSIZE_CHARACTER = "\u2026"
    }
}
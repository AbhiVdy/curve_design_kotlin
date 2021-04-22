package com.core.arcassignment.views.components

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.core.arcassignment.R
import com.core.arcassignment.network.models.ReportModel
import com.core.arcassignment.utils.Utils

class ArcView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    companion object {
        const val ARC_FULL_ROTATION_DEGREE = 360

        const val ARCHIVE_VAL = "archiveValHolder"
        const val PROCESS_VAL = "inProgressVal"
        const val PENDING_VAL = "pendingVal"
    }

    private var archivePercentage = 0
    private var progressPercentage = 0
    private var pendingPercentage = 0
    private var totalPercentage = 100

    private val archivedView = RectF()
    private val progressView = RectF()
    private val pendingView = RectF()

    fun setChart(respObj: ReportModel) {
        val archiveVal = respObj.archived.toFloat()
        val inProgressVal = respObj.inprogress.toFloat()
        val pendingVal = respObj.pending.toFloat()

        totalPercentage = respObj.total
        val archiveValuesHolder = PropertyValuesHolder.ofFloat(ARCHIVE_VAL, 0f, archiveVal)
        val progressValuesHolder = PropertyValuesHolder.ofFloat(PROCESS_VAL, 0f, inProgressVal)
        val pendingValuesHolder = PropertyValuesHolder.ofFloat(PENDING_VAL, 0f, pendingVal)


        val animator = ValueAnimator().apply {
            setValues(archiveValuesHolder, progressValuesHolder, pendingValuesHolder)
            duration = 2000
            addUpdateListener {
                archivePercentage = (it.getAnimatedValue(ARCHIVE_VAL) as Float).toInt()
                pendingPercentage = (it.getAnimatedValue(PENDING_VAL) as Float).toInt()
                progressPercentage = (it.getAnimatedValue(PROCESS_VAL) as Float).toInt()
                invalidate()
            }
        }
        animator.start()
    }

    override fun onDraw(canvas: Canvas?) {
        setSpace()
        canvas?.let {
            drawArchivedArc(it)
            drawProgressArc(it)
            drawPendingArc(it)
        }
    }

    private fun setSpace() {
        val horizontalCenter = (width.div(2)).toFloat()
        val verticalCenter = (height.div(2)).toFloat()

        val archSize = 250
        val progSize = 200
        val pendSize = 150

        archivedView.set(
            horizontalCenter - archSize,
            verticalCenter - archSize,
            horizontalCenter + archSize,
            verticalCenter + archSize
        )

        progressView.set(
            horizontalCenter - progSize,
            verticalCenter - progSize,
            horizontalCenter + progSize,
            verticalCenter + progSize
        )

        pendingView.set(
            horizontalCenter - pendSize,
            verticalCenter - pendSize,
            horizontalCenter + pendSize,
            verticalCenter + pendSize
        )
    }

    private fun drawArchivedArc(canvas: Canvas) {
        val percentageToFill = getPercentage(Utils.REPORT_STATUS.ARCHIVE)
        Log.i("PER", percentageToFill.toString())
        canvas.drawArc(archivedView, 270f, percentageToFill, false, fillArcPaint)
    }

    private fun drawProgressArc(canvas: Canvas) {
        val percentageToFill = getPercentage(Utils.REPORT_STATUS.PROGRESS)
        canvas.drawArc(progressView, 90f, percentageToFill, false, fillArcPaint1)
    }

    private fun drawPendingArc(canvas: Canvas) {
        val percentageToFill = getPercentage(Utils.REPORT_STATUS.PENDING)
        canvas.drawArc(pendingView, 180f, percentageToFill, false, fillArcPaint2)
    }

    private fun getPercentage(type: Utils.REPORT_STATUS): Float {
        return when (type) {
            Utils.REPORT_STATUS.ARCHIVE -> {
                val perVal: Float = (archivePercentage.toFloat() / totalPercentage.toFloat())
                ARC_FULL_ROTATION_DEGREE * perVal
            }
            Utils.REPORT_STATUS.PENDING -> {
                val perVal: Float = (pendingPercentage.toFloat() / totalPercentage.toFloat())
                ARC_FULL_ROTATION_DEGREE * perVal
            }
            Utils.REPORT_STATUS.PROGRESS -> {
                val perVal: Float = (progressPercentage.toFloat() / totalPercentage.toFloat())
                ARC_FULL_ROTATION_DEGREE * perVal
            }
        }


    }


    private val fillArcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = context.resources.getColor(R.color.cyanShade, null)
        strokeWidth = 160f
        strokeCap = Paint.Cap.SQUARE
    }

    private val fillArcPaint1 = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = context.resources.getColor(R.color.gold, null)
        strokeWidth = 125f
        strokeCap = Paint.Cap.SQUARE
    }

    private val fillArcPaint2 = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = context.resources.getColor(R.color.orange, null)
        strokeWidth = 70f
        strokeCap = Paint.Cap.SQUARE
    }
}
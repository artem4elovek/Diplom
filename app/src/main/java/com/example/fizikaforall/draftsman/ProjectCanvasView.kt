package com.example.fizikaforall.draftsman

import android.content.Context
import android.graphics.*
import android.view.SurfaceHolder
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.fizikaforall.R


class ProjectCanvasView : SurfaceHolder.Callback {

    // for caching
    private lateinit var canvas: Canvas
    private lateinit var bitmap: Bitmap
    private lateinit var paint: Paint
    var x=600f
    var y= 400f

    override fun surfaceCreated(p0: SurfaceHolder) {
        canvas = p0.lockCanvas()

        paint= Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 30f
        canvas.drawLine(100f,100f,x,y, paint)
        p0.unlockCanvasAndPost(canvas)
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        canvas = p0.lockCanvas()
        paint= Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 30f
        canvas.drawLine(100f,100f,x,y, paint)
        p0.unlockCanvasAndPost(canvas)
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        canvas = p0.lockCanvas()
        paint= Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 30f
        canvas.drawLine(100f,100f,x,y, paint)
        p0.unlockCanvasAndPost(canvas)
    }

    fun update (){

    }

}
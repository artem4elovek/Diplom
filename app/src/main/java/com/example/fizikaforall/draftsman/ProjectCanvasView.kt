package com.example.fizikaforall.draftsman

import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.SurfaceHolder
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.fizikaforall.R
import kotlinx.coroutines.*


class ProjectCanvasView(private val paintEngine: PaintEngine) : SurfaceHolder.Callback {

    private lateinit var drawingJob: Job
    private var life = true

    fun endCoroutine(){
        life = false
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        drawingJob = GlobalScope.launch(Dispatchers.IO) {
            while(life) {
                if (paintEngine.testNew()) {
                    val canvas = p0.lockCanvas() ?: continue
                    try {
                        synchronized(p0) {
                            paintEngine.giveCanvas(canvas)
                        }
                    } finally {
                        p0.unlockCanvasAndPost(canvas)
                    }
                }
            }
        }
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        drawingJob = GlobalScope.launch(Dispatchers.IO) {
            while(life) {
                if (paintEngine.testNew()){
                    val canvas = p0.lockCanvas()?: continue
                    try {
                        synchronized(p0) {
                            paintEngine.giveCanvas(canvas)
                        }
                    } finally {
                        p0.unlockCanvasAndPost(canvas)
                    }
                }
            }
        }
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        drawingJob = GlobalScope.launch(Dispatchers.IO) {
            while (life) {
                if (paintEngine.testNew()) {
                    val canvas = p0.lockCanvas() ?: continue
                    try {
                        synchronized(p0) {
                            paintEngine.giveCanvas(canvas)
                        }
                    } finally {
                        p0.unlockCanvasAndPost(canvas)
                    }
                }
            }
        }
    }

/*    private fun drawingLogic(canvas: Canvas) {

        paint= Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 25f
        k+=10
        canvas.drawLine(300f,100f+k,600f+k,610f, paint)
        canvas.drawBitmap()
    }*/

}
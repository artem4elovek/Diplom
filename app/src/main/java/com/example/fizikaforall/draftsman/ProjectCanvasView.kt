package com.example.fizikaforall.draftsman

import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.SurfaceHolder
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.fizikaforall.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ProjectCanvasView : SurfaceHolder.Callback {

    private lateinit var canvas: Canvas
    private lateinit var bitmap: Bitmap
    private lateinit var paint: Paint
    var x=600f
    var y= 400f

    /*constructor(x1: Float, y1:Float) : this() {
        x = x1
        y = y1
    }
*/
    /*@Override
    fun onTouchEvent(event: MotionEvent){

    }
*/

    override fun surfaceCreated(p0: SurfaceHolder) {
        canvas = p0.lockCanvas()
        paint= Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 25f
        canvas.drawLine(300f,100f,x,y, paint)
        p0.unlockCanvasAndPost(canvas)

        /*GlobalScope.launch(Dispatchers.Main) {
        for (k in 1 until 800 ) {
            canvas.drawLine(100f, 100f, k.toFloat(), k.toFloat(), paint)
            p0.unlockCanvasAndPost(canvas)
            delay(1000L)
        }}*/
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        canvas = p0.lockCanvas()
        paint= Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 25f
        canvas.drawLine(300f,100f,x,y, paint)
        p0.unlockCanvasAndPost(canvas)
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {

    }
}
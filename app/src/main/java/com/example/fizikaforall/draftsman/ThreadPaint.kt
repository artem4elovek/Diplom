package com.example.fizikaforall.draftsman

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import kotlinx.coroutines.delay
import java.lang.Exception

class ThreadPaint(private val paintView: SurfaceHolder) :Thread() {
    private var life = true
    private lateinit var paint:Paint
    fun stopPaint(){
        life=false
    }
    override fun run() {
        super.run()
        while (life){
            val canvas = paintView.lockCanvas()
            canvas.drawColor(Color.WHITE)
            paint= Paint(Paint.ANTI_ALIAS_FLAG)
            paint.color = Color.RED
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 25f
            try {

                canvas.drawLine(300f,100f,600f,610f, paint)
            Log.d("ssss","ssss")
               //// for (k in 1 until 800 ) {
                 ////   canvas.drawLine(100f, 100f, k.toFloat(), k.toFloat(), paint)
                synchronized(paintView) {
                    canvas
                    }
                  //  sleep(10L)
               // }
            }catch (e:Exception){e.printStackTrace()}
            finally {
                paintView.unlockCanvasAndPost(canvas)
            }

        }
    }

}
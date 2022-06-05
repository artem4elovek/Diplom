package com.example.fizikaforall.draftsman

import android.content.Context
import android.graphics.*
import android.view.SurfaceHolder
import androidx.core.content.ContextCompat
import com.example.fizikaforall.R

class PaintEngine(val context: Context) {
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var details = mutableListOf<DetailPrint>()
    private var x:Float = 0f
    private var y:Float=0f
    init{

        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 12f
        paint.style= Paint.Style.FILL

    }


    fun giveCanvas(canvas: Canvas){
        canvas.drawColor(ContextCompat.getColor(context, R.color.white))
        //canvas.drawBitmap(Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565),x,y,paint)
        details.map{ canvas.drawBitmap(it.image,it.x,it.y,paint) }
        //canvas.drawBitmap(BitmapFactory.decodeResource(context.resources,R.drawable.ic_resistor), x,y, paint)
       // canvas.drawLine(300f,100f,x,y, paint)
    }


    fun giveDetails(list:MutableList<DetailPrint>){
        details = list
    }

    fun moveLine(x:Float,y:Float){
        this.x = x
        this.y = y
    }





}
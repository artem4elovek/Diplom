package com.example.fizikaforall.draftsman

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.MotionEvent
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.example.fizikaforall.R


class TouchHandler(private val paintEngine:PaintEngine, val context: Context, private var list: MutableList<DetailPrint> = mutableListOf<DetailPrint>(), private var idChoice: Int = 0) {

    private var list2 = mutableListOf<DetailPrint>()
    private var touchRadius:Int = 60
    private var x:Float = 0f
    private var y:Float = 0f
    private var size:Int = 120
    private var startX:Float = 0f
    private var startY:Float = 0f
    private var id:Int = 1
    private var touchId:Int = 0
    private var tup = true


    fun runTouch(event: MotionEvent){
        val touchCount = event.pointerCount
        for(i in 0 until touchCount) {
            when {
                event.getPointerId(i) == 0 -> {
                    when (event.action) {
                        MotionEvent.ACTION_MOVE -> move(event.x,event.y)
                        MotionEvent.ACTION_DOWN-> scanNearby(event.x,event.y)
                        MotionEvent.ACTION_UP -> saveMove(event.x,event.y)
                    }
                }
                event.getPointerId(i) == 1 -> {}
                event.getPointerId(i) == 2 -> {}
                event.getPointerId(i) == 3 -> {}
            }
        }
    }

    fun resistorChoice(){
        id+=1
        /*val bitmap = context.resources.getDrawable(R.drawable.ic_voltmeter,null).toBitmap(size, size)
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
        val image = stream.toByteArray()
        list.add(Resistor(startX,startY,id,image))*/
        list.add(Resistor(startX,startY,id, context.resources.getDrawable(R.drawable.ic_resistor,null).toBitmap(size, size)))
        paintEngine.giveDetails(list)
        idChoice=2
    }
    fun voltmeterChoice(){
        id+=1
        list.add(Resistor(startX,startY,id, context.resources.getDrawable(R.drawable.ic_voltmeter,null).toBitmap(size, size)))
        paintEngine.giveDetails(list)
        idChoice=3
    }
    fun powerChoice(){
        id+=1
        list.add(Resistor(startX,startY,id, context.resources.getDrawable(R.drawable.ic_power,null).toBitmap(size, size)))
        paintEngine.giveDetails(list)
        idChoice=1
    }
    fun cableChoice(){
        idChoice=0
    }

    private fun scanNearby(x: Float, y:Float):Int = list.lastOrNull {
        (it.x+touchRadius <= x) and
                (it.x+touchRadius >= (x-size)) and
                (it.y+touchRadius <= y) and
                (it.y+touchRadius >= (y-size))}?.id ?: 0


    private fun saveMove(x: Float, y:Float){
        if (tup)
        {

            //должны открыться настройки детали
        }
        else {
            tup = true}

    }

    private fun move(x: Float, y:Float){

        //paintEngine.moveLine(x,y)
       if (tup){
           touchId = scanNearby(x, y)
        }
        tup=false

        if (touchId != 0) list.map { if(it.id == touchId)
                                        {it.x= x
                                         it.y=y }else it }
    }



}
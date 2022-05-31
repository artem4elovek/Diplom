package com.example.fizikaforall.draftsman

import android.content.Context
import android.view.MotionEvent
import android.widget.Toast

class TouchHandler(private val context: Context, private var idChoice: Int = 0) {

    var x:Float = 0f
    var y:Float = 0f
    var tup = true


    fun runTouch(event: MotionEvent){
        val touchCount = event.pointerCount
        for(i in 0 until touchCount) {
            if (event.getPointerId(i) == 0) {
                when (event.action) {
                    MotionEvent.ACTION_MOVE -> move(event.x,event.y)
                    MotionEvent.ACTION_DOWN-> scanNearby(event.x,event.y)
                    MotionEvent.ACTION_UP -> saveMove(event.x,event.y)
                }
            }
            else if (event.getPointerId(i) == 1) {aaa("два пальца нельзя")}
            else if (event.getPointerId(i) == 2) {aaa("три пальца нельзя")}
            else if (event.getPointerId(i) == 3) {aaa("четыре пальца нельзя")}
        }
    }

    fun resistorChoice(){
        idChoice=2
    }
    fun voltmeterChoice(){
        idChoice=3
    }
    fun powerChoice(){
        idChoice=1
    }
    fun cableChoice(){
        idChoice=0
    }

    private fun scanNearby(x: Float, y:Float){

    }

    private fun saveMove(x: Float, y:Float){
        if (tup)
        {
            aaa("$x  $y компонент выбран $idChoice")
            //должны открыться настройки детали
        }
        else {aaa("$x  $y компонент перенесен $idChoice")
            tup = true}

    }

    private fun move(x: Float, y:Float){

        tup=false
    }

    private fun aaa(str:String){
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
    }


}
package com.example.fizikaforall.draftsman

import android.graphics.Bitmap
import androidx.core.graphics.scale
import kotlin.math.sqrt

open class Dot(open var x : Float,
               open var y: Float,
               open var id:Int,
               open var parentId:Int = 0)
{

    fun distance(that: Dot): Float {
        val distanceX = this.x - that.x
        val distanceY = this.y - that.y
        return sqrt(distanceX * distanceX + distanceY * distanceY)
    }

}
open class DetailPrint(
     override var x : Float,
     override  var y: Float,
     open val image: Bitmap,
     override var id:Int,
     open var size : Int,
     open var angle:Int = 0
):Dot(x,y,id){
    open var bondingPoints = mutableListOf<Dot>()
    fun moveEvent(  newX : Float, newY: Float){
        bondingPoints.map{it.x+=(newX-x)
            it.y+=(newY-y)}
        x = newX
        y = newY
    }
    fun sizeEvent(newSize : Int){
        size = newSize
        image.scale(size, size)
    }
}
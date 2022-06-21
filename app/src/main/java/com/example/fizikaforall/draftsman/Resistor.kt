package com.example.fizikaforall.draftsman

import android.graphics.Bitmap
import android.graphics.BitmapFactory


data class AllDetails(
    val details: MutableList<DetailPrint>,
    val cables: MutableList<Cable>
)



interface TextPlaice{
    var text: String
    var units: String
    fun getSizeText() : Float
    fun getTextPosition():Pair< Float, Float>
    fun getText() = Pair("$text $units",getSizeText())
}

class Resistor(
    override var x : Float,
    override var y: Float,
    override var id:Int,
    override val image: Bitmap,
    override var size : Int,
    override var angle: Int,
    override var units: String
): DetailPrint(x,y,image,id, size ),TextPlaice {
    override var bondingPoints = mutableListOf<Dot>(Dot(x,y+(size/2),1, id),Dot(x+(size),y+(size/2),2, id))
    override var text: String = "0"
    override fun getSizeText()  =(size/2).toFloat()
    override fun getTextPosition(): Pair< Float, Float> = Pair(x, y)
}

class PowerAdapter(
    override var x : Float,
    override var y: Float,
    override var id:Int,
    override val image: Bitmap,
    override var size : Int,
    override var angle: Int,
    override var units: String
): DetailPrint(x,y,image,id, size),TextPlaice
{
    override var bondingPoints = mutableListOf<Dot>(Dot(x,y+(size/2),1, id),Dot(x+(size),y+(size/2),2, id))
    override var text: String = "0"
    override fun getSizeText(): Float =(size/2).toFloat()
    override fun getTextPosition(): Pair< Float, Float> = Pair(x ,y)
}

class Knot(
    override var x : Float,
    override var y: Float,
    override var id:Int,
    override val image: Bitmap,
    override var size : Int
): DetailPrint(x,y,image,id, size ) {
    override var bondingPoints = mutableListOf<Dot>(Dot(x,y,1, id))
}

class Voltmeter (
    override var x : Float,
    override var y: Float,
    override var id:Int,
    override val image: Bitmap,
    override var size : Int,
    override var angle: Int,
    override var units: String
): DetailPrint(x,y,image,id, size),TextPlaice{
    override var text: String = "0"
    override var bondingPoints = mutableListOf<Dot>(Dot(x,y+(size/2),1, id),Dot(x+(size),y+(size/2),2, id))
    override fun getSizeText(): Float =(size/2).toFloat()
    override fun getTextPosition(): Pair< Float, Float> = Pair(x, y)
}
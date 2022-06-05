package com.example.fizikaforall.draftsman

import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Resistor(
    override  var x : Float,
    override  var y: Float,
    override  val id:Int,
    override val image: Bitmap // = BitmapFactory.
): DetailPrint(x,y,image,id)

class PowerAdapter(
   override var x : Float,
   override  var y: Float,
   override val image: Bitmap,
   override val id:Int
): DetailPrint(x,y,image,id)

/*class Resistor(
    private var x : Float,
    private var y: Float,
    private  val image: Bitmap,
    private  val id:Int
): DetailPrint(x,y,image,id) {
    //private lateinit var  image: Bitmap

    //init

}*/
class Voltmeter (
    override var x : Float,
    override var y: Float,
    override val image: Bitmap,
    override val id:Int
): DetailPrint(x,y,image,id)
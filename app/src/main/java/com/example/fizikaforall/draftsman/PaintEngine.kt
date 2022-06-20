package com.example.fizikaforall.draftsman

import android.content.Context
import android.graphics.*
import android.view.SurfaceHolder
import androidx.core.content.ContextCompat
import com.example.fizikaforall.R
import com.example.fizikaforall.fragments.DetailDialogSheetFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PaintEngine(val context: Context) {
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private  var details =  AllDetails(mutableListOf<DetailPrint>(), mutableListOf<Cable>())
    private var x:Float = 0f
    private var y:Float=0f
    private var testCable = false
    private var reloadScreen = true
    init{
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 12f
        paint.style= Paint.Style.FILL
    }
    fun giveCanvas(canvas: Canvas) {
            canvas.drawColor(ContextCompat.getColor(context, R.color.white))
            details.details.map { it ->
                canvas.drawBitmap(it.image.rotate(it.angle.toFloat()), it.x, it.y, paint)
                if (testCable) it.bondingPoints.map { dot -> canvas.drawPoint(dot.x, dot.y, paint) }
                if (it is  TextPlaice){
                    paint.textSize =it.getText().second
                    canvas.drawText(it.getText().first, it.getTextPosition().first,it.getTextPosition().second,paint)
                }
            }
            if (details.cables.isNotEmpty()) details
                .cables
                .map { cable ->
                    canvas.drawLine(
                        cable.dotStart.x,
                        cable.dotStart.y, cable.dotEnd.x,
                        cable.dotEnd.y,
                        paint
                    )
                }
            reloadScreen = false
    }
    fun cableEvent(){testCable=!testCable }
    fun testPicture():Boolean = testCable
    fun newScreen(){ reloadScreen = true }
    fun giveDetails(list:AllDetails){
            details = list
    }
    fun testNew():Boolean = reloadScreen

    private fun Bitmap.rotate(degrees: Float): Bitmap {
        val matrix = Matrix().apply { postRotate(degrees) }
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }

}
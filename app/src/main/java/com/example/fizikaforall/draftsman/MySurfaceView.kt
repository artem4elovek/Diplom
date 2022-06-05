package com.example.fizikaforall.draftsman

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.view.SurfaceView
import androidx.core.content.ContextCompat
import com.example.fizikaforall.R
import java.lang.Exception

class MySurfaceView(context: Context) : SurfaceView(context) {

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        try {
            canvas.drawColor(ContextCompat.getColor(context,R.color.white))
        } catch (e:Exception){
            e.printStackTrace()
        }


    }


}
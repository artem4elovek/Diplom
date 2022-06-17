package com.example.fizikaforall.draftsman

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.view.MotionEvent
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.FragmentManager
import com.example.fizikaforall.R
import com.example.fizikaforall.fragments.DetailDialogSheetFragment


class TouchHandler(
    private val paintEngine: PaintEngine,
    val context: Context,
    private var list1:AllDetails,
    private var fragmentManager: FragmentManager,
    private var idChoice: Int = 0
) {
    private var list = AllDetails(mutableListOf<DetailPrint>(), mutableListOf<Cable>())
    private var touchRadius: Int = 60
    private var fingerX: Float = 0f
    private var fingerY: Float = 0f
    private var size: Int = 120
    private var startX: Float = 0f
    private var startY: Float = 0f
    private var id: Int = 1
    private var touchId: Int = 0
    private var pairId: Pair<Int, Int> = 0 to 0
    private var tup = true
    private var firstId: Pair<Int, Int> = 0 to 0
    private var resultZoom: Float = 0f

    init {
        paintEngine.giveDetails(list)
    }

    fun runTouch(event: MotionEvent) {
        val touchCount = event.pointerCount
        for (i in 0 until touchCount) {
            when {
                event.getPointerId(i) == 0 -> {
                    when (event.action) {
                        MotionEvent.ACTION_MOVE -> move(event.x, event.y)
                        MotionEvent.ACTION_DOWN -> scanNearby(event.x, event.y)
                        MotionEvent.ACTION_UP -> saveMove(event.x, event.y)
                    }
                }
                event.getPointerId(i) == 1 -> {
                    when (event.action) {
                        MotionEvent.ACTION_MOVE -> {
                            if ((resultZoom != 0f) and (resultZoom > ((event.x + event.y) - (fingerX + fingerY))) and (size > 0)) size -= 20
                            else if ((resultZoom != 0f) and (resultZoom < ((event.x + event.y) - (fingerX + fingerY)))) size += 20
                            resultZoom = (event.x + event.y) - (fingerX + fingerY)
                            sizeEvent(size)
                        }
                    }
                }
                event.getPointerId(i) == 2 -> {}
                event.getPointerId(i) == 3 -> {}
            }
        }
    }

    fun resistorChoice() {
        id += 1
        /*val bitmap = context.resources.getDrawable(R.drawable.ic_voltmeter,null).toBitmap(size, size)
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
        val image = stream.toByteArray()
        list.add(Resistor(startX,startY,id,image))*/
        list.details.add(
            Resistor(
                startX,
                startY,
                id,
                context.resources.getDrawable(R.drawable.ic_resistor, null).toBitmap(size, size),
                size, 0
            )
        )
        idChoice = 2
        if (paintEngine.testPicture()) {
            paintEngine.cableEvent()
            firstId = 0 to 0
        }
        paintEngine.newScreen()
    }

    fun voltmeterChoice() {
        id += 1
        list.details.add(
            Voltmeter(
                startX,
                startY,
                id,
                context.resources.getDrawable(R.drawable.ic_voltmeter, null).toBitmap(size, size),
                size,
                "",
                0
            )
        )
        paintEngine.newScreen()
        idChoice = 3
        if (paintEngine.testPicture()) {
            paintEngine.cableEvent()
            firstId = 0 to 0
        }
    }

    fun powerChoice() {
        id += 1
        list.details.add(
            PowerAdapter(
                startX,
                startY,
                id,
                context.resources.getDrawable(R.drawable.ic_power, null).toBitmap(size, size),
                size,
                0
            )
        )
        idChoice = 1
        if (paintEngine.testPicture()) {
            paintEngine.cableEvent()
            firstId = 0 to 0
        }
        paintEngine.newScreen()
    }

    fun cableChoice() {
        if (paintEngine.testPicture()) {
            paintEngine.cableEvent()
            idChoice = -1
            firstId = 0 to 0
        } else {
            paintEngine.cableEvent()
            idChoice = 0
        }
        paintEngine.newScreen()
    }

    private fun sideTouch(x: Float, y: Float): Pair<Int, Int> {
        val id = scanNearby(x, y)
        var res = 0
        if (id != 0) {
            val dots = list
                .details
                .first { it.id == id }
            res = dots.bondingPoints.minByOrNull { it.distance(Dot(x, y, 0)) }!!.id
        }
        return id to res
    }


    private fun scanNearby(x: Float, y: Float): Int = list.details.lastOrNull {
        ((it.x - touchRadius) <= x) and
                ((it.x + touchRadius) >= (x - it.size)) and
                ((it.y - touchRadius) <= y) and
                ((it.y + touchRadius) >= (y - it.size))
    }?.id ?: 0

    private fun saveMove(x: Float, y: Float) {
        if (tup) {
            if (idChoice == 0) {
                pairId = sideTouch(x, y)
                if (pairId != 0 to 0) {
                    if (firstId.first != pairId.first) {
                        if (firstId == 0 to 0) {
                            firstId = pairId
                        } else {
                            list.cables.add(
                                Cable(list.details.last {
                                    it.id == firstId.first
                                }.bondingPoints.last { it.id == firstId.second },
                                    list.details.last { it.id == pairId.first }.bondingPoints.last {
                                        it.id == pairId.second
                                    })
                            )
                            firstId = 0 to 0
                        }
                    }
                } else if (firstId != 0 to 0) {
                    id += 1
                    val knot = Knot(
                        x,
                        y,
                        id,
                        Bitmap.createBitmap(size.toInt(), size.toInt(), Bitmap.Config.ARGB_8888),
                        6
                    )
                    list.details.add(knot)
                    list.cables.add(Cable(list.details.last { it.id == firstId.first }.bondingPoints.last {
                        it.id == firstId.second
                    }, knot.bondingPoints[0]))
                    firstId = 0 to 0
                }
            } else {
                val detail=  DetailDialogSheetFragment(angleEvent {})
             //   Toast.makeText(context,list.details.last { it.id == idChoice}.toString(),Toast.LENGTH_SHORT).show()
                detail.show(fragmentManager,  "hhj")
                //text(idChoice)
            }
            //должны открыться настройки детали

        } else {
            tup = true
        }
        resultZoom = 0f
        fingerX = 0f
        fingerY = 0f
        paintEngine.newScreen()
    }

    private fun text(id: Int) {
        paintEngine.newScreen()
    }

    private fun angleEvent(i:Int) {
        list.details[0].angle += 90
      //  paintEngine.newScreen()
    }


    private fun sizeEvent(newSize: Int) {
        list.details.map { it.sizeEvent(newSize) }
        paintEngine.newScreen()
    }

    private fun move(x: Float, y: Float) {
        fingerX = x
        fingerY = y
        if (tup) {
            touchId = scanNearby(x, y)
        }
        tup = false
        if (touchId != 0) list.details.map {
            if (it.id == touchId) it.moveEvent(x, y)
            else it
        }
        paintEngine.newScreen()
    }
}
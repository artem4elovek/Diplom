package com.example.fizikaforall.draftsman

import android.content.Context
import android.graphics.Bitmap
import android.view.MotionEvent
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.FragmentManager
import com.example.fizikaforall.R
import com.example.fizikaforall.fragments.DetailDialogSheetFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


class TouchHandler(
    private val paintEngine: PaintEngine,
    val context: Context,
    private var list: AllDetails,
    private var fragmentManager: FragmentManager,
    private var bottomSheet: (Int)->Unit,
    private var idChoice: Int =  -1
) {
    //private var list = AllDetails(mutableListOf<DetailPrint>(), mutableListOf<Cable>())
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

    fun giveList(): AllDetails = list
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

    private fun newId() {
        while (list.details.map { it.id }.toList().contains(id)) id += 1
    }


    private fun cableDel(id: Int): List<Cable> =
        list.cables.filter { (it.dotStart.parentId == id) || (it.dotEnd.parentId == id) }

    fun deleteDetail(id: Int) {
        var detail = list.details.lastOrNull { it.id == id }
        if (detail != null) {
            var allContact = cableDel(detail.id)
            allContact.map { list.cables.remove(it) }
            list.details.remove(detail)
        }
    }


    fun angleDetail(id: Int) {
        val angle = 90
        list.details.map { detalPrint ->
            if (detalPrint.id == id) {
                if (detalPrint.angle + angle > 270) {
                    detalPrint.angle = 0
                } else detalPrint.angle += angle
                detalPrint.bondingPoints.map {
                    val cosMy = cos((PI / 2)).toFloat()
                    val sinMy = sin((PI / 2)).toFloat()
                    val centerX: Float = detalPrint.x + (detalPrint.size / 2)
                    val centerY: Float = detalPrint.y + (detalPrint.size / 2)
                    val x = cosMy * (it.x - centerX) - sinMy * (it.y - centerY) + centerX
                    it.y = sinMy * (it.x - centerX) + cosMy * (it.y - centerY) + centerY
                    it.x = x
                }
            }
        }
        paintEngine.newScreen()
    }

    fun delChoice() {
        idChoice = 4
    }

    fun resistorChoice() {
        newId()
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
        newId()
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
        newId()
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
                    newId()
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
                var idDetail = scanNearby(x, y)
                if (idChoice == 4) deleteDetail(idDetail) //deleteDetail(idDetail)
                else bottomSheet(idDetail)
               /* val detail = DetailDialogSheetFragment()
                Toast.makeText(
                    context,
                    list.details.last { it.id == idChoice }.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                detail.show(fragmentManager, "hhj")*/
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

    fun newText(id: Int, text:String) {
        list.details.map{ if((it.id == id)){ if (it is TextPlaice) it.text = text}
            paintEngine.newScreen()
        }
    }

     fun text(id: Int):String? {
         val detail = list.details.last { it.id == id }
          return if(detail is TextPlaice) detail.text
         else null
    }

    private fun angleEvent(i: Int) {
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
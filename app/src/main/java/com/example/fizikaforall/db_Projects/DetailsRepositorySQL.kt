package com.example.fizikaforall.db_Projects

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.core.database.getFloatOrNull
import androidx.core.graphics.drawable.toBitmap
import com.example.fizikaforall.R
import com.example.fizikaforall.draftsman.*
import com.example.fizikaforall.manual.ManualProject
import kotlinx.coroutines.CoroutineDispatcher
import kotlin.math.log

class DetailsRepositorySQL(
    private val db: SQLiteDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : DetailsInterface {


    override fun getAllDetails(id_Project: Int, context: Context): AllDetails {
        val cursor = db.query(
            ContractSQL.Details.NAME_TABLE,
            arrayOf(
                ContractSQL.Details.POSITION_X,
                ContractSQL.Details.POSITION_Y,
                ContractSQL.Details.ID_COLUMN,
                ContractSQL.Details.SIZE_COLUMN,
                ContractSQL.Details.TYPE_COMPONENT,
                ContractSQL.Details.ROTATION,
                ContractSQL.Details.ID_PROJECT,
            ),
            "${ContractSQL.Details.ID_PROJECT} == ?",
            arrayOf(id_Project.toString()),
            null,
            null,
            null
        )
        var allDetails = AllDetails(cursor.use {
            val list = mutableListOf<DetailPrint>()
            while (cursor.moveToNext()) {
                list.add(createComponents(cursor, context))
                //Toast.makeText(context,cursor.getInt(cursor.getColumnIndexOrThrow(ContractSQL.Details.ID_COLUMN)).toString(),Toast.LENGTH_SHORT).show()
            }
            return@use list
        }, mutableListOf<Cable>())

        var cable = cableConnector(allDetails.details)
        cable.map { allDetails.cables.add(it) }

        return allDetails
    }


    private fun downloadDots(id_Detail: Int): MutableList<Dot> {
        val cursor = db.query(
            ContractSQL.DotsContract.NAME_TABLE,
            arrayOf(
                ContractSQL.DotsContract.ID_COLUMN,
                ContractSQL.DotsContract.ID_DETAIL,
                ContractSQL.DotsContract.POSITION_X,
                ContractSQL.DotsContract.POSITION_Y
            ),
            "${ContractSQL.DotsContract.ID_DETAIL} == ?",
            arrayOf(id_Detail.toString()),
            null,
            null,
            null
        )
        return cursor.use {
            val list = mutableListOf<Dot>()
            while (cursor.moveToNext()) {
                list.add(
                    Dot(
                        cursor.getFloat(cursor.getColumnIndexOrThrow(ContractSQL.DotsContract.POSITION_X)),
                        cursor.getFloat(cursor.getColumnIndexOrThrow(ContractSQL.DotsContract.POSITION_Y)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(ContractSQL.DotsContract.ID_COLUMN)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(ContractSQL.DotsContract.ID_DETAIL))
                    )
                )
            }
            return@use list
        }
    }


    private fun cableConnector(list: MutableList<DetailPrint>): MutableList<Cable> {
        val cableList = mutableListOf<Cable>()
        list.map { detailPrint ->
            detailPrint.bondingPoints.map { firstDot ->
                val test = testingConnect(firstDot)
                if (test.isNotEmpty()) {
                    test.map { id_Dot ->
                        list.map { detailPrintNext ->
                            detailPrintNext.bondingPoints.map { endDot ->
                                if (endDot.id == id_Dot)
                                    cableList.add(Cable(firstDot, endDot))
                            }
                        }
                    }
                }
            }
        }
        return cableList
    }

    private fun createComponents(cursor: Cursor, context: Context): DetailPrint =
        when (cursor.getInt(cursor.getColumnIndexOrThrow(ContractSQL.Details.TYPE_COMPONENT))) {
            0 -> createKnot(cursor, context)
            1 -> createPower(cursor, context)
            2 -> createResistor(cursor, context)
            3 -> createVoltmeter(cursor, context)
            else -> createKnot(cursor, context)
        }

    private fun createResistor(cursor: Cursor, context: Context): Resistor {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(ContractSQL.Details.ID_COLUMN))
        var size = cursor.getInt(cursor.getColumnIndexOrThrow(ContractSQL.Details.SIZE_COLUMN))
        var resistor = Resistor(
            cursor.getFloat(cursor.getColumnIndexOrThrow(ContractSQL.Details.POSITION_X)),
            cursor.getFloat(cursor.getColumnIndexOrThrow(ContractSQL.Details.POSITION_Y)),
            id,
            context.resources.getDrawable(R.drawable.ic_resistor, null).toBitmap(size, size),
            size,
            cursor.getInt(cursor.getColumnIndexOrThrow(ContractSQL.Details.ROTATION)),
            context.getString(R.string.Om)
        )


        val cursor1 = db.query(
            ContractSQL.Resistors.NAME_TABLE,
            arrayOf(ContractSQL.Resistors.RESISTANCE),
            "${ContractSQL.Resistors.ID_DETAIL} == ?",
            arrayOf(id.toString()), null, null, null
        )
        cursor1.use {
            cursor1.moveToLast()
            resistor.text = cursor1.getFloat(cursor1.getColumnIndexOrThrow(ContractSQL.Resistors.RESISTANCE)).toString()
        }



        resistor.bondingPoints =
            downloadDots(id)
        //list.map { resistor.bondingPoints.add(it) }
        return resistor
    }


    private fun testingConnect(dot: Dot): MutableList<Int> {

        val cursor = db.query(
            ContractSQL.ContactDots.NAME_TABLE,
            arrayOf(
                ContractSQL.ContactDots.ID_FINISH
            ),
            "${ContractSQL.ContactDots.ID_START} == ?",
            arrayOf(dot.id.toString()),
            null,
            null,
            null
        )
        return cursor.use {
            val list = mutableListOf<Int>()
            while (cursor.moveToNext()) {
                list.add(
                    cursor.getInt(
                        cursor
                            .getColumnIndexOrThrow(
                                ContractSQL
                                    .ContactDots.ID_FINISH
                            )
                    )
                )
            }
            return@use list
        }
    }


    private fun createVoltmeter(cursor: Cursor, context: Context): Voltmeter {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(ContractSQL.Details.ID_COLUMN))
        var size = cursor.getInt(cursor.getColumnIndexOrThrow(ContractSQL.Details.SIZE_COLUMN))
        var voltmeter = Voltmeter(
            cursor.getFloat(cursor.getColumnIndexOrThrow(ContractSQL.Details.POSITION_X)),
            cursor.getFloat(cursor.getColumnIndexOrThrow(ContractSQL.Details.POSITION_Y)),
            id,
            context.resources.getDrawable(R.drawable.ic_voltmeter, null).toBitmap(size, size),
            size,
            cursor.getInt(cursor.getColumnIndexOrThrow(ContractSQL.Details.ROTATION)),
            context.getString(R.string.Om)
        )

        val cursor1 = db.query(
            ContractSQL.Voltmeter.NAME_TABLE,
            arrayOf(ContractSQL.Voltmeter.VOLTAGE),
            "${ContractSQL.Voltmeter.ID_DETAIL} == ?",
            arrayOf(id.toString()), null, null, null
        )
        cursor1.use {
            cursor1.moveToLast()
            voltmeter.text =
                cursor1.getDouble(cursor1.getColumnIndexOrThrow(ContractSQL.Voltmeter.VOLTAGE))
                    .toString()
        }


        voltmeter.bondingPoints =
            downloadDots(id)
        return voltmeter
    }

    private fun createPower(cursor: Cursor, context: Context): PowerAdapter {
        var size = cursor.getInt(cursor.getColumnIndexOrThrow(ContractSQL.Details.SIZE_COLUMN))
        var id = cursor.getInt(cursor.getColumnIndexOrThrow(ContractSQL.Details.ID_COLUMN))
        var powerAdapter = PowerAdapter(
            cursor.getFloat(cursor.getColumnIndexOrThrow(ContractSQL.Details.POSITION_X)),
            cursor.getFloat(cursor.getColumnIndexOrThrow(ContractSQL.Details.POSITION_Y)),
            id,
            context.resources.getDrawable(R.drawable.ic_power, null).toBitmap(size, size),
            size,
            cursor.getInt(cursor.getColumnIndexOrThrow(ContractSQL.Details.ROTATION)),
            context.getString(R.string.Volt)
        )
        Log.d("cr", id.toString())
        val cursor1 = db.query(
            ContractSQL.Powers.NAME_TABLE,
            arrayOf(ContractSQL.Powers.VOLTAGE),
            "${ContractSQL.Powers.ID_DETAIL} == ?",
            arrayOf(id.toString()), null, null, null
        )
        Log.d("cr", id.toString())
            cursor1.moveToLast()
            powerAdapter.text = cursor1.getDouble(cursor1.getColumnIndexOrThrow(ContractSQL.Powers.VOLTAGE)).toString()
        powerAdapter.bondingPoints = downloadDots(id)

        return powerAdapter
    }

    private fun createKnot(cursor: Cursor, context: Context): Knot {
        var size = cursor.getInt(cursor.getColumnIndexOrThrow(ContractSQL.Details.SIZE_COLUMN))
        var knot = Knot(
            cursor.getFloat(cursor.getColumnIndexOrThrow(ContractSQL.Details.POSITION_X)),
            cursor.getFloat(cursor.getColumnIndexOrThrow(ContractSQL.Details.POSITION_Y)),
            cursor.getInt(cursor.getColumnIndexOrThrow(ContractSQL.Details.ID_COLUMN)),
            Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888),
            size,
        )
        knot.bondingPoints =
            downloadDots(cursor.getInt(cursor.getColumnIndexOrThrow(ContractSQL.Details.ID_COLUMN)))

        return knot
    }

    override fun saveProject(id_Project: Int, allDetails: AllDetails, context: Context) {
        try {
            db.execSQL("PRAGMA foreign_keys=ON;")
            db.delete(
                ContractSQL.Details.NAME_TABLE,
                "${ContractSQL.Details.ID_PROJECT} == ?",
                arrayOf(id_Project.toString())
            )

        } catch (e: SQLiteConstraintException) {
        }
        allDetails.details.map { detail ->
            var result: Int = 0
            try {
                db.insertOrThrow(
                    ContractSQL.Details.NAME_TABLE,
                    null,
                    contentValuesOf(
                        ContractSQL.Details.POSITION_X to detail.x,
                        ContractSQL.Details.POSITION_Y to detail.y,
                        ContractSQL.Details.SIZE_COLUMN to detail.size,
                        ContractSQL.Details.TYPE_COMPONENT to
                                when (detail) {
                                    is Knot -> 0
                                    is PowerAdapter -> 1
                                    is Resistor -> 2
                                    is Voltmeter -> 3
                                    else -> {
                                        0
                                    }
                                },
                        ContractSQL.Details.ROTATION to detail.angle,
                        ContractSQL.Details.ID_PROJECT to id_Project
                    )
                )
            } catch (e: SQLiteConstraintException) {
                result = -1
            }
            val cursor: Cursor =
                db.query(
                    ContractSQL.Details.NAME_TABLE,
                    arrayOf(ContractSQL.Details.ID_COLUMN), null, null, null, null, null
                )
            cursor.moveToLast()
            detail.id = cursor.getInt(cursor.getColumnIndexOrThrow(ContractSQL.Details.ID_COLUMN))
            when (detail) {
                is PowerAdapter -> savePower(detail)
                is Resistor -> saveResistor(detail)
                is Voltmeter -> saveVoltmeter(detail)
                else -> {}
            }
            detail.bondingPoints.map { point ->
                point.parentId =
                    cursor.getInt(cursor.getColumnIndexOrThrow(ContractSQL.Details.ID_COLUMN))
                db.insertOrThrow(
                    ContractSQL.DotsContract.NAME_TABLE,
                    null,
                    contentValuesOf(
                        ContractSQL.DotsContract.POSITION_X to point.x,
                        ContractSQL.DotsContract.POSITION_Y to point.y,
                        ContractSQL.DotsContract.ID_DETAIL to point.parentId
                    )
                )
                val cursor1: Cursor =
                    db.query(
                        ContractSQL.DotsContract.NAME_TABLE,
                        arrayOf(ContractSQL.DotsContract.ID_COLUMN), null, null, null, null, null
                    )
                cursor1.moveToLast()
                point.id =
                    cursor1.getInt(cursor.getColumnIndexOrThrow(ContractSQL.DotsContract.ID_COLUMN))
                Log.d("errPoint", point.toString())
            }
        }
        allDetails.cables.map { cable ->
            Log.d(
                "err",
                cable.dotStart.toString() + "<>" + cable.dotStart.id.toString() + "______" + cable.dotEnd.toString() + "<>" + cable.dotEnd.id.toString()
            )
            //Toast.makeText(context, cable.dotStart.id.toString()+cable.dotEnd.id.toString() ,Toast.LENGTH_SHORT).show()
            db.insertOrThrow(
                ContractSQL.ContactDots.NAME_TABLE,
                null,
                contentValuesOf(
                    ContractSQL.ContactDots.ID_START to cable.dotStart.id,
                    ContractSQL.ContactDots.ID_FINISH to cable.dotEnd.id
                )
            )

        }
    }


    private fun saveResistor(detail: Resistor) {
        var test = detail.text
       if  (test.isEmpty()){
           test = 0.toString()
        }else{
           // test.filter {(it in '0'..'9') || (it=='.')}
        }

        try {
            Log.d("test",  test.toFloat().toString() + "--" + detail.id)
            db.insertOrThrow(
                ContractSQL.Resistors.NAME_TABLE,
                null,
                contentValuesOf(
                    ContractSQL.Resistors.ID_DETAIL to detail.id,
                    ContractSQL.Resistors.RESISTANCE to test.toDouble()
                )
            )
        } catch (e: SQLiteConstraintException) {

        }
    }

    private fun saveVoltmeter(detail: Voltmeter) {
        var test = detail.text
        if  (test.isEmpty()){
            test = 0.toString()
        }


        try {
            db.insertOrThrow(
                ContractSQL.Voltmeter.NAME_TABLE,
                null,
                contentValuesOf(
                    ContractSQL.Voltmeter.ID_DETAIL to detail.id,
                    ContractSQL.Voltmeter.VOLTAGE to test.toDouble()
                )
            )
        } catch (e: SQLiteConstraintException) {

        }
    }

    private fun savePower(detail: PowerAdapter) {
        var test = detail.text
        if  (test.isEmpty()){
            test = 0.toString()
        }

        try {
            db.insertOrThrow(
                ContractSQL.Powers.NAME_TABLE,
                null,
                contentValuesOf(
                    ContractSQL.Powers.ID_DETAIL to detail.id,
                    ContractSQL.Powers.VOLTAGE to test.toDouble()
                )
            )
        } catch (e: SQLiteConstraintException) {

        }
    }


}
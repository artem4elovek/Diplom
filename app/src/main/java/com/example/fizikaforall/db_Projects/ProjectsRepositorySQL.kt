package com.example.fizikaforall.db_Projects

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import androidx.core.content.contentValuesOf
import com.example.fizikaforall.db_Projects.ContractSQL.ProjectsTable
import com.example.fizikaforall.manual.ManualProject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.lang.reflect.Field


class ProjectsRepositorySQL(
    private val db: SQLiteDatabase,
    private val ioDispatcher: CoroutineDispatcher,

):ProjectInterface {

   // private val reconstructFlow = MutableSharedFlow<Unit>(replay = 1).also { it.tryEmit(Unit) }

    private fun getAllProjects(): List<ManualProject> {
        val cursor = db.query(
            ProjectsTable.NAME_TABLE,
            arrayOf(ProjectsTable.ID_COLUMN, ProjectsTable.NAME_PROJECT),
            null, null, null, null, null
        )
        return cursor.use {
            val list = mutableListOf<ManualProject>()
            while (cursor.moveToNext()) {
                list.add(
                    ManualProject(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ProjectsTable.ID_COLUMN)),
                        cursor.getString(cursor.getColumnIndexOrThrow(ProjectsTable.NAME_PROJECT))
                    )
                )
            }
            return@use list
        }

    }

    private fun newProject(name: String): Int {
        var result : Int = 0
        try {
            db.insertOrThrow(
                ProjectsTable.NAME_TABLE,
                null,
                contentValuesOf(ProjectsTable.NAME_PROJECT to name)
            )
        } catch (e: SQLiteConstraintException) {
            result = -1
        }
        val cursor = db.query(
            ProjectsTable.NAME_TABLE,
            arrayOf(ProjectsTable.ID_COLUMN),
            "${ProjectsTable.NAME_PROJECT} = ?",
            arrayOf(name),
            null, null, null
        )
        return if (result == 0 ) cursor.use {
            if (cursor.count != 0) cursor.moveToFirst()
            cursor.getInt(cursor.getColumnIndexOrThrow(ProjectsTable.ID_COLUMN))
        }
        else result
    }

    override fun getProjects(): List<ManualProject> =  getAllProjects()
    override fun renameProjects(id: Int, name: String) {
      /*  db.update(ProjectsTable.NAME_TABLE,
            ProjectsTable.NAME_PROJECT,

            ProjectsTable.ID_COLUMN+"=?"
            )*/
    }

    override fun newProjects(name: String):Int =  newProject(name)
    override fun removeProjects(id:Int) {
        db.delete(
            ProjectsTable.NAME_TABLE,
            ProjectsTable.ID_COLUMN+"=?"
        ,arrayOf(id.toString())
        )
    }


    /* override suspend fun getProjects(onlyActive: Boolean): Flow<List<ManualProject>> {
        return combine(reconstructFlow) {_ ->
             getAllProjects()
         }.flowOn(ioDispatcher)
     }*/


}



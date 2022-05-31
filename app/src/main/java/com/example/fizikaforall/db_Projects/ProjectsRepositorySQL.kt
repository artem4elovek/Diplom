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

class ProjectsRepositorySQL(
    private val db: SQLiteDatabase,
    private val ioDispatcher: CoroutineDispatcher,

):ProjectInterface {

    private val reconstructFlow = MutableSharedFlow<Unit>(replay = 1).also { it.tryEmit(Unit) }

     fun getAllProjects(): List<ManualProject> {
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
        try {
            db.insertOrThrow(
                ProjectsTable.NAME_TABLE,
                null,
                contentValuesOf(ProjectsTable.NAME_PROJECT to name)
            )
        } catch (e: SQLiteConstraintException) {
         /*   val appException = NameProjectException()
            appException.initCause(e)
            throw appException*/
        }
        val cursor = db.query(
            ProjectsTable.NAME_TABLE,
            arrayOf(ProjectsTable.ID_COLUMN),
            "${ProjectsTable.NAME_PROJECT} = ?",
            arrayOf(name),
            null, null, null
        )
        return cursor.use {
            if (cursor.count != 0) cursor.moveToFirst()
            cursor.getInt(cursor.getColumnIndexOrThrow(ProjectsTable.ID_COLUMN))
        }
    }

    override suspend fun getProjects(onlyActive: Boolean): Flow<List<ManualProject>> {
       return combine(reconstructFlow) {_ ->
            getAllProjects()
        }.flowOn(ioDispatcher)
    }


}


/*
suspend fun <T> wrapSQLiteException(dispatcher: CoroutineDispatcher, block: suspend CoroutineScope.() -> T): T {
    try {
        return withContext(dispatcher, block)
    } catch (e: SQLiteException) {
        val appException = StorageException()
        appException.initCause(e)
        throw appException
    }
}*/

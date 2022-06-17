package com.example.fizikaforall.db_Projects

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object Repositories {
    private lateinit var  applicationContext: Context
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    fun init(context: Context) {
        applicationContext = context
    }

    private val database: SQLiteDatabase by lazy<SQLiteDatabase> {
        AppSQLiteHelper(applicationContext).writableDatabase
    }

    val projectsRepositorySQL : ProjectsRepositorySQL by lazy{
        ProjectsRepositorySQL(database,ioDispatcher)
    }

    val detailsRepositorySQL : DetailsRepositorySQL by lazy{
        DetailsRepositorySQL(database,ioDispatcher)
    }

}
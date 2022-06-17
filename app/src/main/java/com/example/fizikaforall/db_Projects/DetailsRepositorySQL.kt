package com.example.fizikaforall.db_Projects

import android.database.sqlite.SQLiteDatabase
import com.example.fizikaforall.draftsman.AllDetails
import kotlinx.coroutines.CoroutineDispatcher

class DetailsRepositorySQL(
    private val db: SQLiteDatabase,
    private val ioDispatcher: CoroutineDispatcher
    ):DetailsInterface  {
    override fun getAllDetails(): AllDetails {
        TODO("Not yet implemented")
    }

    override fun removeDetail(id: Int) {
        TODO("Not yet implemented")
    }

    override fun saveProject(id_Project: Int, allDetails: AllDetails) {
        TODO("Not yet implemented")
    }

    override fun renameProjects(id: Int, name: String) {
        TODO("Not yet implemented")
    }

    override fun newProjects(name: String): Int {
        TODO("Not yet implemented")
    }

}
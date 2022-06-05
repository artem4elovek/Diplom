package com.example.fizikaforall.db_Projects

import com.example.fizikaforall.manual.ManualProject
import kotlinx.coroutines.flow.Flow

interface ProjectInterface {


        //suspend fun getProjects(onlyActive: Boolean = false): Flow<List<ManualProject>>
        fun getProjects(): List<ManualProject>
        fun renameProjects(id:Int,name:String)
        fun newProjects(name:String): Int
        fun removeProjects(id:Int)

    }
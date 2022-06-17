package com.example.fizikaforall.db_Projects

import com.example.fizikaforall.draftsman.AllDetails
import com.example.fizikaforall.manual.ManualProject

interface DetailsInterface {
    fun getAllDetails(): AllDetails
    fun removeDetail(id:Int)
    fun saveProject(id_Project:Int, allDetails: AllDetails)
    fun renameProjects(id:Int,name:String)
    fun newProjects(name:String): Int

}
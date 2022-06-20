package com.example.fizikaforall.db_Projects

import android.content.Context
import com.example.fizikaforall.draftsman.AllDetails
import com.example.fizikaforall.manual.ManualProject

interface DetailsInterface {
    fun getAllDetails(id_Project: Int,context:Context): AllDetails
    //fun removeDetail(id:Int)
    fun saveProject(id_Project:Int, allDetails: AllDetails,context: Context)
    //fun renameProjects(id:Int,name:String)
    //fun newProjects(name:String): Int

}
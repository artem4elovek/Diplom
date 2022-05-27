package com.example.fizikaforall.manual

import java.util.*

typealias ManualProjectsListener  = (ManualProjects: List<ManualProject>)-> Unit

class ManualProjectService {
    private var manualProjects = mutableListOf<ManualProject>()
    private var manualProjectsListeners = mutableListOf<ManualProjectsListener>()

    init{
        var test = ManualProject(145,"kdsalfjlsdskjfhhaSDjkfhlkjahsdlkfjhalkjshdjdfl;ajdsl;fjl;kajsdf")
        manualProjects.add(test)
        test = ManualProject(1865,"kdsalfjlsjdfl;ajdsl;fjl;kajsdf")
        manualProjects.add(test)
        test = ManualProject(167,"kdsalfjlsjdfl;ajdsl;fjl;kajsdf")
        manualProjects.add(test)
        test = ManualProject(189,"kdsalfjlsjdfl;ajdsl;fjl;kajsdf")
        manualProjects.add(test)
        test = ManualProject(16,"kdsalfjlsjdfl;ajdsl;fjl;kajsdf")
        manualProjects.add(test)
        test = ManualProject(18,"kdsalfjlsjdfl;ajdsl;fjl;kajsdf")
        manualProjects.add(test)
        test = ManualProject(10,"kdsalfjlsjdfl;ajdsl;fjl;kajsdf")
        manualProjects.add(test)
    }

    fun addManualProjects(manualProject: ManualProject)
    {
        manualProjects.add(manualProject)
    }

    fun getManualProjects():List<ManualProject> = manualProjects

    fun deleteManualProjects(manualProject: ManualProject) {
        val indexToDelete =  manualProjects.indexOfFirst { it.id == manualProject.id }
        if (indexToDelete!= -1) {
            manualProjects.removeAt(indexToDelete)
            notifyChangers()
        }
    }

    fun moveManualProject(manualProject: ManualProject, moveBy: Int){
        val oldIndex = manualProjects.indexOfFirst { it.id == manualProject.id }
        if (oldIndex == -1)return
        val newIndex = oldIndex+moveBy
        if (newIndex<0 || newIndex>=manualProjects.size) return
        Collections.swap(manualProjects, oldIndex, newIndex)
        notifyChangers()
    }

    fun addListener(manualProjectsListener: ManualProjectsListener) {
        manualProjectsListeners.add(manualProjectsListener)
        manualProjectsListener.invoke(manualProjects)
    }

    fun removeListener(manualProjectsListener: ManualProjectsListener) {
        manualProjectsListeners.remove(manualProjectsListener)
    }

    private fun notifyChangers(){
        manualProjectsListeners.forEach{it.invoke(manualProjects)}
    }

}
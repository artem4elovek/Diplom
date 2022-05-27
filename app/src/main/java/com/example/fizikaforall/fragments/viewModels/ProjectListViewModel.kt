package com.example.fizikaforall.fragments.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fizikaforall.manual.ManualProject
import com.example.fizikaforall.manual.ManualProjectService
import com.example.fizikaforall.manual.ManualProjectsListener

class ProjectListViewModel(
    private val manualProjectService: ManualProjectService
): ViewModel() {
    private val _projects = MutableLiveData<List<ManualProject>>()
    val projects : LiveData<List<ManualProject>> = _projects


    init{
        loadProject()
    }

    override fun onCleared() {
        super.onCleared()
        manualProjectService.removeListener { listener }
    }

    fun loadProject(){
        manualProjectService.addListener { listener }
    }
    fun moveProject(manualProject: ManualProject, moveBy: Int){
        manualProjectService.moveManualProject(manualProject, moveBy)
    }
    fun deleteProject(manualProject: ManualProject){
        manualProjectService.deleteManualProjects(manualProject)
    }
    private val listener : ManualProjectsListener = {
        _projects.value = it
    }

}
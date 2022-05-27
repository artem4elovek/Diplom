package com.example.fizikaforall.fragments.FragmentUtils

import android.provider.Settings.Global.getString
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fizikaforall.App
import com.example.fizikaforall.R
import com.example.fizikaforall.fragments.viewModels.ProjectListViewModel

class ViewModelFactory(
    private val app: App
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when(modelClass){
            ProjectListViewModel::class.java->{
                ProjectListViewModel(app.manualProjectService)
            }
            else->{
                throw IllegalAccessException(R.string.viewError.toString())
            }
        }
        return viewModel as T
    }

}

fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)
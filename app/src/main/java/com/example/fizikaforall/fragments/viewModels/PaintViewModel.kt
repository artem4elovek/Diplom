package com.example.fizikaforall.fragments.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fizikaforall.draftsman.AllDetails
import com.example.fizikaforall.draftsman.Cable
import com.example.fizikaforall.draftsman.DetailPrint

class PaintViewModel :ViewModel() {
    private var _printer: MutableLiveData<AllDetails> =MutableLiveData()
    val printer : LiveData<AllDetails>
    get() = _printer

    init{
        _printer.value = AllDetails(mutableListOf<DetailPrint>(), mutableListOf<Cable>())
    }
    fun getAllDetails() =_printer
    fun updateAllDetails(lists:AllDetails){
        _printer.value = lists
    }

}
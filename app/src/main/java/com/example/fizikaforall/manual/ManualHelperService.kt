package com.example.fizikaforall.manual

import java.util.*

typealias ManualHelpersListener = (ManualHelpers: List<ManualHelper>)-> Unit

class ManualHelperService {
    private var manualHelpers = mutableListOf<ManualHelper>()
    private var manualHelpersListeners = mutableListOf<ManualHelpersListener>()

    init {}

    fun getManualHelpers(): List<ManualHelper> = manualHelpers

    fun deleteManualHelpers(manualHelper: ManualHelper) {
        val indexToDelete = manualHelpers.indexOfFirst { it.id == manualHelper.id }
        if (indexToDelete != -1) {
            manualHelpers.removeAt(indexToDelete)
            notifyChangers()
        }
    }

    fun moveManualHelper(manualHelper: ManualHelper, moveBy: Int) {
        val oldIndex = manualHelpers.indexOfFirst { it.id == manualHelper.id }
        if (oldIndex == -1) return
        val newIndex = oldIndex + moveBy
        if (newIndex < 0 || newIndex >= manualHelpers.size) return
        Collections.swap(manualHelpers, oldIndex, newIndex)
        notifyChangers()
    }

    fun addListener(manualHelpersListener: ManualHelpersListener) {
        manualHelpersListeners.add(manualHelpersListener)
    }

    fun removeListener(manualHelpersListener: ManualHelpersListener) {
        manualHelpersListeners.remove(manualHelpersListener)
    }

    private fun notifyChangers() {
        manualHelpersListeners.forEach { it.invoke(manualHelpers) }
    }
}
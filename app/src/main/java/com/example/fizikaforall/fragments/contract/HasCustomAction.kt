package com.example.fizikaforall.fragments.contract

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface HasCustomAction {
    fun getCustomAction():List<CustomAction>
}

class CustomAction(
    @DrawableRes val iconRes:Int,
    @StringRes val textRes: Int,
    val onCustomAction: Runnable
)
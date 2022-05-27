package com.example.fizikaforall.fragments.contract

import androidx.annotation.StringRes

interface HasCustomTitle {
    @StringRes
    fun getTitleRes():Int
}
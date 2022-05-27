package com.example.fizikaforall

import android.app.Application
import com.example.fizikaforall.manual.ManualHelperService
import com.example.fizikaforall.manual.ManualProjectService

class App: Application() {
    val manualProjectService = ManualProjectService()
    val manualHelperService = ManualHelperService()
}
package com.example.fizikaforall.draftsman

import android.view.SurfaceHolder

class PaintEngine(private val surfaceHolder: SurfaceHolder) {



    fun moveLine(x:Float,y:Float){
        var projectCanvasView = ProjectCanvasView()
        projectCanvasView.x=x
        projectCanvasView.y=y
        reloadHolder (projectCanvasView)
    }

    private fun reloadHolder (projectCanvasView: ProjectCanvasView)
    {
        surfaceHolder.addCallback(projectCanvasView)
    }



}
package com.example.fizikaforall.draftsman

import android.view.SurfaceHolder

class PaintEngine(private val surfaceHolder: SurfaceHolder) {

    private val surfaceHolder1: SurfaceHolder = surfaceHolder

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
package com.example.fizikaforall.fragments

import android.app.ActionBar
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fizikaforall.adapters.HelperAdapter
import com.example.fizikaforall.databinding.FragmentDrawingWorkbenchBinding
import com.example.fizikaforall.databinding.FragmentRecuclerBinding
import com.example.fizikaforall.fragments.contract.CustomAction
import com.example.fizikaforall.fragments.contract.HasCustomAction
import com.example.fizikaforall.fragments.contract.HasCustomTitle
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.GravityCompat
import com.example.fizikaforall.R
import com.example.fizikaforall.draftsman.PaintEngine
import com.example.fizikaforall.draftsman.ProjectCanvasView
import com.example.fizikaforall.draftsman.TouchHandler


class DrawingWorkbenchFragment:Fragment(),HasCustomAction{
    private lateinit var binding: FragmentDrawingWorkbenchBinding
    private  var surfaceCanvas = ProjectCanvasView()
    private lateinit var engine :PaintEngine
    private lateinit var touchHandler: TouchHandler

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentDrawingWorkbenchBinding.inflate(inflater,container,false)
        binding.bottomNavigator.inflateMenu(com.example.fizikaforall.R.menu.menu_drawing_bottom)
        binding.PaintDesk.holder.addCallback(ProjectCanvasView())
        touchHandler = TouchHandler(requireActivity())

        binding.bottomNavigator.setOnNavigationItemSelectedListener{ it->
            when(it.itemId)
            {
                R.id.resistorItem -> touchHandler.resistorChoice()
                R.id.voltmeterItem ->touchHandler.voltmeterChoice()
                R.id.cableItem ->touchHandler.cableChoice()
                R.id.powerItem -> touchHandler.powerChoice()
            }
            true
        }

        binding.PaintDesk.setOnTouchListener{_, event ->touchHandler.runTouch(event)
            true
        }


        return binding.root
    }
    private fun getIdProject():Long = requireArguments().getLong(DrawingWorkbenchFragment.ARG_ID_PROJECT)

    companion object{

        @JvmStatic
        private val ARG_ID_PROJECT="ARG_ID_PROJECT"

        @JvmStatic
        fun newInstance(counterValue: Long): DrawingWorkbenchFragment{
            val args = Bundle().apply {
                putLong(ARG_ID_PROJECT,counterValue)
            }
            val fragment =  DrawingWorkbenchFragment()
            fragment.arguments= args
            return fragment
        }
    }


   private fun aaa(str:String){
        Toast.makeText(requireActivity(), str, Toast.LENGTH_SHORT).show()
    }

    override fun getCustomAction(): List<CustomAction> = listOf(CustomAction(
        iconRes = R.drawable.ic_arrow_back,
        textRes = R.string.back,
        onCustomAction = Runnable {
            engine.moveLine(449f,710f)
        }
    ),CustomAction(
        iconRes = R.drawable.ic_arrow_forward,
        textRes = R.string.back,
        onCustomAction = Runnable {

        }
    ),CustomAction(
        iconRes = R.drawable.ic_save,
        textRes = R.string.back,
        onCustomAction = Runnable {

        }
    ))
}
package com.example.fizikaforall.fragments

import android.app.ActionBar
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
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
import com.example.fizikaforall.R
import com.example.fizikaforall.draftsman.ProjectCanvasView


class DrawingWorkbenchFragment:Fragment(){
    private lateinit var binding: FragmentDrawingWorkbenchBinding
    private  var surfaceCanvas = ProjectCanvasView()

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentDrawingWorkbenchBinding.inflate(inflater,container,false)
        binding.bottomNavigator.inflateMenu(com.example.fizikaforall.R.menu.menu_drawing_bottom)
        toolBarConstructor(CustomAction(
            iconRes = R.drawable.ic_arrow_back,
            textRes = R.string.back,
            onCustomAction = Runnable {

            }
        ))
        toolBarConstructor(CustomAction(
            iconRes = R.drawable.ic_arrow_forward,
            textRes = R.string.back,
            onCustomAction = Runnable {

            }
        ))
        toolBarConstructor(CustomAction(
            iconRes = R.drawable.ic_save,
            textRes = R.string.back,
            onCustomAction = Runnable {

            }
        ))
        //binding.PaintDesk.setBackgroundColor(Color.WHITE)
        binding.PaintDesk.holder.addCallback(surfaceCanvas)

       // surface .draw(Canvas(Bitmap.createBitmap(3,2,Bitmap.Config.ARGB_8888)))
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
    private fun toolBarConstructor( action: CustomAction) {
       // binding.toolbar.menu.clear()

        val iconDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(context!!,action.iconRes)!!)
        iconDrawable.setTint(Color.WHITE)

        val menuItem = binding.toolbar.menu.add(action.textRes)
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        menuItem.icon = iconDrawable
        menuItem.setOnMenuItemClickListener {
            action.onCustomAction.run()
            return@setOnMenuItemClickListener true
        }
    }

    //override fun getTitleRes(): Int = com.example.fizikaforall.R.string.descriptionError

}
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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.fizikaforall.R
import com.example.fizikaforall.draftsman.*
import com.example.fizikaforall.fragments.viewModels.PaintViewModel


class DrawingWorkbenchFragment:Fragment(),HasCustomAction{
    private lateinit var binding: FragmentDrawingWorkbenchBinding
    private lateinit var surfaceCanvas : ProjectCanvasView
    private  var list = AllDetails(mutableListOf<DetailPrint>(), mutableListOf<Cable>())
    private val paintViewModel by lazy { ViewModelProviders.of(requireActivity()).get(PaintViewModel::class.java)}
    private lateinit var engine :PaintEngine
    private lateinit var touchHandler: TouchHandler

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentDrawingWorkbenchBinding.inflate(inflater,container,false)

        binding.bottomNavigator.inflateMenu(R.menu.menu_drawing_bottom)
        engine = PaintEngine(requireContext())
        surfaceCanvas = ProjectCanvasView(engine)
        binding.PaintDesk.holder.addCallback(surfaceCanvas)
        touchHandler = TouchHandler(engine,requireActivity(),list,parentFragmentManager)
        paintViewModel.getAllDetails().observe(
            requireActivity(), Observer {
                it?.let {
                    list = it
                }
            }
        )

        binding.bottomNavigator.setOnNavigationItemSelectedListener{ it->
            when(it.itemId)
            {
                R.id.resistorItem ->  touchHandler.resistorChoice()
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
    private fun getIdProject():Int = requireArguments().getInt(DrawingWorkbenchFragment.ARG_ID_PROJECT)

    companion object{

        @JvmStatic
        private val ARG_ID_PROJECT="ARG_ID_PROJECT"

        @JvmStatic
        fun newInstance(counterValue: Int): DrawingWorkbenchFragment{
            val args = Bundle().apply {
                putInt(ARG_ID_PROJECT,counterValue)
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



    override fun onDestroyView() {
        super.onDestroyView()
        surfaceCanvas.endCoroutine()
        paintViewModel.updateAllDetails(list)

    }
/*

    fun toObject(stringValue: String): MutableList<DetailPrint> {
        return JSON.parse(Field.serializer(), stringValue)
    }

    fun toJson(field: Field): String {
        // Обратите внимание, что мы вызываем Serializer, который автоматически сгенерирован из нашего класса
        // Сразу после того, как мы добавили аннотацию @Serializer
        return JSON.stringify(Field.serializer(), field)
    }


*/

}
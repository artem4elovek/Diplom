package com.example.fizikaforall.fragments

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fizikaforall.databinding.FragmentDrawingWorkbenchBinding
import com.example.fizikaforall.fragments.contract.CustomAction
import com.example.fizikaforall.fragments.contract.HasCustomAction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fizikaforall.R
import com.example.fizikaforall.draftsman.*
import com.example.fizikaforall.fragments.contract.navigator
import com.example.fizikaforall.fragments.viewModels.PaintViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog


class DrawingWorkbenchFragment:Fragment(),HasCustomAction{
    private lateinit var binding: FragmentDrawingWorkbenchBinding
    private lateinit var surfaceCanvas : ProjectCanvasView
    private  lateinit var  list: AllDetails // = navigator().getRepository().detailsRepositorySQL.getAllDetails(getIdProject())
    private val paintViewModel by lazy { ViewModelProviders.of(requireActivity()).get(PaintViewModel::class.java)}
    private lateinit var engine :PaintEngine
    private var idProject = 0
    private lateinit var touchHandler: TouchHandler

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentDrawingWorkbenchBinding.inflate(inflater,container,false)
        idProject = getIdProject()
        aaa(idProject.toString())
        list = navigator().getRepository().detailsRepositorySQL.getAllDetails(idProject,context!!)
        if (list == null){
            list = AllDetails(mutableListOf<DetailPrint>(), mutableListOf<Cable>())
        }
        binding.bottomNavigator.inflateMenu(R.menu.menu_drawing_bottom)
        engine = PaintEngine(requireContext())
        surfaceCanvas = ProjectCanvasView(engine)
        binding.PaintDesk.holder.addCallback(surfaceCanvas)
        var a:(Int)->Unit = {it-> sheetDialog(it)}
        touchHandler = TouchHandler(engine,requireActivity(),list,parentFragmentManager,a)
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
                R.id.bucketItem ->touchHandler.delChoice()
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
           // Toast.makeText(context,touchHandler.giveList().details[0].id.toString(),Toast.LENGTH_SHORT).show()
        navigator().getRepository().detailsRepositorySQL.saveProject(idProject,touchHandler.giveList(), context!!)
        }
    ))



    override fun onDestroyView() {
        super.onDestroyView()
        surfaceCanvas.endCoroutine()
        paintViewModel.updateAllDetails(list)

    }


    private fun sheetDialog(id:Int ) {
        var text = touchHandler.text(id)
        val dialog = BottomSheetDialog(requireContext())
        val bottomSheet = layoutInflater.inflate(R.layout.bottom_sheet_fragment, null)
        bottomSheet.setOnClickListener {
            dialog.dismiss() }
        dialog.setContentView(bottomSheet)
        bottomSheet.findViewById<ImageButton>(R.id.angleButton).setOnClickListener {
            touchHandler.angleDetail(id)

        }
        val edittext = bottomSheet.findViewById<EditText>(R.id.edit_text_details)
        val saveButton = bottomSheet.findViewById<Button>(R.id.saveText)
        if(text == null){
            edittext.visibility =   View.GONE
            saveButton.visibility  =   View.GONE
        }
        else edittext.setText(text)
        saveButton.setOnClickListener {
            touchHandler.newText(id,edittext.text.toString())
            dialog.dismiss()
        }
        dialog.show()
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
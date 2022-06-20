package com.example.fizikaforall.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.fizikaforall.R
import com.example.fizikaforall.databinding.BottomSheetFragmentBinding
import com.example.fizikaforall.draftsman.DetailPrint
import com.example.fizikaforall.draftsman.TextPlaice
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailDialogSheetFragment : BottomSheetDialogFragment() {
   // private val detailPrint: DetailPrint
   //     get() = requireArguments().get(ALL_PROJECTS_KEY) as DetailPrint


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val dialogBinding =  BottomSheetFragmentBinding.inflate(layoutInflater)
       // dialogBinding.editTextDetails.setText(if (detailPrint is TextPlaice)detailPrint.text else "")
        dialogBinding.saveText.setOnClickListener {
            val nameProject = dialogBinding.editTextDetails.text.toString()
            if (nameProject.trim().isEmpty()) {
                dialogBinding.editTextDetails.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.light_wine
                    )
                ) 
            } else {
                parentFragmentManager.setFragmentResult(
                   REQUEST_KEY, bundleOf(
                        INPUT_KEY to nameProject
                    )
                )
                dismiss()
            }
            dialogBinding.angleButton.setOnClickListener {
               // if(detailPrint.angle<270)
              //  detailPrint.angle += 90
               // else detailPrint.angle= 0
            }
        }
        return dialogBinding.root
    }

    companion object{
        @JvmStatic val TAG = DetailDialogSheetFragment::class.java.simpleName
        @JvmStatic val REQUEST_KEY = "$TAG:defaultRequestKey"
        @JvmStatic val INPUT_KEY ="INPUT_KEY"
        @JvmStatic val INT_KEY ="INT_KEY"
        @JvmStatic val ALL_PROJECTS_KEY ="ALL_PROJECTS"

        fun show(manager: FragmentManager, detailPrint: DetailPrint) {
            val dialogFragment = DetailDialogSheetFragment()
            dialogFragment.arguments = bundleOf(ALL_PROJECTS_KEY to detailPrint)
            dialogFragment.show(manager,TAG)
        }
      /*  fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (String) -> Unit) {
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner, FragmentResultListener { _, result ->
                result.getString(INPUT_KEY)?.let { listener.invoke(it) }
            })
        }*/
    /*    fun angleListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (String) -> Unit) {
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner, FragmentResultListener { _, result ->
                result.getString(INT_KEY)?.let { listener.invoke(it) }
            })*/
       // }
    }



}
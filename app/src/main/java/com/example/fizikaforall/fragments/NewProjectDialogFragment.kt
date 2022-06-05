package com.example.fizikaforall.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.fizikaforall.R
import com.example.fizikaforall.databinding.ItemProjectCreatedBinding
import com.example.fizikaforall.manual.ManualProject

class NewProjectDialogFragment : DialogFragment() {

    private val projects: List<String>
        get() = requireArguments().get(ALL_PROJECTS_KEY) as List<String>

    private var listProjects = mutableListOf<String>()
    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels).toInt()
        val height = (resources.displayMetrics.heightPixels).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dialogBinding =  ItemProjectCreatedBinding.inflate(layoutInflater)
        var projectName:String = resources.getString(R.string.standardNameProject)
        var i = 1
        var test = true
            while(projects.contains(projectName + i.toString())) i += 1

        dialogBinding.nameProject.setText(projectName.plus(i.toString()))
        dialogBinding.buttonSave.setOnClickListener{
            val nameProject = dialogBinding.nameProject.text.toString()
            if (projects.contains(nameProject.trim()) and  nameProject.trim().isNotEmpty()) {
                dialogBinding.nameProject.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.light_wine
                    )
                )

            }else{    parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(INPUT_KEY to nameProject))
                dismiss()}
        }
        dialogBinding.closeButton.setOnClickListener { dismiss() }
        return dialogBinding.root
    }


    fun setTestsProject(exportList: List<String>) {listProjects.addAll(exportList) }

  /*  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBinding =  ItemProjectCreatedBinding.inflate(layoutInflater)
        val clickListener = DialogInterface.OnClickListener { _, buttonClick ->
            parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(RESPONSE_KEY to buttonClick))
        }

        dialogBinding.buttonSave.setOnClickListener(this)
        dialogBinding.closeButton.setOnClickListener {  Log.d(TAG,"EEEESSS") }

        return AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.ProjectSave) { dialog, _ ->
                val index = (dialog as AlertDialog).listView.checkedItemPosition
                val volume = volumeItems.values[index]
                parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(RESPONSE_KEY to volume))
            }
            .create()
    }

*/



    companion object{
        @JvmStatic val TAG = NewProjectDialogFragment::class.java.simpleName
        @JvmStatic val REQUEST_KEY = "$TAG:defaultRequestKey"
        @JvmStatic val INPUT_KEY ="INPUT_KEY"
        @JvmStatic val ALL_PROJECTS_KEY ="ALL_PROJECTS"

        fun show(manager: FragmentManager, projects: List<String>) {
            val dialogFragment = NewProjectDialogFragment()
            dialogFragment.arguments = bundleOf(ALL_PROJECTS_KEY to projects)
            dialogFragment.show(manager, TAG)
        }
        fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (String) -> Unit) {
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner, FragmentResultListener { _, result ->
                result.getString(INPUT_KEY)?.let { listener.invoke(it) }
            })
        }

    }
}
package com.example.fizikaforall.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fizikaforall.MainActivity
import com.example.fizikaforall.R
import com.example.fizikaforall.adapters.ArchiveActionListener
import com.example.fizikaforall.databinding.FragmentDrawingWorkbenchBinding
import com.example.fizikaforall.databinding.FragmentRecuclerBinding
import com.example.fizikaforall.db_Projects.AppSQLiteHelper
import com.example.fizikaforall.db_Projects.Repositories
import com.example.fizikaforall.fragments.FragmentUtils.factory
import com.example.fizikaforall.fragments.contract.CustomAction
import com.example.fizikaforall.fragments.contract.HasCustomAction
import com.example.fizikaforall.fragments.contract.navigator
import com.example.fizikaforall.manual.ManualProjectsListener
import com.example.fizikaforall.fragments.viewModels.ProjectListViewModel
import com.example.fizikaforall.manual.ManualProject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.fizikaforall.adapters.ArchiveAdapter as ArchiveAdapter1

class ArchiveFragment: Fragment(),HasCustomAction {
    private lateinit var binding: FragmentRecuclerBinding
    private lateinit var adapter : ArchiveAdapter1
    private lateinit var projectsList : List<ManualProject>


    private val viewModel: ProjectListViewModel by viewModels{factory()}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRecuclerBinding.inflate(inflater,container,false)
        adapter = ArchiveAdapter1(object : ArchiveActionListener {
            override fun onProjectSelected(manualProject: ManualProject) {
                launchWorkbench(manualProject.id)
            }
            override fun onProjectDelete(manualProject: ManualProject) {
                navigator().getRepository().projectsRepositorySQL.removeProjects(manualProject.id)
                projectsList =  navigator().getRepository().projectsRepositorySQL.getProjects()
                adapter.projects = projectsList
            }
            override fun onProjectMove(manualProject: ManualProject,moveBy: Int) {
                Toast.makeText(activity, "dadfa",Toast.LENGTH_SHORT).show()
            }
        })
       /* var bd = Repositories
        bd.init(requireContext())
       */ //bd.testCreatedbd()
        //var test1= ManualProject(167,"kdsalfjlsjdfl;ajdsl;fjl;kajsdf")
       /// test.add(test1)
       // GlobalScope.launch {
        projectsList =  navigator().getRepository().projectsRepositorySQL.getProjects()
        //}
        /*testList.add(test)
        test
        test = ManualProject(167,"kdsalfjlsjdfl;ajdsl;fjl;kajsdf")
        testList.add(test)
        test = ManualProject(189,"kdsalfjlsjdfl;ajdsl;fjl;kajsdf")
        testList.add(test)
        test = ManualProject(16,"kdsalfjlsjdfl;ajdsl;fjl;kajsdf")
        testList.add(test)
        test = ManualProject(18,"kdsalfjlsjdfl;ajdsl;fjl;kajsdf")
        testList.add(test)
        test = ManualProject(10,"kdsalfjlsjdfl;ajdsl;fjl;kajsdf")
        testList.add(test)*/

        viewModel.projects.observe(viewLifecycleOwner, Observer{
            adapter.projects = it
        })
        adapter.projects = projectsList
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        //binding.toolbar.title = getString(R.string.app_name)
        return binding.root
    }

    private fun goBack() {
       navigator().goBack()
    }

    private fun goToArchive() {
        navigator().goToArchive()
    }
    private fun goToHelperScreen()
    {
        navigator().showHelperScreen()
    }

    private fun getCounterValue():Int = requireArguments().getInt(ARG_COUNTER_VALUE)

    private fun getQuote():String =requireArguments().getString(ARG_QUOTE)!!

    private  fun launchWorkbench(id: Int){
        navigator().showDrawingWorkbench(id)
    }

    private val manualProjectsListener:ManualProjectsListener= {
        adapter.projects=it
    }
    companion object{
        @JvmStatic
        private val ARG_COUNTER_VALUE="ARG_COUNTER_VALUE"
        @JvmStatic
        private val ARG_QUOTE="ARG_QUOTE"
        @JvmStatic
        fun newInstance(counterValue: Int,quote: String):HelperFragment{
            val args = Bundle().apply {
                putInt(ARG_COUNTER_VALUE,counterValue)
                putString(ARG_QUOTE,quote)
            }
            val fragment = HelperFragment()
            fragment.arguments= args
            return fragment
        }
    }

    private fun cancellation()
    {}
    private fun createdProject(){
        NewProjectDialogFragment.show(parentFragmentManager,projectsList.map{it.text}.toList())
        NewProjectDialogFragment.setupListener(parentFragmentManager,this){
            var  result = navigator().getRepository().projectsRepositorySQL.newProjects(it)
            if (result >0){launchWorkbench(result)}
        }


      //  launchWorkbench(navigator().getRepository().projectsRepositorySQL.newProjects("Проверочный"))
    }

   /* private fun setupSimpleDialogFragmentListener() {
        parentFragmentManager.setFragmentResultListener(NewProjectDialogFragment().REQUEST_KEY, this, FragmentResultListener { _, result ->
            val which = result.getInt(NewProjectDialogFragment().KEY_RESPONSE)
            when (which) {
                DialogInterface. -> showToast(R.string.uninstall_confirmed)
                DialogInterface.BUTTON_NEGATIVE -> showToast(R.string.uninstall_rejected)
                DialogInterface.BUTTON_NEUTRAL -> {
                    showToast(R.string.uninstall_ignored)
                }
            }
        })
    }*/


    override fun getCustomAction(): List<CustomAction> = listOf(CustomAction(
            iconRes = R.drawable.ic_add,
            textRes = R.string.addProject,
            onCustomAction = Runnable {
                createdProject()
            }
    ))

}
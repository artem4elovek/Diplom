package com.example.fizikaforall.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fizikaforall.MainActivity
import com.example.fizikaforall.R
import com.example.fizikaforall.adapters.ArchiveActionListener
import com.example.fizikaforall.databinding.FragmentDrawingWorkbenchBinding
import com.example.fizikaforall.databinding.FragmentRecuclerBinding
import com.example.fizikaforall.fragments.FragmentUtils.factory
import com.example.fizikaforall.fragments.contract.CustomAction
import com.example.fizikaforall.fragments.contract.HasCustomAction
import com.example.fizikaforall.fragments.contract.navigator
import com.example.fizikaforall.manual.ManualProjectsListener
import com.example.fizikaforall.fragments.viewModels.ProjectListViewModel
import com.example.fizikaforall.manual.ManualProject
import com.example.fizikaforall.adapters.ArchiveAdapter as ArchiveAdapter1

class ArchiveFragment: Fragment(),HasCustomAction {
    private lateinit var binding: FragmentRecuclerBinding
    private lateinit var adapter : ArchiveAdapter1
    private val testList  = mutableListOf<ManualProject>()

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
                Toast.makeText(activity, "dadfa",Toast.LENGTH_SHORT).show()
            }
            override fun onProjectMove(manualProject: ManualProject,moveBy: Int) {
                Toast.makeText(activity, "dadfa",Toast.LENGTH_SHORT).show()
            }
        })
        var test = ManualProject(145,"kdsalfjlsjdfl;ajdsl;fjl;kajsdf")
        testList.add(test)
        test = ManualProject(1865,"kdsalfjlsjdfl;ajdsl;fjl;kajsdf")
        testList.add(test)
        test = ManualProject(167,"kdsalfjlsjdfl;ajdsl;fjl;kajsdf")
        testList.add(test)
        test = ManualProject(189,"kdsalfjlsjdfl;ajdsl;fjl;kajsdf")
        testList.add(test)
        test = ManualProject(16,"kdsalfjlsjdfl;ajdsl;fjl;kajsdf")
        testList.add(test)
        test = ManualProject(18,"kdsalfjlsjdfl;ajdsl;fjl;kajsdf")
        testList.add(test)
        test = ManualProject(10,"kdsalfjlsjdfl;ajdsl;fjl;kajsdf")
        testList.add(test)

        viewModel.projects.observe(viewLifecycleOwner, Observer{
            adapter.projects = it
        })
        adapter.projects = testList
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.toolbar.title = getString(R.string.app_name)
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

    private  fun launchWorkbench(id: Long){
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

    override fun getCustomAction(): CustomAction {
        return CustomAction(
            iconRes = R.drawable.ic_arrow_back,
            textRes = R.string.back,
        onCustomAction = Runnable {
            cancellation()
        }
        )
    }
}
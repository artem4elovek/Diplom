package com.example.fizikaforall.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fizikaforall.App
import com.example.fizikaforall.MainActivity
import com.example.fizikaforall.adapters.HelperAdapter
import com.example.fizikaforall.databinding.ActivityMainBinding
import com.example.fizikaforall.databinding.FragmentRecuclerBinding
import com.example.fizikaforall.manual.ManualHelperService

class HelperFragment: Fragment() {
    private lateinit var binding: FragmentRecuclerBinding
    private lateinit var adapter :HelperAdapter

    //private val manualHelperService : ManualHelperService
      //  get() = (applicationContext as App).manualHelperService

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentRecuclerBinding.inflate(inflater,container,false)
        adapter = HelperAdapter()
       // val layoutManager = LinearLayoutManager(this)
      //  binding.recyclerView.layoutManager = layoutManager
      //  binding.recyclerView.adapter = adapter
        return super.onCreateView(inflater, container, savedInstanceState)
    }




    private fun goBack() {
        requireActivity().onBackPressed()
    }

    private fun getCountervalue():Int = requireArguments().getInt(ARG_COUNTER_VALUE)

    private fun getQuote():String =requireArguments().getString(ARG_QUOTE)!!

    private fun launchNext(){
        val fragment = HelperFragment.newInstance(counterValue = (requireActivity() as MainActivity).getScreensCount()+1, quote = (requireActivity() as MainActivity).createQuote()  )
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

}
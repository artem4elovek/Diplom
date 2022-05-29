package com.example.fizikaforall.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fizikaforall.App
import com.example.fizikaforall.MainActivity
import com.example.fizikaforall.R
import com.example.fizikaforall.adapters.HelperAdapter
import com.example.fizikaforall.databinding.ActivityMainBinding
import com.example.fizikaforall.databinding.FragmentRecuclerBinding
import com.example.fizikaforall.fragments.contract.HasCustomTitle
import com.example.fizikaforall.manual.ManualHelperService

class HelperFragment: Fragment(),HasCustomTitle {
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

    override fun getTitleRes(): Int  = R.string.helperTitle

}
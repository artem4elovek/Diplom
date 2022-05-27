package com.example.fizikaforall.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.fizikaforall.R
import com.example.fizikaforall.databinding.ItemHelperBinding
import com.example.fizikaforall.manual.ManualHelper

class HelperAdapter: RecyclerView.Adapter<HelperAdapter.HelperViewHolder>() {

    var helpers : List<ManualHelper> = emptyList()
    set(newValue) {
        field = newValue
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelperViewHolder {
       val  inflater = LayoutInflater.from(parent.context)
        val binding = ItemHelperBinding.inflate(inflater, parent, false)
        return HelperViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HelperViewHolder, position: Int) {
       val helper = helpers[position]
        with(holder.binding){
            themeHelperText.text = helper.theme
            helperText.text = helper.text
            if(helper.photo.isNotBlank()) {
                helperImage.setImageResource(R.drawable.ic_broken_image)
            }
            else {
                helperImage.isVisible = false
            }

        }
    }

    override fun getItemCount(): Int = helpers.size

    class HelperViewHolder(
        val binding: ItemHelperBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
package com.example.fizikaforall.adapters

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.fizikaforall.R
import com.example.fizikaforall.databinding.ItemArchiveBinding
import com.example.fizikaforall.databinding.ItemHelperBinding
import com.example.fizikaforall.manual.ManualHelper
import com.example.fizikaforall.manual.ManualProject


interface  ArchiveActionListener{
    fun onProjectSelected(manualProject: ManualProject)
    fun onProjectDelete(manualProject: ManualProject)
    fun onProjectMove(manualProject: ManualProject, moveBy: Int)

}

class ArchiveAdapter(
    private val archiveActionListener:ArchiveActionListener
): RecyclerView.Adapter<ArchiveAdapter.ArchiveViewHolder>(), View.OnClickListener {

    var projects : List<ManualProject> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }
    override fun onClick(v: View)
    {
        val project = v.tag as ManualProject
        when(v.id){
            R.id.optionButton->{
                showOption(v)
            }
            else ->{
                archiveActionListener.onProjectSelected(project)
            }
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchiveViewHolder {
        val  inflater = LayoutInflater.from(parent.context)
        val binding = ItemArchiveBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.optionButton.setOnClickListener(this)
        return ArchiveViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ArchiveViewHolder, position: Int) {
        val project = projects[position]
        with(holder.binding){
            holder.itemView.tag = project
            optionButton.tag = project
            archiveText.text = project.text
        }
    }
    override fun getItemCount(): Int = projects.size
    class ArchiveViewHolder(
        val binding: ItemArchiveBinding
    ) : RecyclerView.ViewHolder(binding.root)



    private fun showOption(v: View) {
        val context = v.context
        val popupMenu = PopupMenu(v.context,v)
        val project = v.tag as ManualProject
        val position = projects.indexOfFirst { it.id == project.id }
        popupMenu.menu.add(0,ID_MOVE_UP, Menu.NONE,context.getString(R.string.moveUp)).apply {
            isEnabled = position>0
        }
        popupMenu.menu.add(1,ID_MOVE_DOWN, Menu.NONE,context.getString(R.string.moveDown)).apply {
            isEnabled = position< projects.size-1
        }
        popupMenu.menu.add(2,ID_DEL, Menu.NONE,context.getString(R.string.delete))
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                ID_MOVE_UP -> {
                    archiveActionListener.onProjectMove(project,-1)
                }
                ID_MOVE_DOWN->{
                    archiveActionListener.onProjectMove(project,1)
                }
                ID_DEL->{
                    archiveActionListener.onProjectDelete(project)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    companion object{
        private const val ID_MOVE_UP = 1
        private const val ID_MOVE_DOWN = 2
        private const val ID_DEL = 3

    }

}
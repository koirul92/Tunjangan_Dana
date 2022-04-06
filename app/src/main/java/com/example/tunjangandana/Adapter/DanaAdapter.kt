package com.example.tunjangandana.Adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tunjangandana.MainActivity
import com.example.tunjangandana.databinding.DanaListBinding
import com.example.tunjangandana.room.BobotDatabase
import com.example.tunjangandana.room.Dana
import kotlinx.coroutines.GlobalScope
import androidx.lifecycle.lifecycleScope
import com.example.tunjangandana.Fragment.HomeFragment
import kotlinx.coroutines.async

class DanaAdapter (
    private val listDana: List<Dana>,
    private val delete: (Dana)->Unit,
    private val edit: (Dana)->Unit ):RecyclerView.Adapter<DanaAdapter.ViewHolder>() {


    class ViewHolder(val binding : DanaListBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DanaListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding){
            tvKeterangan.text = listDana[position].keterangan
            tvGoals.text = listDana[position].danaGoals.toString()
            tvDanaMonth.text = listDana[position].danaMonth.toString()
/*
            ivEdit.setOnClickListener {
                val activity = it.context as MainActivity
                val dialogFragment = EditFragment()
                dialogFragment.show(activity.supportFragmentManager, null)
            }*/
            ivDelete.setOnClickListener {
                delete.invoke(listDana[position])
            }


        }
    }

    override fun getItemCount(): Int {
        return listDana.size
    }
}
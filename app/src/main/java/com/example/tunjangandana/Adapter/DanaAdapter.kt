package com.example.tunjangandana.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tunjangandana.databinding.DanaListBinding
import com.example.tunjangandana.room.Dana

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
            ivEdit.setOnClickListener {
                edit.invoke(listDana[position])
            }
            ivDelete.setOnClickListener {
                delete.invoke(listDana[position])
            }


        }
    }

    override fun getItemCount(): Int {
        return listDana.size
    }
}
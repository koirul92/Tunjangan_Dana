package com.example.tunjangandana.Fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.tunjangandana.R
import com.example.tunjangandana.databinding.FragmentAddBinding
import com.example.tunjangandana.room.BobotDatabase
import com.example.tunjangandana.room.Dana
import kotlinx.coroutines.*
import java.lang.IllegalStateException

class AddFragment (private val listDana: (Dana)->Unit) : DialogFragment() {

    private var _binding: FragmentAddBinding? = null
    private var mDb: BobotDatabase? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        mDb = BobotDatabase.getInstance(requireContext())
        _binding = FragmentAddBinding.inflate(layoutInflater)
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
            binding.apply {
                btnSave.setOnClickListener {
                    val goals: Int = binding.etGoals.text.toString().toInt()
                    val month: Int = binding.etMonth.text.toString().toInt()
                    val permonth: Int = goals/month
                    val dana = Dana(
                        null, binding.etKeterangan.text.toString(), goals,permonth
                    )
                    listDana(dana)
                    dialog?.dismiss()
                }
            }
            builder.create()
        }?:throw IllegalStateException("Activity cannot be null")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
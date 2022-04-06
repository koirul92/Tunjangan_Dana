package com.example.tunjangandana.Fragment

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tunjangandana.Adapter.DanaAdapter
import com.example.tunjangandana.MainActivity
import com.example.tunjangandana.databinding.EditDialogBinding
import com.example.tunjangandana.databinding.FragmentHomeBinding
import com.example.tunjangandana.room.BobotDatabase
import com.example.tunjangandana.room.Dana
import com.example.tunjangandana.room.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var myDatabase: BobotDatabase? = null
    private val binding get() = _binding!!

    private var adapter:DanaAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myDatabase = BobotDatabase.getInstance(requireContext())

        val sharedPreference = requireContext()
            .getSharedPreferences(MainActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE)


        fetchData()

        binding.tvWelcome.text = "Welcome ${sharedPreference?.getString("email",null)}"


        binding.fabAdd.setOnClickListener {

            val dialog = AddFragment{
                lifecycleScope.launch(Dispatchers.IO) {
                    val result = myDatabase?.danaDao()?.insertDana(it)
                    activity?.runOnUiThread {
                        if (result == (0).toLong()){
                            Toast.makeText(context,
                                "gagal insert", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(context,
                                "sukses insert", Toast.LENGTH_SHORT).show()
                            fetchData()
                        }
                    }
                }
            }

            dialog.show(parentFragmentManager,"dialog")
            }
        binding.tvLogout.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreference.edit()
            editor.clear()
            editor.apply()
            val direct = HomeFragmentDirections.actionHomeFragmentToSplashFragment()
            findNavController().navigate(direct)
        }


    }

    fun fetchData(){
        lifecycleScope.launch(Dispatchers.IO) {
            val myDb = myDatabase?.danaDao()
            val listDana = myDb?.getAllDana()

            activity?.runOnUiThread {
                listDana?.let {
                    adapter = DanaAdapter(it, delete = {
                        Dana -> androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setPositiveButton("Iya"){_,_ ->
                            val mDb = BobotDatabase.getInstance(requireContext())
                            lifecycleScope.launch(Dispatchers.IO){
                                val result = mDb?.danaDao()?.deleteDana(Dana)
                                activity?.runOnUiThread{
                                    if(result != 0){
                                        Toast.makeText(
                                            requireContext(),
                                            "Data ${Dana.keterangan} berhasil dihapus",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            requireContext(),
                                            "Data ${Dana.keterangan} gagal dihapus",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                                fetchData()
                            }
                        }
                        .setNegativeButton("Tidak"){ dialog, _ ->
                            dialog.dismiss()
                        }
                        .setMessage("Apakah anda yakin ingin menghapus ${Dana.keterangan}")
                        .setTitle("Konfirmasi Hapus")
                        .create()
                        .show()
                    }, edit = { dana ->
                        val dialogBinding = EditDialogBinding.inflate(LayoutInflater.from(requireContext()))
                        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        dialogBuilder.setView(dialogBinding.root)
                        val dialog = dialogBuilder.create()
                        dialog.setCancelable(false)
                        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialogBinding.tvId.text = "${dana.id}"
                        dialogBinding.etKeterangan.setText("${dana.keterangan}")
                        dialogBinding.etGoals.setText("${dana.danaGoals}")
                        dialogBinding.btnSave.setOnClickListener{
                            val mDB = BobotDatabase.getInstance(requireContext())
                            val id: Int = dialogBinding.tvId.text.toString().toInt()
                            val keterangan: String = dialogBinding.etKeterangan.text.toString()
                            val goals: Int = dialogBinding.etGoals.text.toString().toInt()
                            val month: Int = dialogBinding.etMonth.text.toString().toInt()
                            val permonth: Int = goals/month
                            val data = Dana(
                                id,keterangan,goals,permonth
                            )
                            lifecycleScope.launch(Dispatchers.IO){
                                val result = mDB?.danaDao()?.updateDana(data)
                                runBlocking(Dispatchers.Main){
                                    if (result != 0){
                                        Toast.makeText(
                                            requireContext(),
                                            "Pengutang ${data.keterangan} Berhasil Di Update!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        fetchData()
                                        dialog.dismiss()
                                    } else {
                                        Toast.makeText(
                                            requireContext(),
                                            "Pengutang ${data.keterangan} Gagal Di update!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        dialog.dismiss()
                                    }
                                }
                            }
                        }
                        dialog.show()
                    })
                    binding.rvDana.adapter = adapter
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
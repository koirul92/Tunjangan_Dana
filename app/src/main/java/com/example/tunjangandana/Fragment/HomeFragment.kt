package com.example.tunjangandana.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.tunjangandana.Adapter.DanaAdapter
import com.example.tunjangandana.MainActivity
import com.example.tunjangandana.databinding.FragmentHomeBinding
import com.example.tunjangandana.room.BobotDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myDatabase = BobotDatabase.getInstance(requireContext())

        val sharedPreference = requireContext()
            .getSharedPreferences(MainActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE)

//        binding.button.setOnClickListener {
//            val editor = sharedPreference.edit()
//            editor.clear()
//            editor.apply()
//        }

        fetchData()

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

//            val action = ListScheduleFragmentDirections.actionListScheduleFragmentToFormFragment2()
//            it.findNavController().navigate(action)
        }


    }

    private fun fetchData(){
        lifecycleScope.launch(Dispatchers.IO) {
            val myDb = myDatabase?.danaDao()
            val listStudent = myDb?.getAllDana()

            activity?.runOnUiThread {
                listStudent?.let {
                    adapter = DanaAdapter(it)
                    binding.rvDana.adapter = adapter
                }
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
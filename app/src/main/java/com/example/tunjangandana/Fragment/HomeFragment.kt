package com.example.tunjangandana.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.tunjangandana.Adapter.DanaAdapter
import com.example.tunjangandana.MainActivity
import com.example.tunjangandana.R
import com.example.tunjangandana.databinding.FragmentHomeBinding
import com.example.tunjangandana.room.BobotDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var mDb: BobotDatabase? = null
    private val binding get() = _binding!!

    private var adapter:DanaAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = BobotDatabase.getInstance(requireContext())

        val sharedPreference = requireContext().getSharedPreferences(MainActivity.SHARED_PREFERENCES,Context.MODE_PRIVATE)

        binding.fabAdd.setOnClickListener{

        }
    }
     fun fetchData(){
        lifecycleScope.launch(Dispatchers.IO){
            val listDana = mDb?.danaDao()?.getAllDana()
            activity?.runOnUiThread {
                listDana?.let {
                    adapter = DanaAdapter(it)
                    binding.rvDana.adapter = adapter
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
    }

}
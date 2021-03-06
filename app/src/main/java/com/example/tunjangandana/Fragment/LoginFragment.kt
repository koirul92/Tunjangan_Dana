package com.example.tunjangandana.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tunjangandana.MainActivity
import com.example.tunjangandana.R
import com.example.tunjangandana.databinding.FragmentLoginBinding
import com.example.tunjangandana.room.BobotDatabase
import com.example.tunjangandana.room.DanaRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding?=null
    private val binding get() = _binding!!

    private var mDb: BobotDatabase?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDb = BobotDatabase.getInstance(requireContext())
        val sharedPreferences = requireContext()
            .getSharedPreferences(MainActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE)
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            lifecycleScope.launch(Dispatchers.IO){
                val Login = mDb?.userDao()?.login(email,password)
                activity?.runOnUiThread {
                    if (Login ==null){
                        Snackbar.make(it,"Username atau Password anda salah",Snackbar.LENGTH_LONG).show()
                    }else{
                        val editor = sharedPreferences.edit()
                        editor.putString("email",email)
                        editor.apply()
                        val direct = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                        findNavController().navigate(direct)
                    }
                }
            }

        }
        binding.btnRegister.setOnClickListener {
            val direct = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(direct)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
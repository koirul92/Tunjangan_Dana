package com.example.tunjangandana.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tunjangandana.R
import com.example.tunjangandana.databinding.FragmentRegisterBinding
import com.example.tunjangandana.room.BobotDatabase
import com.example.tunjangandana.room.User
import kotlinx.coroutines.*


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private var mDb: BobotDatabase? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDb = BobotDatabase.getInstance(requireContext())

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            val regist = User(null,name,email,password)
            when {
                name.isNullOrEmpty() -> {
                    binding.materialName.error = "Kolom nama harus diisi"
                }
                email.isNullOrEmpty() -> {
                    binding.materialEmail.error = "Kolom email harus diisi"
                }
                password.isNullOrEmpty() -> {
                    binding.materialPassword.error = "Kolom password harus diisi"
                }
                confirmPassword.isNullOrEmpty() -> {
                    binding.materialConfirmPassword.error = "Kolom konfirmasi password harus diisi"
                }
                password.lowercase() != confirmPassword.lowercase() -> {
                    binding.materialConfirmPassword.error = "Password dan konfirmasi password tidak sama"
                    binding.etConfirmPassword.setText("")
                }else-> {
                lifecycleScope.launch(Dispatchers.IO) {
                    val result = mDb?.userDao()?.insertUser(regist)
                    activity?.runOnUiThread {
                        if (result != 0.toLong()){
                            Toast.makeText(context, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(context, "Pendaftaran gagal", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                val direct = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                findNavController().navigate(direct)
            }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null}
}
package com.example.tunjangandana.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.tunjangandana.R
import com.example.tunjangandana.databinding.FragmentRegisterBinding
import com.example.tunjangandana.room.BobotDatabase
import com.example.tunjangandana.room.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


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
                    binding.materialName.error = "Username kamu belum diisi"
                }
                email.isNullOrEmpty() -> {
                    binding.materialEmail.error = "Email kamu belum diisi"
                }
                password.isNullOrEmpty() -> {
                    binding.materialPassword.error = "Password kamu belum diisi"
                }
                confirmPassword.isNullOrEmpty() -> {
                    binding.materialConfirmPassword.error = "Kamu perlu konfirmasi password"
                }
                password.lowercase() != confirmPassword.lowercase() -> {
                    binding.materialConfirmPassword.error = "Password tidak sama"
                    binding.etConfirmPassword.setText("")
                }else-> {
                    GlobalScope.async {
                    val result = mDb?.userDao()?.insertUser(regist)
                    runBlocking {
                        if (result != 0.toLong()){
                            Toast.makeText(activity, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(activity, "Pendaftaran gagal", Toast.LENGTH_SHORT).show()
                        }
                        onStop()
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
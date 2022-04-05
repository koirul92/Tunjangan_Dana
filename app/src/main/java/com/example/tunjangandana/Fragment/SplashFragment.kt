package com.example.tunjangandana.Fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tunjangandana.MainActivity.Companion.SHARED_PREFERENCES
import com.example.tunjangandana.R

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreference = context?.getSharedPreferences(SHARED_PREFERENCES,Context.MODE_PRIVATE)

        val userShared = sharedPreference?.getString("username","")

        Handler(Looper.getMainLooper()).postDelayed({
            if (userShared == ""){
            }
        },2000)
    }

}
package com.kppmining.client.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kppmining.client.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

//        if (activity?.intent!!.hasExtra("message")) {
//            val message = activity?.intent!!.getStringExtra("message")
//
//            Snackbar.make(requireView(), message.toString(), Snackbar.LENGTH_SHORT).show()
//        }
        return binding.root
    }
}
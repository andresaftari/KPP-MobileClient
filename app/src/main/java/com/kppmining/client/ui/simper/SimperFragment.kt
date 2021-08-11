package com.kppmining.client.ui.simper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kppmining.client.MainActivity
import com.kppmining.client.databinding.FragmentSimperBinding
import com.kppmining.core.domain.model.DummyAccount

class SimperFragment : Fragment() {
    private lateinit var binding: FragmentSimperBinding
    private var name = DummyAccount()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSimperBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        returnUsername()

        return binding.root
    }

    private fun returnUsername() {
        val account = arguments?.getParcelable<DummyAccount>("account")
        Log.d("CHECK_USERNAME", account!!.username)

        name = account
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(requireContext(), MainActivity::class.java).putExtra("username", name))
                activity?.finish()
                return true
            }
        }
        return false
    }
}
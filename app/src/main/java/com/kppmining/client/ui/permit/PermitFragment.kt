package com.kppmining.client.ui.permit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.kppmining.client.MainActivity
import com.kppmining.client.databinding.FragmentPermitBinding
import com.kppmining.core.domain.model.DummyAccount

class PermitFragment : Fragment() {
    private lateinit var binding: FragmentPermitBinding
    private var name = DummyAccount()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPermitBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        returnUsername()

        binding.btnPermit.setOnClickListener {
            Snackbar.make(
                requireView(),
                "Fitur pembuatan Mine Permit masih dalam pengembangan",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        return binding.root
    }

    // Get Username
    private fun returnUsername() {
        val account = arguments?.getParcelable<DummyAccount>("account")
        Log.d("CHECK_USERNAME", account!!.username)

        name = account
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(
                    Intent(
                        requireContext(),
                        MainActivity::class.java
                    ).putExtra("username", name)
                )
                activity?.finish()
                return true
            }
        }
        return false
    }
}
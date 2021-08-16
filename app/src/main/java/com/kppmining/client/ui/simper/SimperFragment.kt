package com.kppmining.client.ui.simper

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
import com.kppmining.client.databinding.FragmentSimperBinding
import com.kppmining.client.ui.simper.creation.*
import com.kppmining.core.domain.model.DummyAccount

class SimperFragment : Fragment() {
    private lateinit var binding: FragmentSimperBinding
    private var account = DummyAccount()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSimperBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        returnUsername()

        // Sebelum ada API, status ada 1 - 13
        val statusSimper = 1

        binding.btnSimper.setOnClickListener {
            when (statusSimper) {
                1 -> {
                    startActivity(
                        Intent(
                            requireContext(),
                            SimperMcuActivity::class.java
                        ).putExtra("username", account)
                    )
                    activity?.finish()
                }
                2 -> {
                    startActivity(Intent(requireContext(), SimperBerkasActivity::class.java))
                    activity?.finish()
                }
                3 -> {
                    startActivity(Intent(requireContext(), SimperSelectionActivity::class.java))
                    activity?.finish()
                }
                4 -> {
                    startActivity(Intent(requireContext(), SimperTempPermitActivity::class.java))
                    activity?.finish()
                }
                5 -> {
                    startActivity(Intent(requireContext(), SimperDdtActivity::class.java))
                    activity?.finish()
                }
                6 -> {
                    startActivity(Intent(requireContext(), SimperGroundTestActivity::class.java))
                    activity?.finish()
                }
                7 -> {
                    startActivity(Intent(requireContext(), SimperTestResultActivity::class.java))
                    activity?.finish()
                }
            }

            Snackbar.make(
                requireView(),
                "Fitur pembuatan SIMPER masih dalam pengembangan",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        return binding.root
    }

    // Get Username
    private fun returnUsername() {
        val data = arguments?.getParcelable<DummyAccount>("account")
        Log.d("CHECK_USERNAME", data!!.username)

        this.account = data
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(
                    Intent(
                        requireContext(),
                        MainActivity::class.java
                    ).putExtra("username", account)
                )
                activity?.finish()
                return true
            }
        }
        return false
    }
}
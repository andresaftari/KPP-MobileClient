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
import com.kppmining.client.ui.simper.create.*
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
        val statusSimper = 2

        binding.btnSimper.setOnClickListener {
            when (statusSimper) {
                1 -> {
                    startActivity(
                        Intent(
                            requireContext(),
                            ThreadmillCheckSimperActivity::class.java
                        ).putExtra("username", account)
                    )
                    activity?.finish()
                }
                2 -> {
                    startActivity(
                        Intent(
                            requireContext(),
                            PcrCheckSimperActivity::class.java
                        ).putExtra("username", account)
                    )
                    activity?.finish()
                }
                3 -> {
                    startActivity(
                        Intent(
                            requireContext(),
                            McuValidationSimperActivity::class.java
                        ).putExtra("username", account)
                    )

                    activity?.finish()
                }
                4 -> {
                    startActivity(
                        Intent(
                            requireContext(),
                            FormInputSimperActivity::class.java
                        ).putExtra("username", account)
                    )
                    activity?.finish()
                }
                5 -> {
                    startActivity(
                        Intent(
                            requireContext(),
                            InductionProcessSimperActivity::class.java
                        ).putExtra("username", account)
                    )
                    activity?.finish()
                }
                6 -> {
                    startActivity(
                        Intent(
                            requireContext(),
                            TemporaryPermitSimperActivity::class.java
                        ).putExtra("username", account)
                    )
                    activity?.finish()
                }
                7 -> {
                    startActivity(
                        Intent(
                            requireContext(),
                            DdtInternalSimperActivity::class.java
                        ).putExtra("username", account)
                    )
                    activity?.finish()
                }
                8 -> {
                    startActivity(
                        Intent(
                            requireContext(),
                            DdtInputResultSimperActivity::class.java
                        ).putExtra("username", account)
                    )
                    activity?.finish()
                }
                9 -> {
                    startActivity(
                        Intent(
                            requireContext(),
                            GroundRegistrationSimperActivity::class.java
                        ).putExtra("username", account)
                    )
                    activity?.finish()
                }
                10 -> {
                    startActivity(
                        Intent(
                            requireContext(),
                            GroundResultInputSimperActivity::class.java
                        ).putExtra("username", account)
                    )
                    activity?.finish()
                }
                11 -> {
                    startActivity(
                        Intent(
                            requireContext(),
                            GroundTestResultCheckSimperActivity::class.java
                        ).putExtra("username", account)
                    )
                    activity?.finish()
                }
                12 -> {
                    startActivity(
                        Intent(
                            requireContext(),
                            OrientationInputSimperActivity::class.java
                        ).putExtra("username", account)
                    )
                    activity?.finish()
                }
                13 -> {
                    startActivity(
                        Intent(
                            requireContext(),
                            OrientationValidationSimperActivity::class.java
                        ).putExtra("username", account)
                    )
                    activity?.finish()
                }
                14 -> {
                    startActivity(
                        Intent(
                            requireContext(),
                            OrientationProcessingSimperActivity::class.java
                        ).putExtra("username", account)
                    )
                    activity?.finish()
                }
                15 -> {
                    startActivity(
                        Intent(
                            requireContext(),
                            AdministrationAndSelectionSimperActivity::class.java
                        ).putExtra("username", account)
                    )
                    activity?.finish()
                }
                16 -> {
                    startActivity(
                        Intent(
                            requireContext(),
                            ReleasingSimperActivity::class.java
                        ).putExtra("username", account)
                    )
                    activity?.finish()
                }
            }
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
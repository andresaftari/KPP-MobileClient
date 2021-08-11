package com.kppmining.client.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kppmining.client.R
import com.kppmining.client.databinding.FragmentHomeBinding
import com.kppmining.client.utils.MenuAdapter
import com.kppmining.core.domain.model.DummyAccount
import com.kppmining.core.domain.model.Menus

@SuppressLint("SetTextI18n")
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var menuAdapter: MenuAdapter

    private var bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        fetchMenu()

        binding.toolbar1.apply {
            civAvatar.visibility = View.VISIBLE
            tvUser.visibility = View.VISIBLE
            icNotif.visibility = View.VISIBLE
        }

        setNotification()

        if (activity?.intent!!.hasExtra("username")) {
            val accounts = activity?.intent!!.getParcelableExtra<DummyAccount>("username")
            binding.toolbar1.tvUser.text = "Hi, ${accounts!!.username}"

            bundle.putParcelable("account", accounts)
        }

        return binding.root
    }

    private fun setNotification() = binding.toolbar1.icNotif.setOnClickListener {
        Snackbar.make(
            requireView(),
            "Fitur notifikasi masih dalam pengembangan",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun fetchMenu() {
        val arrayOfMenus = arrayListOf(
            Menus(R.drawable.ic_simper, "Simper"),
            Menus(R.drawable.ic_permit, "Mine Permit"),
            Menus(R.drawable.ic_she, "SHE Test"),
            Menus(R.drawable.ic_sap, "SAP"),
            Menus(R.drawable.ic_commisioning, "Commissioning"),
            Menus(R.drawable.ic_sao, "SAO"),
            Menus(R.drawable.ic_ftw, "Fit to Work")
        )

        menuAdapter = MenuAdapter(arrayOfMenus) { pos, item ->
            when (pos) {
                0 -> findNavController().navigate(
                    R.id.action_navigation_home_to_navigation_simper, bundle
                )
                1 -> Snackbar.make(
                    requireView(),
                    "${item.name} selected!",
                    Snackbar.LENGTH_SHORT
                ).show()
                else -> Snackbar.make(
                    requireView(),
                    "Menu ${item.name} masih dalam pengembangan!",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        with(binding.rvMenu) {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = menuAdapter
        }
    }
}
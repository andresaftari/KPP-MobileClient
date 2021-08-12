package com.kppmining.client.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kppmining.client.databinding.ComponentDialogSaveBinding
import com.kppmining.client.databinding.FragmentProfileBinding
import com.kppmining.core.domain.model.DummyAccount

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var dialogBinding: ComponentDialogSaveBinding
    private var username = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        if (activity?.intent!!.hasExtra("username")) {
            val accounts = activity?.intent!!.getParcelableExtra<DummyAccount>("username")
            username = accounts!!.username

            binding.edtName.setText(username)
        }

        setDisplay()
        return binding.root
    }

    private fun setDisplay() = binding.btnProfile.setOnClickListener { startDialog() }

    private fun startDialog() {
        val layoutInflater = LayoutInflater.from(requireContext())
        dialogBinding = ComponentDialogSaveBinding.inflate(layoutInflater)

        MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .setCancelable(false)
            .setPositiveButton("Okay") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
package com.kppmining.client.ui.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kppmining.client.databinding.FragmentSettingBinding
import com.kppmining.client.ui.auth.LoginActivity

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(layoutInflater, container, false)

        with(binding) {
            cardAbout.setOnClickListener {
                startActivity(
                    Intent(Intent.ACTION_VIEW).setData(
                        Uri.parse("http://www.kppmining.com/Lists/Profile/show.aspx?ID=1")
                    )
                )
            }
            cardPrivacy.setOnClickListener {
                startActivity(
                    Intent(Intent.ACTION_VIEW).setData(
                        Uri.parse("http://www.kppmining.com/Lists/Profile/Show.aspx?ID=3")
                    )
                )
            }
            btnLogout.setOnClickListener {
                startActivity(
                    Intent(
                        requireContext(),
                        LoginActivity::class.java
                    ).putExtra("logout_message", "Anda berhasil keluar!")
                )
                activity?.finish()
            }
        }

        return binding.root
    }
}
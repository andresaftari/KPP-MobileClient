package com.kppmining.client.ui.notifications

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.kppmining.client.MainActivity
import com.kppmining.client.R
import com.kppmining.client.databinding.ComponentDialogClearBinding
import com.kppmining.client.databinding.FragmentNotificationBinding
import com.kppmining.client.utils.NotificationAdapter
import com.kppmining.core.domain.model.DummyAccount
import com.kppmining.core.domain.model.DummyNotification

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var dialogBinding: ComponentDialogClearBinding

    private val arrOfNotification = arrayListOf<DummyNotification>()

    private var account = DummyAccount()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        setNotificationList()
        binding.tvClearNotif.setOnClickListener { startDialog() }

        returnUsername()
        return binding.root
    }

    // Get Username
    private fun returnUsername() {
        val data = arguments?.getParcelable<DummyAccount>("account")
        Log.d("CHECK_USERNAME", data!!.username)

        this.account = data
    }

    private fun startDialog() {
        val layoutInflater = LayoutInflater.from(requireContext())
        dialogBinding = ComponentDialogClearBinding.inflate(layoutInflater)

        MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .setCancelable(false)
            .setPositiveButton("Hapus") { dialog, _ ->
                clearNotificationList()
                dialog.dismiss()
            }
            .setNegativeButton("Tidak") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun clearNotificationList() {
        arrOfNotification.clear()

        notificationAdapter = NotificationAdapter(arrOfNotification)

        with(binding.rvNotif) {
            adapter = notificationAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        Snackbar.make(
            requireView(),
            "Notifikasi berhasil dihapus",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    @SuppressLint("Recycle")
    private fun setNotificationList() {
        val arrOfIcon = resources.obtainTypedArray(R.array.notification_status_icon)
        val arrOfText = resources.getStringArray(R.array.notification_text)
        val arrOfStatus = resources.getStringArray(R.array.notification_status)
        val arrOfTitle = resources.getStringArray(R.array.notification_title)
        val arrOfDate = resources.getStringArray(R.array.notification_date)
        val arrOfTime = resources.getStringArray(R.array.notification_time)

        for (i in arrOfTitle.indices) {
            val notif = DummyNotification(
                arrOfIcon.getResourceId(i, -1),
                arrOfStatus[i],
                arrOfDate[i],
                arrOfTime[i],
                arrOfTitle[i],
                arrOfText[i]
            )
            arrOfNotification.add(notif)
        }

        notificationAdapter = NotificationAdapter(arrOfNotification)

        with(binding.rvNotif) {
            adapter = notificationAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
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
package com.kppmining.client.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.kppmining.client.R
import com.kppmining.client.databinding.ComponentDialogClearActivityBinding
import com.kppmining.client.databinding.FragmentHistoryBinding
import com.kppmining.client.utils.HistoryAdapter
import com.kppmining.core.domain.model.DummyHistory

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var dialogBinding: ComponentDialogClearActivityBinding

    private val arrOfHistory = arrayListOf<DummyHistory>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)

        setHistoryList()
        binding.tvClearHistory.setOnClickListener { startDialog() }

        return binding.root
    }

    private fun startDialog() {
        val layoutInflater = LayoutInflater.from(requireContext())
        dialogBinding = ComponentDialogClearActivityBinding.inflate(layoutInflater)

        MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .setCancelable(false)
            .setPositiveButton("Hapus") { dialog, _ ->
                clearHistoryList()
                dialog.dismiss()
            }
            .setNegativeButton("Tidak") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun clearHistoryList() {
        arrOfHistory.clear()

        historyAdapter = HistoryAdapter(arrOfHistory)

        with(binding.rvHistory) {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        Snackbar.make(
            requireView(),
            "Riwayat Aktivitas berhasil dihapus",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun setHistoryList() {
        val arrOfName = resources.getStringArray(R.array.history_name)
        val arrOfDate = resources.getStringArray(R.array.history_date)
        val arrOfTime = resources.getStringArray(R.array.history_time)

        for (i in arrOfName.indices) {
            val history = DummyHistory(arrOfName[i], arrOfDate[i], arrOfTime[i])
            arrOfHistory.add(history)
        }

        historyAdapter = HistoryAdapter(arrOfHistory)

        with(binding.rvHistory) {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}
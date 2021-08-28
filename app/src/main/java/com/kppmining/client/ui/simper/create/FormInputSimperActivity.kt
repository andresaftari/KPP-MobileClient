package com.kppmining.client.ui.simper.create

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.esafirm.imagepicker.features.ImagePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kppmining.client.MainActivity
import com.kppmining.client.databinding.ActivityFormInputSimperBinding
import com.kppmining.client.databinding.ComponentDialogSubmitBinding
import com.kppmining.client.utils.FORM_IMAGE_PICKER_CHECK
import com.kppmining.core.domain.model.DummyAccount
import com.kppmining.core.utils.ImageCompressor
import java.io.File
import java.io.IOException
import java.io.InvalidObjectException

class FormInputSimperActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormInputSimperBinding

    private var account = DummyAccount()

    private var getInductionPhoto: File? = null
    private var getPcrPhoto: File? = null
    private var getKtpPhoto: File? = null
    private var getSimPhoto: File? = null
    private var getMcuPhoto: File? = null
    private var getIjazahPhoto: File? = null
    private var getLetterPhoto: File? = null

    private var isInductionClicked = false
    private var isPcrClicked = false
    private var isKtpClicked = false
    private var isSimClicked = false
    private var isMcuClicked = false
    private var isIjazahClicked = false
    private var isLetterClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormInputSimperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar1.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        returnUsername()

        with(binding) {
            edtInduction.setOnClickListener {
                isInductionClicked = true
                getPhoto()
            }
            edtPcr.setOnClickListener {
                isPcrClicked = true
                getPhoto()
            }
            edtKtp.setOnClickListener {
                isKtpClicked = true
                getPhoto()
            }
            edtScanSim.setOnClickListener {
                isSimClicked = true
                getPhoto()
            }
            edtIjazah.setOnClickListener {
                isIjazahClicked = true
                getPhoto()
            }
            edtScanMcu.setOnClickListener {
                isMcuClicked = true
                getPhoto()
            }
            edtLetter.setOnClickListener {
                isLetterClicked = true
                getPhoto()
            }
            btnSubmit.setOnClickListener { sendData() }
        }
    }

    private fun getPhoto() = ImagePicker.create(this).single().start()

    private fun sendData() = with(binding) {
        val nikData = edtNik.text.toString()
        val simData = edtSim.text.toString()
        val ijazahData = edtIjazah.text.toString()
        val scanInductionData = edtInduction.text.toString()
        val scanPcrData = edtPcr.text.toString()
        val scanKtpData = edtKtp.text.toString()
        val scanSimData = edtScanSim.text.toString()
        val scanIjazahData = edtScanIjazah.text.toString()
        val scanMCUData = edtScanMcu.text.toString()
        val scanLetterData = edtLetter.text.toString()

        when {
            TextUtils.isEmpty(nikData) -> {
                edtNik.apply {
                    error = "Silakan isi NIK Anda"
                    requestFocus()
                }
            }
            TextUtils.isEmpty(simData) -> {
                edtSim.apply {
                    error = "Silakan isi No. SIM Anda"
                    requestFocus()
                }
            }
            TextUtils.isEmpty(ijazahData) -> {
                edtIjazah.apply {
                    error = "Silakan isi No. Ijazah Anda"
                    requestFocus()
                }
            }
            TextUtils.equals(scanInductionData, "Pilih Gambar") -> {
                edtInduction.apply {
                    error = "Silakan isi hasil Scan Pengajuan Induksi"
                    requestFocus()
                }
            }
            TextUtils.equals(scanPcrData, "Pilih Gambar") -> {
                edtPcr.apply {
                    error = "Silakan isi hasil Scan PCR"
                    requestFocus()
                }
            }
            TextUtils.equals(scanKtpData, "Pilih Gambar") -> {
                edtKtp.apply {
                    error = "Silakan isi hasil Scan KTP"
                    requestFocus()
                }
            }
            TextUtils.equals(scanSimData, "Pilih Gambar") -> {
                edtScanSim.apply {
                    error = "Silakan isi hasil Scan SIM"
                    requestFocus()
                }
            }
            TextUtils.equals(scanMCUData, "Pilih Gambar") -> {
                edtScanMcu.apply {
                    error = "Silakan isi hasil Scan MCU"
                    requestFocus()
                }
            }
            TextUtils.equals(scanIjazahData, "Pilih Gambar") -> {
                edtScanIjazah.apply {
                    error = "Silakan isi Scan Ijazah"
                    requestFocus()
                }
            }
            TextUtils.equals(scanLetterData, "Pilih Gambar") -> {
                edtLetter.apply {
                    error = "Silakan isi hasil Scan Pernyataan"
                    requestFocus()
                }
            }
            else -> startDialog()
        }
    }

    private fun returnUsername() {
        if (intent!!.hasExtra("username")) {
            val data = intent!!.getParcelableExtra<DummyAccount>("username")
            this.account = data!!

            Log.d("CHECK_USERNAME", data.username)
        }
    }

    private fun startDialog() {
        val layoutInflater = LayoutInflater.from(this)
        val dialogBinding = ComponentDialogSubmitBinding.inflate(layoutInflater)

        MaterialAlertDialogBuilder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .setPositiveButton("Okay") { dialog, _ ->
                dialog.dismiss()
                startActivity(
                    Intent(
                        this, InductionProcessSimperActivity::class.java
                    ).putExtra("username", account)
                )
                finish()
            }.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val pickedImage = ImagePicker.getFirstImageOrNull(data)

            try {
                val parcelFileDecriptor = this
                    .contentResolver.openFileDescriptor(pickedImage.uri, "r")!!

                val fileDecriptor = parcelFileDecriptor.fileDescriptor
                val image = BitmapFactory.decodeFileDescriptor(fileDecriptor)
                val filePhoto = ImageCompressor.addTempFile(this, image)

                if (isInductionClicked) {
                    getInductionPhoto = filePhoto
                    binding.edtInduction.setText(filePhoto.name.toString())
                }
                if (isPcrClicked) {
                    getPcrPhoto = filePhoto
                    binding.edtPcr.setText(filePhoto.name.toString())
                }
                if (isKtpClicked) {
                    getKtpPhoto = filePhoto
                    binding.edtKtp.setText(filePhoto.name.toString())
                }
                if (isSimClicked) {
                    getSimPhoto = filePhoto
                    binding.edtScanSim.setText(filePhoto.name.toString())
                }
                if (isIjazahClicked) {
                    getIjazahPhoto = filePhoto
                    binding.edtScanIjazah.setText(filePhoto.name.toString())
                }
                if (isMcuClicked) {
                    getMcuPhoto = filePhoto
                    binding.edtScanMcu.setText(filePhoto.name.toString())
                }
                if (isLetterClicked) {
                    getLetterPhoto = filePhoto
                    binding.edtLetter.setText(filePhoto.name.toString())
                }
            } catch (ex: InvalidObjectException) {
                Log.d("$FORM_IMAGE_PICKER_CHECK.berkasSimper", "${ex.printStackTrace()}")
            } catch (ex: IOException) {
                Log.d("$FORM_IMAGE_PICKER_CHECK.berkasSimper", "${ex.printStackTrace()}")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    ).putExtra("username", account)
                )
                finish()
                return true
            }
        }
        return false
    }
}
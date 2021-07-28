package com.kppmining.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DummyAccount(val username: String = "") : Parcelable

package com.kppmining.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menus(
    val thumbnail: Int,
    val name: String,
) : Parcelable
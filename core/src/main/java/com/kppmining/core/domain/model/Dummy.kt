package com.kppmining.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DummyAccount(val username: String = "") : Parcelable

@Parcelize
data class DummyBanner(
    var bannerThumb: Int = 0,
    var bannerTitle: String = "",
    var bannerSubtitle: String = ""
) : Parcelable

@Parcelize
data class DummyHistory(
    val actionName: String = "",
    val actionDate: String = "",
    val actionTime: String = ""
): Parcelable

@Parcelize
data class DummyNotification(
    var notifIcon: Int = 0,
    var notifStatus: String = "",
    var notifDate: String = "",
    var notifTime: String = "",
    var notifTitle: String = "",
    var notifText: String = ""
): Parcelable

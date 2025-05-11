package com.zain.giftcard.models


data class ScratchCardModel(
    val title: String,
    val imageRes: Int,
    var isScratched: Boolean = false
)

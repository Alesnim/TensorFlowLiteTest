package com.itschool.tensorflowlitetest.classifier

public data class Recognition (
    val label: Int,
    val confidence: Float,
    val timeCost: Long
)

package com.example.fizikaforall.electricity

data class Resistor(
    val id: Long,
    val resistance: Resistance
)

data class Resistance(
    var resistance: Float = 0f,
)
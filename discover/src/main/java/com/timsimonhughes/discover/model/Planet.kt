package com.timsimonhughes.discover.model

data class Planet(
    val id: Int,
    val title: String?,
    val imageUrl: String?,
    val overview: String?,
    val orbitalPeriod: String?,
    val distanceSol: String?,
    val moons: String?,
    val orbitalSpeed: Float,
    val planetType: String?
)
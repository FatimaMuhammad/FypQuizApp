package com.example.fypfsy.utils

import com.example.fypfsy.R

object IconPicker {
    val icons = arrayOf(
        R.drawable.icon2,
        R.drawable.icon3,
        R.drawable.icon_4,
        R.drawable.icon_6,
        R.drawable.icon8


    )
    var currentIcon = 0

    fun getIcon(): Int {
        currentIcon = (currentIcon + 1)% icons.size
        return icons[currentIcon]
    }
}
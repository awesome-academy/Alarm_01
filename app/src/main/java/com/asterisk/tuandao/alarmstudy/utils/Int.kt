package com.asterisk.tuandao.alarmstudy.utils

fun Int.negativeMod(other: Int) : Int {
    val mod = this%other
    return if (mod < 0) mod + other else mod
}

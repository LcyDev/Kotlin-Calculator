package io.lwcl.utils

import java.awt.Color

object ColorUtil {
    private val HEX_REGEX = Regex("^#[0-9a-fA-F]{6}\$")

    fun hexToColor(colorHex: String): Color {
        if (!HEX_REGEX.matches(colorHex)) {
            throw IllegalArgumentException("Invalid color hexadecimal string")
        }
        val colorInt = colorHex.replace("#", "").toInt(16)
        return Color(colorInt)
    }
}

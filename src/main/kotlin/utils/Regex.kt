package io.lwcl.utils

object RegexUtils {
    val DOUBLE_OR_NUMBER_REGEX = Regex("(-?\\d+[.]\\d*)|(\\d+)|(-\\d+)")
    val OPT_DECIMAL_REGEX = Regex("-?\\d+[.]0*")
    val ZERO_REGEX = Regex("0*")

    val NUMBER_REGEX = Regex("^[-+]?\\d+(\\.\\d+)?$")
    val ALPHANUMERIC_REGEX = Regex("^[a-zA-Z0-9]+$")
    val OPERATOR_REGEX = Regex("[+\\-*/]")
    val FUNCTION_REGEX = Regex("[a-zA-Z]+")
    val SPECIAL_CHARACTERS_REGEX = Regex("[^a-zA-Z0-9+\\-*/]")
    val DECIMAL_SEPARATOR_REGEX = Regex("\\.")
    val GROUPING_SEPARATOR_REGEX = Regex(",")
    val PERCENTAGE_REGEX = Regex("%")
    val PERCENTAGE_SIGN_REGEX = Regex("[+\\-]")
    val E_REGEX = Regex("e[+-]?\\d+")
    val PI_REGEX = Regex("π")
    val SQUARE_ROOT_REGEX = Regex("\\sqrt{\\d+}")
    val FACTORIAL_REGEX = Regex("\\d+!")
}

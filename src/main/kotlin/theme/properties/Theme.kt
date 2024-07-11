package io.lwcl.theme.properties

data class Theme (
    val name: String,
    val textColor: String,
    val applicationBackground: String,

    val button: Button,
    val buttonType: ButtonType
)

data class Button(
    val backTextColor: String,
    val backBackground: String,
    val clearTextColor: String,
    val clearBackground: String,
    val equalTextColor: String,
    val equalBackground: String,
)

data class ButtonType(
    val functionTextColor: String,
    val functionBackground: String,
    val numberTextColor: String,
    val numberBackground: String,
    val operatorTextColor: String,
    val operatorBackground: String,
    val utilityTextColor: String,
    val utilityBackground: String
)

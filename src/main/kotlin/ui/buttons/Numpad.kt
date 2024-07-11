package io.lwcl.ui.buttons

import io.lwcl.enums.buttons.NumberButton
import io.lwcl.ui.CalculatorUI
import io.lwcl.utils.RegexUtils.ZERO_REGEX
import javax.swing.JButton

class Numpad {
    private val btn0 : JButton = createNumButton(NumberButton.ZERO)
    private val btn1 : JButton = createNumButton(NumberButton.ONE)
    private val btn2 : JButton = createNumButton(NumberButton.TWO)
    private val btn3 : JButton = createNumButton(NumberButton.THREE)
    private val btn4 : JButton = createNumButton(NumberButton.FOUR)
    private val btn5 : JButton = createNumButton(NumberButton.FIVE)
    private val btn6 : JButton = createNumButton(NumberButton.SIX)
    private val btn7 : JButton = createNumButton(NumberButton.SEVEN)
    private val btn8 : JButton = createNumButton(NumberButton.EIGHT)
    private val btn9 : JButton = createNumButton(NumberButton.NINE)

    val buttons = listOf(
        btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9
    )

    private fun createNumButton(number: NumberButton, visibility: Boolean = true): JButton {
        val button = CalculatorUI.createButton(number.toString(), visibility)
        button.addActionListener { handleNumButtonClick(number) }
        return button
    }

    private fun handleNumButtonClick(number: NumberButton) {
        val currentText = CalculatorUI.inputScreen.text
        if (CalculatorUI.addToDisplay) {
            CalculatorUI.inputScreen.text = when {
                ZERO_REGEX.matches(currentText) -> number.toString()
                else -> currentText + number.toString()
            }
        } else {
            CalculatorUI.inputScreen.text = number.toString()
            CalculatorUI.addToDisplay = true
        }
        CalculatorUI.go = true
    }
}

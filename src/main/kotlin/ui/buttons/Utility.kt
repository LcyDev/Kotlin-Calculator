package io.lwcl.ui.buttons

import io.lwcl.enums.buttons.UtilityButton
import io.lwcl.logic.CalcEngine.performOperation
import io.lwcl.ui.CalculatorUI
import javax.swing.JButton
import io.lwcl.utils.RegexUtils.DOUBLE_OR_NUMBER_REGEX
import io.lwcl.utils.RegexUtils.OPT_DECIMAL_REGEX

class Utility {

    private val btnBack: JButton = createUtilButton(UtilityButton.BACKSPACE)
    private val btnClear: JButton = createUtilButton(UtilityButton.CLEAR)
    private val btnEqual: JButton = createUtilButton(UtilityButton.EQUALS)
    private val btnPoint: JButton = createUtilButton(UtilityButton.POINT)

    val buttons = hashMapOf<UtilityButton, JButton>()

    private fun createUtilButton(util: UtilityButton, visibility: Boolean = true): JButton {
        val button = CalculatorUI.createButton(util.toString(), visibility)

        buttons[util] = button

        button.addActionListener {
            when (util) {
                UtilityButton.BACKSPACE -> onBackButtonClick()
                UtilityButton.CLEAR -> onClearButtonClick()
                UtilityButton.EQUALS -> onEqualsButtonClick()
                UtilityButton.POINT -> onPointButtonClick()
            }
        }
        return button
    }

    private fun handleUtilButtonClick(util: UtilityButton) {

    }
    private fun onBackButtonClick() {
        val currentText = CalculatorUI.inputScreen.text
        val newText = StringBuilder(currentText).deleteCharAt(currentText.length - 1).toString()
        CalculatorUI.inputScreen.text = newText.ifEmpty { "0" }
    }

    private fun onClearButtonClick() {
        CalculatorUI.inputScreen.text = "0"
        CalculatorUI.selectedOperator = ' '
        CalculatorUI.typedValue = 0.0
    }

    private fun onEqualsButtonClick() {
        val currentText = CalculatorUI.inputScreen.text

        if (!DOUBLE_OR_NUMBER_REGEX.matches(currentText)) {
            return
        }

        if (CalculatorUI.go) {
            CalculatorUI.typedValue = performOperation(CalculatorUI.typedValue, currentText.toDouble(), CalculatorUI.selectedOperator)
            val formattedValue = when {
                OPT_DECIMAL_REGEX.matches(CalculatorUI.typedValue.toString()) -> CalculatorUI.typedValue.toInt()
                else -> CalculatorUI.typedValue
            }
            CalculatorUI.inputScreen.text = formattedValue.toString()
            CalculatorUI.selectedOperator = '='
            CalculatorUI.addToDisplay = false
        }
    }

    private fun onPointButtonClick() {
        val currentText = CalculatorUI.inputScreen.text
        if (CalculatorUI.addToDisplay && !currentText.contains(".")) {
            CalculatorUI.inputScreen.text = "$currentText."
        } else if (!CalculatorUI.addToDisplay) {
            CalculatorUI.inputScreen.text = "0."
            CalculatorUI.addToDisplay = true
        }
        CalculatorUI.go = true
    }
}

package io.lwcl.ui.buttons

import io.lwcl.enums.buttons.FunctionButton
import io.lwcl.ui.CalculatorUI
import io.lwcl.utils.RegexUtils.DOUBLE_OR_NUMBER_REGEX
import io.lwcl.utils.RegexUtils.OPT_DECIMAL_REGEX
import javax.swing.JButton
import kotlin.math.ln
import kotlin.math.sqrt

class Function {
    val btnRoot: JButton = createFuncButton(FunctionButton.ROOT, false)
    val btnLog: JButton = createFuncButton(FunctionButton.LOG, false)

    val buttons = listOf(btnRoot, btnLog)

    private fun createFuncButton(func: FunctionButton, visibility: Boolean = true): JButton {
        val button = CalculatorUI.createButton(func.toString(), visibility)

        button.addActionListener {
            when (func) {
                FunctionButton.ROOT -> onRootButtonClick()
                FunctionButton.LOG -> onLogButtonClick()
                else -> {}
            }
        }
        return button
    }

    private fun onRootButtonClick() {
        val currentText = CalculatorUI.inputScreen.text ?: return  // Early return if text is null

        if (!DOUBLE_OR_NUMBER_REGEX.matches(currentText)) {
            return
        }

        if (CalculatorUI.go) {
            CalculatorUI.typedValue = sqrt(currentText.toDouble())
            CalculatorUI.inputScreen.text = when {
                OPT_DECIMAL_REGEX.matches(CalculatorUI.typedValue.toString()) -> CalculatorUI.typedValue.toInt().toString()
                else -> CalculatorUI.typedValue.toString()
            }
            CalculatorUI.selectedFunction = FunctionButton.ROOT
            CalculatorUI.addToDisplay = false
        }
    }



    private fun onLogButtonClick() {
        val currentText = CalculatorUI.inputScreen.text ?: return  // Early return if text is null

        if (!DOUBLE_OR_NUMBER_REGEX.matches(currentText)) {
            return
        }

        if (CalculatorUI.go) {
            CalculatorUI.typedValue = ln(currentText.toDouble())
            CalculatorUI.inputScreen.text = when {
                OPT_DECIMAL_REGEX.matches(CalculatorUI.typedValue.toString()) -> CalculatorUI.typedValue.toInt().toString()
                else -> CalculatorUI.typedValue.toString()
            }
            CalculatorUI.selectedFunction = FunctionButton.LOG
            CalculatorUI.addToDisplay = false
        }
    }
}

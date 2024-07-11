package io.lwcl.ui.buttons

import io.lwcl.enums.buttons.OperatorButton
import io.lwcl.logic.CalcEngine.performOperation
import io.lwcl.ui.CalculatorUI
import io.lwcl.utils.RegexUtils.DOUBLE_OR_NUMBER_REGEX
import io.lwcl.utils.RegexUtils.OPT_DECIMAL_REGEX
import javax.swing.JButton

class Operator {
    val btnAdd: JButton = createButton(OperatorButton.ADD)
    val btnSub: JButton = createButton(OperatorButton.SUBTRACT)
    val btnMul: JButton = createButton(OperatorButton.MULTIPLY)
    val btnDiv: JButton = createButton(OperatorButton.DIVIDE)
    val btnMod: JButton = createButton(OperatorButton.REMAINDER)
    val btnPower: JButton = createButton(OperatorButton.POWER, false)

    val buttons = listOf(
        btnAdd, btnSub, btnMul, btnDiv, btnMod, btnPower
    )

    private fun createButton(operator: OperatorButton, visibility: Boolean = true): JButton {
        val button = CalculatorUI.createButton(operator.toString(), visibility)
        button.addActionListener {
            when (operator) {
                OperatorButton.ADD -> onAddButtonClick()
                OperatorButton.SUBTRACT -> onSubtractButtonClick()
                OperatorButton.MULTIPLY -> onMultiplyButtonClick()
                OperatorButton.DIVIDE -> onDivideButtonClick()
                OperatorButton.REMAINDER -> onModulusButtonClick()
                OperatorButton.POWER -> onPowerButtonClick()
            }
        }
        return button
    }

    private fun onAddButtonClick() {
        val currentText = CalculatorUI.inputScreen.text ?: return  // Early return if text is null

        if (!DOUBLE_OR_NUMBER_REGEX.matches(currentText)) {
            return
        }

        if (CalculatorUI.go) {
            CalculatorUI.typedValue = performOperation(CalculatorUI.typedValue, currentText.toDouble(), CalculatorUI.selectedOperator)
            CalculatorUI.inputScreen.text = when {
                OPT_DECIMAL_REGEX.matches(CalculatorUI.typedValue.toString()) -> CalculatorUI.typedValue.toInt().toString()
                else -> CalculatorUI.typedValue.toString()
            }
            CalculatorUI.selectedOperator = '+'
            CalculatorUI.go = false
            CalculatorUI.addToDisplay = false
        } else {
            CalculatorUI.selectedOperator = '+'
        }
    }

    private fun onSubtractButtonClick() {
        val currentText = CalculatorUI.inputScreen.text ?: return  // Early return if text is null

        if (!DOUBLE_OR_NUMBER_REGEX.matches(currentText)) {
            return
        }

        if (CalculatorUI.go) {
            CalculatorUI.typedValue = performOperation(CalculatorUI.typedValue, currentText.toDouble(), CalculatorUI.selectedOperator)
            CalculatorUI.inputScreen.text = when {
                OPT_DECIMAL_REGEX.matches(CalculatorUI.typedValue.toString()) -> CalculatorUI.typedValue.toInt().toString()
                else -> CalculatorUI.typedValue.toString()
            }
            CalculatorUI.selectedOperator = '-'
            CalculatorUI.go = false
            CalculatorUI.addToDisplay = false
        } else {
            CalculatorUI.selectedOperator = '-'
        }
    }

    private fun onMultiplyButtonClick() {
        val currentText = CalculatorUI.inputScreen.text ?: return  // Early return if text is null

        if (!DOUBLE_OR_NUMBER_REGEX.matches(currentText)) {
            return
        }

        if (CalculatorUI.go) {
            CalculatorUI.typedValue = performOperation(CalculatorUI.typedValue, currentText.toDouble(), CalculatorUI.selectedOperator)
            CalculatorUI.inputScreen.text = when {
                OPT_DECIMAL_REGEX.matches(CalculatorUI.typedValue.toString()) -> CalculatorUI.typedValue.toInt().toString()
                else -> CalculatorUI.typedValue.toString()
            }
            CalculatorUI.selectedOperator = '*'
            CalculatorUI.go = false
            CalculatorUI.addToDisplay = false
        } else {
            CalculatorUI.selectedOperator = '*'
        }
    }

    private fun onDivideButtonClick() {
        val currentText = CalculatorUI.inputScreen.text ?: return  // Early return if text is null

        if (!DOUBLE_OR_NUMBER_REGEX.matches(currentText)) {
            return
        }

        if (CalculatorUI.go) {
            CalculatorUI.typedValue = performOperation(CalculatorUI.typedValue, currentText.toDouble(), CalculatorUI.selectedOperator)
            CalculatorUI.inputScreen.text = when {
                OPT_DECIMAL_REGEX.matches(CalculatorUI.typedValue.toString()) -> CalculatorUI.typedValue.toInt().toString()
                else -> CalculatorUI.typedValue.toString()
            }
            CalculatorUI.selectedOperator = '/'
            CalculatorUI.go = false
            CalculatorUI.addToDisplay = false
        } else {
            CalculatorUI.selectedOperator = '/'
        }
    }

    private fun onModulusButtonClick() {
        val currentText = CalculatorUI.inputScreen.text ?: return  // Early return if text is null

        if (!DOUBLE_OR_NUMBER_REGEX.matches(currentText)) {
            return
        }

        CalculatorUI.typedValue = performOperation(CalculatorUI.typedValue, currentText.toDouble(), CalculatorUI.selectedOperator)
        CalculatorUI.inputScreen.text = when {
            OPT_DECIMAL_REGEX.matches(CalculatorUI.typedValue.toString()) -> CalculatorUI.typedValue.toInt().toString()
            else -> CalculatorUI.typedValue.toString()
        }
        CalculatorUI.selectedOperator = '%'
        CalculatorUI.go = false
        CalculatorUI.addToDisplay = false
    }

    private fun onPowerButtonClick() {
        val currentText = CalculatorUI.inputScreen.text ?: return  // Early return if text is null

        if (!DOUBLE_OR_NUMBER_REGEX.matches(currentText)) {
            return
        }

        if (CalculatorUI.go) {
            CalculatorUI.typedValue = performOperation(CalculatorUI.typedValue, currentText.toDouble(), CalculatorUI.selectedOperator)
            CalculatorUI.inputScreen.text = when {
                OPT_DECIMAL_REGEX.matches(CalculatorUI.typedValue.toString()) -> CalculatorUI.typedValue.toInt().toString()
                else -> CalculatorUI.typedValue.toString()
            }
            CalculatorUI.selectedOperator = '^'
            CalculatorUI.go = false
            CalculatorUI.addToDisplay = false
        } else {
            CalculatorUI.selectedOperator = '^'
        }
    }
}

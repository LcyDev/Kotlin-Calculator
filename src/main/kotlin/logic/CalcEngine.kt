package io.lwcl.logic

import io.lwcl.enums.buttons.OperatorButton
import kotlin.math.pow

object CalcEngine {

    fun performOperation(firstNumber: Double, secondNumber: Double, operator: OperatorButton): Double {
        return when (operator) {
            OperatorButton.ADD -> firstNumber + secondNumber
            OperatorButton.SUBTRACT -> firstNumber - secondNumber
            OperatorButton.MULTIPLY -> firstNumber * secondNumber
            OperatorButton.DIVIDE -> if (secondNumber == 0.0) Double.NaN else firstNumber / secondNumber
            OperatorButton.REMAINDER -> firstNumber % secondNumber
            OperatorButton.POWER -> firstNumber.pow(secondNumber)
            OperatorButton.ROOT -> TODO()
        }
    }
}

package io.lwcl.logic

import io.lwcl.enums.Operator
import kotlin.math.pow

object CalcEngine {

    fun performOperation(firstNumber: Double, secondNumber: Double, operator: Operator): Double {
        return when (operator) {
            Operator.ADD -> firstNumber + secondNumber
            Operator.SUBTRACT -> firstNumber - secondNumber
            Operator.MULTIPLY -> firstNumber * secondNumber
            Operator.DIVIDE -> if (secondNumber == 0.0) Double.NaN else firstNumber / secondNumber
            Operator.REMAINDER -> firstNumber % secondNumber
            Operator.POWER -> firstNumber.pow(secondNumber)
        }
    }
}

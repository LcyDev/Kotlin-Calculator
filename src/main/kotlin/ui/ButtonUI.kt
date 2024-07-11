package io.lwcl.ui

import io.lwcl.enums.ButtonType
import java.awt.Cursor
import java.awt.Font
import javax.swing.JButton
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.sqrt

class ButtonUI(
    private val buttonWidth: Int,
    private val buttonHeight: Int,
    private val fontName: String,
) {

    private val DOUBLE_OR_NUMBER_REGEX = Regex("(-?\\d+[.]\\d*)|(\\d+)|(-\\d+)")
    private val OPT_DECIMAL_REGEX = Regex("-?\\d+[.]0*")
    private val ZERO_REGEX = Regex("0*")


    private val buttonTypes = hashMapOf<JButton, ButtonType>()

    private val btnClear: JButton = createButton("C", ButtonType.OPERATOR)
    private val btnBack: JButton = createButton("<-", ButtonType.OPERATOR)

    private val btnAdd: JButton = createButton("+", ButtonType.OPERATOR)
    private val btnSub: JButton = createButton("-", ButtonType.OPERATOR)
    private val btnMul: JButton = createButton("*", ButtonType.OPERATOR)
    private val btnDiv: JButton = createButton("/", ButtonType.OPERATOR)
    private val btnMod: JButton = createButton("%", ButtonType.OPERATOR)

    val btnRoot: JButton = createButton("√", ButtonType.OPERATOR).apply { isVisible = false }
    val btnPower: JButton = createButton("pow", ButtonType.OPERATOR).apply {
        font = Font(fontName, Font.PLAIN, 24)
        isVisible = false
    }
    val btnLog: JButton = createButton("ln", ButtonType.OPERATOR).apply { isVisible = false }

    private val btnEqual: JButton = createButton("=", ButtonType.EQUAL).apply {
        setSize(buttonWidth * 2 + 10, buttonHeight)
    }
    private val btnPoint: JButton = createButton(".", ButtonType.NUMBER)

    private val btn0: JButton = createButton("0", ButtonType.NUMBER)
    private val btn1: JButton = createButton("1", ButtonType.NUMBER)
    private val btn2: JButton = createButton("2", ButtonType.NUMBER)
    private val btn3: JButton = createButton("3", ButtonType.NUMBER)
    private val btn4: JButton = createButton("4", ButtonType.NUMBER)
    private val btn5: JButton = createButton("5", ButtonType.NUMBER)
    private val btn6: JButton = createButton("6", ButtonType.NUMBER)
    private val btn7: JButton = createButton("7", ButtonType.NUMBER)
    private val btn8: JButton = createButton("8", ButtonType.NUMBER)
    private val btn9: JButton = createButton("9", ButtonType.NUMBER)

    private var selectedOperator = ' '
    private var go = true // For calculation with operator != '='
    private var addToDisplay = true // Connect numbers in display
    private var typedValue = 0.0

    private fun createButton(label: String, type: ButtonType, fontSize : Int = 28, visible: Boolean = true): JButton {
        val btn = JButton(label)
        btn.setBounds(0, 0, buttonWidth, buttonHeight) // Set bounds later in initButtons
        btn.font = Font(fontName, Font.PLAIN, fontSize)
        btn.cursor = Cursor(Cursor.HAND_CURSOR)
        btn.isFocusable = false
        btn.isVisible = visible
        CalculatorUI.window.add(btn)
        buttonTypes[btn] = type
        return btn
    }

    private fun calculate(firstNumber: Double, secondNumber: Double, operator: Char): Double {
        return when (operator) {
            '+' -> firstNumber + secondNumber
            '-' -> firstNumber - secondNumber
            '*' -> firstNumber * secondNumber
            '/' -> firstNumber / secondNumber
            '%' -> firstNumber % secondNumber
            '^' -> firstNumber.pow(secondNumber)
            else -> secondNumber
        }
    }

    fun getAllButtons(): List<JButton> {
        return listOf(
            btnClear, btnBack, btnAdd, btnSub, btnMul, btnDiv, btnMod,
            btnRoot, btnPower, btnLog, btnEqual, btnPoint,
            btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9
        )
    }

    fun getButtonType(button: JButton): ButtonType {
        return buttonTypes[button]!!
    }

    fun initButtons(columns: IntArray, rows: IntArray) {
        btnClear.setLocation(columns[0], rows[1])
        btnClear.addActionListener { onClearButtonClick() }
        btnBack.setLocation(columns[1], rows[1])
        btnBack.addActionListener { onBackButtonClick() }

        btnAdd.setLocation(columns[3], rows[4])
        btnAdd.addActionListener { onAdditionButtonClick() }
        btnSub.setLocation(columns[3], rows[3])
        btnSub.addActionListener { onSubtractionButtonClick() }
        btnMul.setLocation(columns[3], rows[2])
        btnMul.addActionListener { onMultiplyButtonClick() }
        btnDiv.setLocation(columns[3], rows[1])
        btnDiv.addActionListener { onDivideButtonClick() }
        btnMod.setLocation(columns[2], rows[1])
        btnMod.addActionListener { onModulusButtonClick() }

        btnRoot.setLocation(columns[4], rows[1])
        btnRoot.addActionListener { onRootButtonClick() }
        btnPower.setLocation(columns[4], rows[2])
        btnPower.addActionListener { onPowerButtonClick() }
        btnLog.setLocation(columns[4], rows[3])
        btnLog.addActionListener { onLogButtonClick() }

        btnEqual.setLocation(columns[2], rows[5])
        btnEqual.addActionListener { onEqualButtonClick() }
        btnPoint.setLocation(columns[0], rows[5])
        btnPoint.addActionListener { onPointButtonClick() }

        btn0.setLocation(columns[1], rows[5])
        btn0.addActionListener { handleNumberButtonClick("0") }
        btn1.setLocation(columns[0], rows[4])
        btn1.addActionListener { handleNumberButtonClick("1") }
        btn2.setLocation(columns[1], rows[4])
        btn2.addActionListener { handleNumberButtonClick("2") }
        btn3.setLocation(columns[2], rows[4])
        btn3.addActionListener { handleNumberButtonClick("3") }
        btn4.setLocation(columns[0], rows[3])
        btn4.addActionListener { handleNumberButtonClick("4") }
        btn5.setLocation(columns[1], rows[3])
        btn5.addActionListener { handleNumberButtonClick("5") }
        btn6.setLocation(columns[2], rows[3])
        btn6.addActionListener { handleNumberButtonClick("6") }
        btn7.setLocation(columns[0], rows[2])
        btn7.addActionListener { handleNumberButtonClick("7") }
        btn8.setLocation(columns[1], rows[2])
        btn8.addActionListener { handleNumberButtonClick("8") }
        btn9.setLocation(columns[2], rows[2])
        btn9.addActionListener { handleNumberButtonClick("9") }

        // Set button positions using columns and rows (similar to Java code)
        //for (i in getAllButtons().indices) {
            //val button = getAllButtons()[i]
            //    button.setBounds(columns[i % 5] , rows[i / 5], buttonWidth, buttonHeight)
        //}
    }

    private fun onBackButtonClick() {
        val currentText = CalculatorUI.inputScreen.text
        val newText = StringBuilder(currentText).deleteCharAt(currentText.length - 1).toString()
        CalculatorUI.inputScreen.text = newText.ifEmpty { "0" }
    }

// Similar logic for other button click handlers, using when expressions and String templates
// for readability.

    private fun onClearButtonClick() {
        CalculatorUI.inputScreen.text = "0"
        selectedOperator = ' '
        typedValue = 0.0
    }

    private fun onPointButtonClick() {
        val currentText = CalculatorUI.inputScreen.text
        if (addToDisplay && !currentText.contains(".")) {
            CalculatorUI.inputScreen.text = "$currentText."
        } else if (!addToDisplay) {
            CalculatorUI.inputScreen.text = "0."
            addToDisplay = true
        }
        go = true
    }

    private fun onEqualButtonClick() {
        val currentText = CalculatorUI.inputScreen.text

        if (!DOUBLE_OR_NUMBER_REGEX.matches(currentText)) {
            return
        }

        if (go) {
            typedValue = calculate(typedValue, currentText.toDouble(), selectedOperator)
            val formattedValue = when {
                OPT_DECIMAL_REGEX.matches(typedValue.toString()) -> typedValue.toInt()
                else -> typedValue
            }
            CalculatorUI.inputScreen.text = formattedValue.toString()
            selectedOperator = '='
            addToDisplay = false
        }
    }

    private fun onAdditionButtonClick() {
        val currentText = CalculatorUI.inputScreen.text ?: return  // Early return if text is null

        if (!DOUBLE_OR_NUMBER_REGEX.matches(currentText)) {
            return
        }

        if (go) {
            typedValue = calculate(typedValue, currentText.toDouble(), selectedOperator)
            CalculatorUI.inputScreen.text = when {
                OPT_DECIMAL_REGEX.matches(typedValue.toString()) -> typedValue.toInt().toString()
                else -> typedValue.toString()
            }
            selectedOperator = '+'
            go = false
            addToDisplay = false
        } else {
            selectedOperator = '+'
        }
    }

    private fun onSubtractionButtonClick() {
        val currentText = CalculatorUI.inputScreen.text ?: return  // Early return if text is null

        if (!DOUBLE_OR_NUMBER_REGEX.matches(currentText)) {
            return
        }

        if (go) {
            typedValue = calculate(typedValue, currentText.toDouble(), selectedOperator)
            CalculatorUI.inputScreen.text = when {
                OPT_DECIMAL_REGEX.matches(typedValue.toString()) -> typedValue.toInt().toString()
                else -> typedValue.toString()
            }
            selectedOperator = '-'
            go = false
            addToDisplay = false
        } else {
            selectedOperator = '-'
        }
    }

    private fun onMultiplyButtonClick() {
        val currentText = CalculatorUI.inputScreen.text ?: return  // Early return if text is null

        if (!DOUBLE_OR_NUMBER_REGEX.matches(currentText)) {
            return
        }

        if (go) {
            typedValue = calculate(typedValue, currentText.toDouble(), selectedOperator)
            CalculatorUI.inputScreen.text = when {
                OPT_DECIMAL_REGEX.matches(typedValue.toString()) -> typedValue.toInt().toString()
                else -> typedValue.toString()
            }
            selectedOperator = '*'
            go = false
            addToDisplay = false
        } else {
            selectedOperator = '*'
        }
    }

    private fun onDivideButtonClick() {
        val currentText = CalculatorUI.inputScreen.text ?: return  // Early return if text is null

        if (!DOUBLE_OR_NUMBER_REGEX.matches(currentText)) {
            return
        }

        if (go) {
            typedValue = calculate(typedValue, currentText.toDouble(), selectedOperator)
            CalculatorUI.inputScreen.text = when {
                OPT_DECIMAL_REGEX.matches(typedValue.toString()) -> typedValue.toInt().toString()
                else -> typedValue.toString()
            }
            selectedOperator = '/'
            go = false
            addToDisplay = false
        } else {
            selectedOperator = '/'
        }
    }

    private fun onModulusButtonClick() {
        val currentText = CalculatorUI.inputScreen.text ?: return  // Early return if text is null

        if (!DOUBLE_OR_NUMBER_REGEX.matches(currentText)) {
            return
        }

        typedValue = calculate(typedValue, currentText.toDouble(), selectedOperator)
        CalculatorUI.inputScreen.text = when {
            OPT_DECIMAL_REGEX.matches(typedValue.toString()) -> typedValue.toInt().toString()
            else -> typedValue.toString()
        }
        selectedOperator = '%'
        go = false
        addToDisplay = false
    }



    private fun onRootButtonClick() {
        val currentText = CalculatorUI.inputScreen.text ?: return  // Early return if text is null

        if (!DOUBLE_OR_NUMBER_REGEX.matches(currentText)) {
            return
        }

        if (go) {
            typedValue = sqrt(currentText.toDouble())
            CalculatorUI.inputScreen.text = when {
                OPT_DECIMAL_REGEX.matches(typedValue.toString()) -> typedValue.toInt().toString()
                else -> typedValue.toString()
            }
            selectedOperator = '√'
            addToDisplay = false
        }
    }

    private fun onPowerButtonClick() {
        val currentText = CalculatorUI.inputScreen.text ?: return  // Early return if text is null

        if (!DOUBLE_OR_NUMBER_REGEX.matches(currentText)) {
            return
        }

        if (go) {
            typedValue = calculate(typedValue, currentText.toDouble(), selectedOperator)
            CalculatorUI.inputScreen.text = when {
                OPT_DECIMAL_REGEX.matches(typedValue.toString()) -> typedValue.toInt().toString()
                else -> typedValue.toString()
            }
            selectedOperator = '^'
            go = false
            addToDisplay = false
        } else {
            selectedOperator = '^'
        }
    }

    private fun onLogButtonClick() {
        val currentText = CalculatorUI.inputScreen.text ?: return  // Early return if text is null

        if (!DOUBLE_OR_NUMBER_REGEX.matches(currentText)) {
            return
        }

        if (go) {
            typedValue = ln(currentText.toDouble())
            CalculatorUI.inputScreen.text = when {
                OPT_DECIMAL_REGEX.matches(typedValue.toString()) -> typedValue.toInt().toString()
                else -> typedValue.toString()
            }
            selectedOperator = 'l'
            addToDisplay = false
        }
    }


    private fun handleNumberButtonClick(digit: String) {
        val currentText = CalculatorUI.inputScreen.text
        if (addToDisplay) {
            CalculatorUI.inputScreen.text = when {
                ZERO_REGEX.matches(currentText) -> digit
                else -> currentText + digit
            }
        } else {
            CalculatorUI.inputScreen.text = digit
            addToDisplay = true
        }
        go = true
    }
}


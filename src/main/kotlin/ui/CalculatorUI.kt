package io.lwcl.ui

import io.klogging.NoCoLogging
import io.lwcl.enums.ButtonType
import io.lwcl.enums.ThemeFormat
import io.lwcl.theme.ThemeLoader
import io.lwcl.theme.properties.Theme
import io.lwcl.utils.ColorUtil.hexToColor
import java.awt.Color
import java.awt.Cursor
import java.awt.Font
import java.awt.event.ItemEvent
import javax.swing.*

class CalculatorUI : NoCoLogging {
    companion object {
        private const val FONT_NAME = "Segoe"
        private const val APPLICATION_TITLE = "Calculator"
        private const val WINDOW_WIDTH = 410
        private const val WINDOW_HEIGHT = 600
        private const val BUTTON_WIDTH = 80
        private const val BUTTON_HEIGHT = 70
        private const val MARGIN_X = 20
        private const val MARGIN_Y = 60

        val window = JFrame(APPLICATION_TITLE)
        lateinit var inputScreen: JTextField // Initialized later
    }

    private val buttonUI = ButtonUI(BUTTON_WIDTH, BUTTON_HEIGHT, FONT_NAME)
    private val themesMap = ThemeLoader.loadThemes(ThemeFormat.YAML)

    private lateinit var comboTheme: JComboBox<String>
    private lateinit var comboCalculatorType: JComboBox<String>

    init {
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT)
        window.setLocationRelativeTo(null) // Center the window

        val columns = intArrayOf(MARGIN_X, MARGIN_X + 90, MARGIN_X + 90 * 2, MARGIN_X + 90 * 3, MARGIN_X + 90 * 4)
        val rows = intArrayOf(MARGIN_Y, MARGIN_Y + 100, MARGIN_Y + 100 + 80, MARGIN_Y + 100 + 80 * 2, MARGIN_Y + 100 + 80 * 3, MARGIN_Y + 100 + 80 * 4)

        initInputScreen(columns, rows)
        buttonUI.initButtons(columns, rows)
        initCalculatorTypeSelector()
        initThemeSelector()

        window.layout = null
        window.isResizable = false
        window.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        window.isVisible = true
    }

    private fun initInputScreen(columns: IntArray, rows: IntArray) {
        inputScreen = JTextField("0").apply {
            setBounds(columns[0], rows  [0], 350, 70)
            isEditable = false
            background = Color.WHITE
            font = Font(FONT_NAME, Font.PLAIN, 33)
        }
        window.add(inputScreen)
    }

    private fun initThemeSelector() {
        comboTheme = createComboBox(themesMap.keys.toTypedArray(), 230, 30, "Theme")
        comboTheme.addItemListener {
            if (it.stateChange != ItemEvent.SELECTED) return@addItemListener

            val selectedTheme = it.item as String
            themesMap[selectedTheme]?.let { it1 -> applyTheme(it1) }
        }

        if (themesMap.isNotEmpty()) {
            applyTheme(themesMap.values.first())
        }
    }

    private fun initCalculatorTypeSelector() {
        comboCalculatorType = createComboBox(arrayOf("Standard", "Scientific"), 20, 30, "Calculator type")
        comboCalculatorType.addItemListener {
            if (it.stateChange != ItemEvent.SELECTED) return@addItemListener

            when (val selectedItem = it.item as String) {
                "Standard" -> {
                    window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT)
                    buttonUI.btnRoot.isVisible = false
                    buttonUI.btnPower.isVisible = false
                    buttonUI.btnLog.isVisible = false
                }
                "Scientific" -> {
                    window.setSize(WINDOW_WIDTH + 80, WINDOW_HEIGHT)
                    buttonUI.btnRoot.isVisible = true
                    buttonUI.btnPower.isVisible = true
                    buttonUI.btnLog.isVisible = true
                }
                else -> logger.info("Unknown calculator type: $selectedItem")
            }
        }
    }

    private fun createComboBox(items: Array<String>, x: Int, y: Int, toolTip: String): JComboBox<String> {
        val comboBox = JComboBox(DefaultComboBoxModel(items))
        comboBox.setBounds(x, y, 140, 25)
        comboBox.toolTipText = toolTip
        comboBox.cursor = Cursor(Cursor.HAND_CURSOR)
        window.add(comboBox)
        return comboBox
    }

    private fun applyTheme(theme: Theme) {
        // Set background color
        window.contentPane.background = hexToColor(theme.applicationBackground)
        inputScreen.background = hexToColor(theme.applicationBackground)
        comboCalculatorType.background = hexToColor(theme.applicationBackground)
        comboTheme.background = hexToColor(theme.applicationBackground)

        // Set foreground color for text elements
        comboCalculatorType.foreground = hexToColor(theme.textColor)
        comboTheme.foreground = hexToColor(theme.textColor)
        inputScreen.foreground = hexToColor(theme.textColor)

        for (button in buttonUI.getAllButtons()) {
            when (buttonUI.getButtonType(button)) {
                ButtonType.NUMBER -> {
                    button.foreground = hexToColor(theme.textColor)
                    button.background = hexToColor(theme.numbersBackground)
                }
                ButtonType.OPERATOR -> {
                    button.foreground = hexToColor(theme.textColor)
                    button.background = hexToColor(theme.operatorBackground)
                }
                ButtonType.EQUAL -> {
                    button.foreground = hexToColor(theme.btnEqualTextColor)
                    button.background = hexToColor(theme.btnEqualBackground)
                }
            }
        }
    }
}
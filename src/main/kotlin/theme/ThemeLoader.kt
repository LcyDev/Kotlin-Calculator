package io.lwcl.theme

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import io.lwcl.enums.ThemeFormat
import io.lwcl.theme.properties.Theme
import io.lwcl.theme.properties.ThemeList

import java.io.File
import java.io.IOException

object ThemeLoader {

    private const val THEME_FILE = "src/main/resources/themes"

    fun loadThemes(format: ThemeFormat): Map<String, Theme> {
        val mapper = ObjectMapper(getJsonFactory(format))

        mapper.findAndRegisterModules()

        try {
            val themeList: ThemeList = mapper.readValue(File("$THEME_FILE.${format.extension}"))
            return themeList.getThemesAsMap()
        } catch (e: IOException) {
            return emptyMap()
        }
    }

    private fun getJsonFactory(format: ThemeFormat): JsonFactory {
        return when (format) {
            ThemeFormat.JSON -> JsonFactory()
            ThemeFormat.YAML -> YAMLFactory()
        }
    }
}

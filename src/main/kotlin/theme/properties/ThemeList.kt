package io.lwcl.theme.properties

data class ThemeList(
    val themes: Set<Theme>
) {

    fun getThemesAsMap(): Map<String, Theme> {
        return themes.associateBy { it.name }  // More concise approach using associateBy
    }
}

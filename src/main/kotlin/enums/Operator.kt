package io.lwcl.enums

enum class Operator(val symbol: Char) {
    ADD('+'), SUBTRACT('-'),
    MULTIPLY('*'), DIVIDE('/'),
    REMAINDER('%'), POWER('^');
}

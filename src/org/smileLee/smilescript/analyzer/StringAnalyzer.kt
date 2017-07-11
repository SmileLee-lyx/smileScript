package org.smileLee.smilescript.analyzer

import org.smileLee.smilescript.exception.*

/**
 * Created by Administrator on 2017/6/7.
 * @author smileLee
 */

class StringAnalyzer(var str: String) {
    var i: Int = 0

    companion object {
        fun isAlpha(c: Char): Boolean = c in 'A'..'Z' || c in 'a'..'z'
        fun isDigit(c: Char): Boolean = c in '0'..'9'
        fun isBlank(c: Char): Boolean = c == ' ' || c == '\t' || c == '\n'
        fun isBeginningOfSymbol(c: Char): Boolean = isAlpha(c) || c == '_'
        fun isPartOfSymbol(c: Char): Boolean = isAlpha(c) || isDigit(c) || c == '_'
        fun isBeginningOfNumber(c: Char): Boolean = isDigit(c)
        fun isPartOfNumber(c: Char): Boolean = isDigit(c) || c == '.'
    }

    override fun toString(): String {
        return str.substring(i)
    }

    fun isEmpty(): Boolean {
        removeBeginningBlank()
        return i == str.length
    }

    fun removeBeginningBlank() {
        while (i < str.length && isBlank(str[i])) ++i
    }

    val firstChar: Char
        get() {
            removeBeginningBlank()
            if (i != str.length)
                return str[i]
            else
                return 0.toChar()
        }

    val beginWithSymbol: Boolean get() = isBeginningOfSymbol(firstChar)
    val beginWithNumber: Boolean get() = isBeginningOfNumber(firstChar)

    fun removeBeginning(l: Int) {
        removeBeginningBlank()
        i = minOf(i + l, str.length)
    }

    fun removeAssertingBeginningIs(s: String) {
        removeBeginningBlank()
        if (i + s.length <= str.length && str.startsWith(s, i)) {
            i += s.length
        } else {
            throw SyntaxException()
        }
    }

    val symbol: String get() {
        removeBeginningBlank()
        if (!beginWithSymbol) throw SyntaxException()
        val b: Int = i
        while (i < str.length && isPartOfSymbol(str[i])) ++i
        return str.substring(b, i)
    }

    val number: Number? get() {
        removeBeginningBlank()
        val b: Int = i
        var hasDot: Boolean = false
        num@ while (i < str.length && isPartOfNumber(str[i])) {
            if (str[i] == '.') {
                when (hasDot) {
                    true  -> break@num
                    false -> hasDot = true
                }
            }
            ++i
        }
        val numString: String = str.substring(b, i)
        try {
            return when (firstChar) {
                'i', 'I' -> {
                    ++i
                    numString.toInt()
                }
                'd', 'D' -> {
                    ++i
                    numString.toDouble()
                }
                'f', 'F' -> {
                    ++i
                    numString.toFloat()
                }
                'l', 'L' -> {
                    ++i
                    numString.toLong()
                }
                else     -> if (hasDot) numString.toDouble() else numString.toInt()
            }
        } catch (e: NumberFormatException) {
            return null
        }
    }

    val char: Char
        get() {
            removeAssertingBeginningIs("\'")
            val ret = nextChar
            removeAssertingBeginningIs("\'")
            return ret
        }

    val string: String
        get() {
            val s = StringBuilder()
            removeAssertingBeginningIs("\"")
            while (true) {
                if (isEmpty()) throw SyntaxException()
                if (firstOperatorIs("\"")) break
                s.append(nextChar)
            }
            removeAssertingBeginningIs("\"")
            return s.toString()
        }

    val nextChar: Char
        get() {
            if (isEmpty()) throw SyntaxException()
            val ret: Char
            if (str[i] == '\\') {
                ++i
                ret = when (str[i]) {
                    'b'  -> '\b'
                    'n'  -> '\n'
                    'r'  -> '\r'
                    't'  -> '\t'
                    '\\' -> '\\'
                    '\'' -> '\''
                    '\"' -> '\"'
                    else -> 0.toChar()
                }
                ++i
            } else {
                ret = str[i]
                ++i
            }
            return ret
        }

    fun firstSymbolIs(symbol: String): Boolean {
        removeBeginningBlank()
        return !isEmpty() && str.startsWith(symbol, i) && (i + symbol.length >= str.length || !isPartOfSymbol(str[i + symbol.length]))
    }

    fun firstOperatorIs(operator: String): Boolean {
        removeBeginningBlank()
        return when (operator) {
            "+", "-", "=", "&", "|" -> str.startsWith(operator, i) && !str.startsWith(operator + operator, i)
            ">", "<", "!"           -> str.startsWith(operator, i) && !str.startsWith(operator + "=", i)
            else                    -> str.startsWith(operator, i)
        }
    }
}
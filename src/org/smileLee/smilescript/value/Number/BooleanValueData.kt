package org.smileLee.smilescript.value.Number

import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.value.*
import org.smileLee.smilescript.value.`object`.*

/**
 * Created by Administrator on 2017/6/6.
 *
 * @author smileLee
 */
class BooleanValueData(var value: Boolean) : IValueData {
    override fun typeName() = "boolean"

    override fun toInt() = if (value) 1 else 0
    override fun toDouble() = if (value) 1.toDouble() else 0.toDouble()
    override fun toBoolean() = value
    override fun toFloat() = if (value) 1.toFloat() else 0.toFloat()
    override fun toChar() = if (value) 1.toChar() else 0.toChar()
    override fun toLong() = if (value) 1.toLong() else 0.toLong()
    override fun toString() = value.toString()

    override fun clone(): BooleanValueData = BooleanValueData(value)

    override fun castToObject() = ObjectValueData(this, ObjectValueData.Number)

    override fun toCodeString(tab: String) = CodeString(toString())
}
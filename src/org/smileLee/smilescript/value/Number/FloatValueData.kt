package org.smileLee.smilescript.value.Number

import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.value.*
import org.smileLee.smilescript.value.`object`.*

/**
 * Created by Administrator on 2017/6/6.
 *
 * @author smileLee
 */
class FloatValueData(var value: Float) : IValueData {
    override fun typeName() = "float"

    override fun toInt() = value.toInt()
    override fun toDouble() = value.toDouble()
    override fun toBoolean() = value != 0.0f
    override fun toFloat() = value.toFloat()
    override fun toChar() = value.toChar()
    override fun toLong() = value.toLong()
    override fun toString() = value.toString()

    override fun clone() = FloatValueData(value)

    override fun castToObject() = ObjectValueData(this, ObjectValueData.Number)

    override fun toCodeString(tab: String) = CodeString(value.toString() + "f")
}
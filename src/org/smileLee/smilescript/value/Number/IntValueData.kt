package org.smileLee.smilescript.value.Number

import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.value.*
import org.smileLee.smilescript.value.`object`.*

/**
 * Created by Administrator on 2017/6/6.
 *
 * @author smileLee
 */
class IntValueData(var value: Int) : IValueData {
    override fun typeName() = "int"

    override fun toInt() = value.toInt()
    override fun toDouble() = value.toDouble()
    override fun toBoolean() = value != 0
    override fun toFloat() = value.toFloat()
    override fun toChar() = value.toChar()
    override fun toLong() = value.toLong()
    override fun toString() = value.toString()

    override fun clone() = IntValueData(value)

    override fun castToObject() = ObjectValueData(this, ObjectValueData.Number)

    override fun toCodeString(tab: String) = CodeString(value.toString())
}
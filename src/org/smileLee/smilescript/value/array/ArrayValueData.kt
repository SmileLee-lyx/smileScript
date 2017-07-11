package org.smileLee.smilescript.value.array

import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.value.*
import org.smileLee.smilescript.value.`object`.*

/**
 * Created by Administrator on 2017/6/8.
 * @author smileLee
 */

class ArrayValueData(val elements: ArrayList<Value>) : IValueData {
    override fun typeName() = "array"

    override fun toInt() = if (elements.size > 0) elements[0].toInt() else 0
    override fun toDouble() = if (elements.size > 0) elements[0].toDouble() else 0.toDouble()
    override fun toBoolean() = if (elements.size > 0) elements[0].toBoolean() else false
    override fun toFloat() = if (elements.size > 0) elements[0].toFloat() else 0.toFloat()
    override fun toChar() = if (elements.size > 0) elements[0].toChar() else 0.toChar()
    override fun toLong() = if (elements.size > 0) elements[0].toLong() else 0.toLong()
    override fun toString(): String {
        val s = StringBuilder("[")
        for (i in 0..(elements.size - 1)) {
            s.append(elements[i].toString())
            if (i != elements.size - 1) s.append(", ")
        }
        s.append("]")
        return s.toString()
    }

    override fun clone() = ArrayValueData(elements)

    operator fun get(i: Int) = if (i >= 0 && i < elements.size) elements[i] else Value()

    override fun castToObject() = ObjectValueData(this, ObjectValueData.Array)

    override fun toCodeString(tab: String) = CodeString()
}
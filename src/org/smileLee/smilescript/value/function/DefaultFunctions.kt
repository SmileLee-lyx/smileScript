package org.smileLee.smilescript.value.function

import org.smileLee.smilescript.expression.controlExpression.*
import org.smileLee.smilescript.stack.*
import org.smileLee.smilescript.value.*
import org.smileLee.smilescript.value.Number.*
import org.smileLee.smilescript.value.`object`.*
import org.smileLee.smilescript.value.array.*

/**
 * Created by Administrator on 2017/6/8.
 * @author smileLee
 */

class PrintFunction(val line: Boolean) : FunctionValueData(ArrayList(), Block(), Stack(false), false) {
    override fun invoke(providedParams: List<Value?>, thisReference: ThisReference): Value {
        for (param in providedParams) {
            if (line) println(param) else print(param)
        }
        return Value()
    }

    override fun toString() = "System.print" + if (line) "ln" else ""
}

class ObjectAutoCloneFunction : FunctionValueData(ArrayList(), Block(), Stack(false), false) {
    override fun invoke(providedParams: List<Value?>, thisReference: ThisReference): Value = if (thisReference.thisReference == null) Value(IntValueData(0)) else Value(thisReference.thisReference!!.clone())

    override fun toString() = "Object.clone"
}

class ObjectAutoToStringFunction : FunctionValueData(ArrayList(), Block(), Stack(false), false) {
    override fun invoke(providedParams: List<Value?>, thisReference: ThisReference): Value {
        val s = StringBuilder("{")
        if (thisReference.thisReference == null) return Value(StringValueData(""))
        for (e in thisReference.thisReference!!.fields) {
            s.append(" ${e.key}:${e.value};")
        }
        s.append("}")
        return Value(StringValueData(s.toString()))
    }

    override fun toString() = "Object.toString"
}

class ObjectAutoValueOfFunction : FunctionValueData(ArrayList(), Block(), Stack(false), false) {
    override fun invoke(providedParams: List<Value?>, thisReference: ThisReference): Value = if (thisReference.thisReference == null) Value(IntValueData(0)) else thisReference.thisReference!!["value"]

    override fun toString() = "Object.valueOf"
}

class NumberAutoToStringFunction : FunctionValueData(ArrayList(), Block(), Stack(false), false) {
    override fun invoke(providedParams: List<Value?>, thisReference: ThisReference): Value = if (thisReference.thisReference == null) {
        Value(IntValueData(0))
    } else {
        Value(StringValueData(thisReference.thisReference!!["value"].toString()))
    }

    override fun toString() = "Number.toString"
}

class ArrayAutoGetFunction : FunctionValueData(ArrayList(), Block(), Stack(false), false) {
    override fun invoke(providedParams: List<Value?>, thisReference: ThisReference): Value = if (thisReference.thisReference == null) {
        Value()
    } else {
        Value(thisReference.thisReference!!)[if (providedParams.isNotEmpty()) (providedParams[0] ?: Value()) else Value()]
    }

    override fun toString() = "Array.get"
}

class ArrayAutoLengthFunction : FunctionValueData(ArrayList(), Block(), Stack(false), false) {
    override fun invoke(providedParams: List<Value?>, thisReference: ThisReference): Value = if (thisReference.thisReference != null) {
        val array = thisReference.thisReference!!.valueOf().value
        try {
            when (array) {
                is ArrayValueData -> Value(IntValueData(array.elements.size))
                else              -> Value()
            }
        } catch (e: ArrayIndexOutOfBoundsException) {
            Value()
        }
    } else {
        Value()
    }

    override fun toString() = "Array.length"
}
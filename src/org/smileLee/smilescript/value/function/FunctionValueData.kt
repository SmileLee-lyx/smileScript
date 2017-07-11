package org.smileLee.smilescript.value.function

import org.smileLee.smilescript.exception.*
import org.smileLee.smilescript.expression.*
import org.smileLee.smilescript.expression.controlExpression.*
import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.stack.*
import org.smileLee.smilescript.value.*
import org.smileLee.smilescript.value.`object`.*
import org.smileLee.smilescript.value.array.*

/**
 * Created by Administrator on 2017/6/8.
 *
 * @author smileLee
 */
open class FunctionValueData(val params: List<ParameterInfo>,
                             val statements: Block,
                             val capture: Stack,
                             val returnsReference: Boolean,
                             val label: String = "") : IValueData {
    override fun typeName() = "function"

    override fun toInt() = 1
    override fun toDouble() = 1.toDouble()
    override fun toBoolean() = true
    override fun toFloat() = 1.toFloat()
    override fun toChar() = 1.toChar()
    override fun toLong() = 1.toLong()
    override fun toString(): String {
        val s = StringBuilder("function ${if (returnsReference) "&" else ""}(")
        for ((name, isReference) in params) {
            s.append("${if (isReference) "&" else ""}$name, ")
        }
        if (params.isNotEmpty()) s.delete(s.length - 2, s.length)
        s.append(")")
        return s.toString()
    }

    override fun clone() = FunctionValueData(params, statements, capture, returnsReference)

    open operator fun invoke(providedParams: List<Value?>) = this(providedParams, ThisReference())
    open operator fun invoke(providedParams: List<Value?>, thisReference: ThisReference): Value {
        val s = capture.clone()
        s.with(thisReference)
        val arguments = ArrayList<Value>()
        for ((i, param) in params.withIndex()) {
            val actualParam: Value = if (i < providedParams.size) providedParams[i] ?: param.init(s) else param.init(s)
            val passedParam = if (param.isReference) actualParam else actualParam.clone()
            s[param.name] = passedParam
            arguments.add(passedParam)
        }
        (params.size..(providedParams.size - 1)).mapTo(arguments) { (providedParams[it] ?: Value()).clone() }
        s["arguments"] = Value(ArrayValueData(arguments))
        try {
            statements(s)
        } catch(e: ResultException) {
            if (e.label != "" && e.label != label) {
                throw e
            }
            return if (returnsReference) e.result else e.result.clone()
        }
        return Value()
    }

    override fun castToObject(): ObjectValueData = ObjectValueData(this, ObjectValueData.Function)

    data class ParameterInfo(val name: String, val isReference: Boolean, val init: IExpression)

    override fun toCodeString(tab: String) = CodeString()
}
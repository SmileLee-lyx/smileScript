package org.smileLee.smilescript.expression.symbolExpression

import org.smileLee.smilescript.expression.*
import org.smileLee.smilescript.expression.controlExpression.*
import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.stack.*
import org.smileLee.smilescript.value.*
import org.smileLee.smilescript.value.function.*
import org.smileLee.smilescript.value.function.FunctionValueData.*

/**
 * Created by Administrator on 2017/6/8.
 *
 * @author smileLee
 */
open class FunctionExpression(val params: List<ParameterInfo>,
                              val statements: Block,
                              val returnsReference: Boolean,
                              val valueCaptures: List<ConstValueCapture>,
                              val name: String) : IExpression {

    override fun invoke(s: Stack): Value {
        val capture = s.clone()
        for ((captureName, init) in valueCaptures) {
            capture[captureName] = init.invoke(s).clone()
        }
        return Value(FunctionValueData(params, statements, capture, returnsReference, name))
    }

    override fun toCodeString(tab: String): CodeString {
        val newTab = tab + "    "
        val codeString = CodeString("function")
        if (name != "") codeString.append("@").append(name)
        codeString.append(" ")
        if (returnsReference) codeString.append("&")
        codeString.append("(")
        for ((name, isReference, init) in params) {
            val initExpression = init.toCodeString(tab)
            if (isReference) codeString.append("&")
            codeString.append(name)
            if (initExpression.toString() != "null") {
                codeString.append(" = ").append(initExpression)
            }
            codeString.append(", ")
        }
        if (params.isNotEmpty()) codeString.removeFromEnd(2)
        codeString.append(") ")
        if (valueCaptures.isNotEmpty()) {
            codeString.append("[")
            for ((name, init) in valueCaptures) {
                val initExpression = init.toCodeString(tab)
                codeString.append(name)
                if (initExpression.toString() != name) {
                    codeString.append(" = ").append(initExpression)
                }
                codeString.append(", ")
            }
            codeString.removeFromEnd(2)
            codeString.append("] ")
        }
        codeString.append("{\n").append(statements.toCodeString(newTab)).append(tab).append("}")
        return codeString
    }

    data class ConstValueCapture(val captureName: String, val init: IExpression)

}
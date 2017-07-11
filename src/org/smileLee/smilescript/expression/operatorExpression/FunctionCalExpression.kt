package org.smileLee.smilescript.expression.operatorExpression

import org.smileLee.smilescript.expression.*
import org.smileLee.smilescript.expression.symbolExpression.*
import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.stack.*
import org.smileLee.smilescript.value.*
import org.smileLee.smilescript.value.`object`.*

/**
 * Created by Administrator on 2017/6/6.
 * @author smileLee
 */

class FunctionCalExpression(val function: IExpression, val params: ArrayList<IExpression>) : IExpression {
    override fun invoke(s: Stack): Value {
        val providedParams = ArrayList<Value?>()
        params.mapTo(providedParams) {
            if (it is LeftOutParamExpression) null else it.invoke(s)
        }
        val thisReference = ThisReference()
        val func = function(s, thisReference)
        return func(providedParams, thisReference)
    }

    override fun toCodeString(tab: String): CodeString {
        val newTab = tab + "    "
        val codeString = function.toCodeString(tab)
        codeString.append("(")
        for (param in params) {
            codeString.append(param.toCodeString(newTab)).append(", ")
        }
        if (params.isNotEmpty()) codeString.removeFromEnd(2)
        codeString.append(")")
        return codeString
    }
}
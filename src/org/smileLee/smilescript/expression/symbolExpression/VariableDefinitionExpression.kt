package org.smileLee.smilescript.expression.symbolExpression

import org.smileLee.smilescript.expression.*
import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.stack.*
import org.smileLee.smilescript.value.*

/**
 * Created by Administrator on 2017/6/7.
 * @author smileLee
 */

class VariableDefinitionExpression(val variables: List<VariableInfo>) : IExpression {
    override fun invoke(s: Stack): Value {
        var ret = Value()
        for ((varName, init) in variables) {
            s[varName] = init.invoke(s)
            ret = s[varName]
        }
        return ret
    }

    data class VariableInfo(val varName: String, val init: IExpression)

    override fun toCodeString(tab: String): CodeString {
        val codeString = CodeString("var ")
        for ((varName, init) in variables) {
            codeString.append(varName).append(" = ").append(init.toCodeString(tab)).append(", ")
        }
        if (variables.isNotEmpty()) codeString.removeFromEnd(2)
        return codeString
    }
}
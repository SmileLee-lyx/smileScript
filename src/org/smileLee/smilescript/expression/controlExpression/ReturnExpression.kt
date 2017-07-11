package org.smileLee.smilescript.expression.controlExpression

import org.smileLee.smilescript.exception.*
import org.smileLee.smilescript.expression.*
import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.stack.*
import org.smileLee.smilescript.value.*

/**
 * Created by Administrator on 2017/6/6.
 * @author smileLee
 */

class ReturnExpression(val result: IExpression, val label: String) : IExpression {
    override fun invoke(s: Stack): Value {
        throw ResultException(result.invoke(s), label)
    }

    override fun toCodeString(tab: String): CodeString {
        val newTab = tab + "    "
        val codeString = CodeString("return")
        if (label != "") codeString.append("@").append(label)
        codeString.append(" ").append(result.toCodeString(newTab))
        return codeString
    }
}
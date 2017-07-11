package org.smileLee.smilescript.expression.controlExpression

import org.smileLee.smilescript.expression.*
import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.stack.*
import org.smileLee.smilescript.value.*

/**
 * Created by Administrator on 2017/6/7.
 *
 * @author smileLee
 */

class IfExpression(val condition: IExpression,
                   val trueStatements: Block,
                   val falseStatements: Block) : IExpression {

    override fun invoke(s: Stack): Value {
        return if (condition(s).toBoolean()) trueStatements(s) else falseStatements(s)
    }

    override fun toCodeString(tab: String): CodeString {
        val newTab = tab + "    "
        val codeString = CodeString("if (")
        codeString.append(condition.toCodeString(tab)).append(") {\n")
                .append(trueStatements.toCodeString(newTab))
                .append(tab).append("}")
        if (falseStatements.statements.isNotEmpty()) {
            codeString.append(" else {")
                    .append(trueStatements.toCodeString(newTab))
                    .append(tab).append("}")
        }
        return codeString
    }
}
package org.smileLee.smilescript.expression.controlExpression

import org.smileLee.smilescript.exception.*
import org.smileLee.smilescript.expression.*
import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.stack.*
import org.smileLee.smilescript.value.*

/**
 * Created by Administrator on 2017/6/7.
 *
 * @author smileLee
 */

class ForExpression(val initialization: IExpression,
                    val condition: IExpression,
                    val update: IExpression,
                    val statements: Block,
                    val name: String) : IExpression {

    override fun invoke(s: Stack): Value {
        var ret = Value()
        initialization(s)
        try {
            while (condition(s).toBoolean()) {
                ret = statements(s)
                update(s)
            }
        } catch(e: BreakException) {
            if (e.label != "" && e.label != name) {
                throw e
            }
            //do nothing
        }
        return ret
    }

    override fun toCodeString(tab: String): CodeString {
        val newTab = tab + "    "
        val codeString = CodeString("for")
        if (name != "") {
            codeString.append("@").append(name)
        }
        codeString.append(" (")
        codeString.append(initialization.toCodeString(tab)).append("; ")
                .append(condition.toCodeString(tab)).append("; ")
                .append(update.toCodeString(tab)).append(") {\n")
                .append(statements.toCodeString(newTab))
                .append(tab).append("}")
        return codeString
    }
}
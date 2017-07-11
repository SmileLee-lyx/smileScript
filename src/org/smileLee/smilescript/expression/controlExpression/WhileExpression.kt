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

class WhileExpression(val condition: IExpression,
                      val statements: Block,
                      val name: String) : IExpression {

    override fun invoke(s: Stack): Value {
        var ret = Value()
        try {
            while (condition(s).toBoolean()) {
                ret = statements(s)
            }
        } catch(e: BreakException) {
            if (e.label != "" && e.label != name) {
                throw e
            }
        }
        return ret
    }

    override fun toCodeString(tab: String): CodeString {
        val newTab = tab + "    "
        val codeString = CodeString("while")
        if (name != "") {
            codeString.append("@").append(name)
        }
        codeString.append(" (")
        codeString.append(condition.toCodeString(newTab)).append(") {\n")
                .append(statements.toCodeString(newTab))
                .append(tab).append("}")
        return codeString
    }
}
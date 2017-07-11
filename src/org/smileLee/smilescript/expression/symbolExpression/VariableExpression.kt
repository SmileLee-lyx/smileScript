package org.smileLee.smilescript.expression.symbolExpression

import org.smileLee.smilescript.expression.*
import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.stack.*
import org.smileLee.smilescript.value.*

/**
 * Created by Administrator on 2017/6/7.
 * @author smileLee
 */

class VariableExpression(val str: String) : IExpression {
    override fun invoke(s: Stack): Value {
        return s[str]
    }

    override fun toCodeString(tab: String) = CodeString(str)
}
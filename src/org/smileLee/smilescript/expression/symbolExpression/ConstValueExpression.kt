package org.smileLee.smilescript.expression.symbolExpression

import org.smileLee.smilescript.expression.*
import org.smileLee.smilescript.stack.*
import org.smileLee.smilescript.value.*

/**
 * Created by Administrator on 2017/6/7.
 * @author smileLee
 */

class ConstValueExpression(val value: IValueData) : IExpression {
    override fun invoke(s: Stack): Value {
        return Value(value)
    }

    override fun toCodeString(tab: String) = value.toCodeString(tab)
}
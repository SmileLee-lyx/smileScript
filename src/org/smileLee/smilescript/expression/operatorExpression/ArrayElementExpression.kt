package org.smileLee.smilescript.expression.operatorExpression

import org.smileLee.smilescript.expression.*
import org.smileLee.smilescript.stack.*
import org.smileLee.smilescript.value.*

/**
 * Created by Administrator on 2017/6/6.
 * @author smileLee
 */

class ArrayElementExpression(val array: IExpression, val index: IExpression) : IExpression {
    override fun invoke(s: Stack): Value = array(s)[index(s)]
    override fun toCodeString(tab: String) = array.toCodeString(tab).append("[").append(index.toCodeString(tab)).append("]")
}
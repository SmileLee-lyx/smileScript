package org.smileLee.smilescript.expression.controlExpression

import org.smileLee.smilescript.analyzer.*
import org.smileLee.smilescript.expression.*
import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.stack.Stack
import org.smileLee.smilescript.value.*
import java.util.*

/**
 * Created by Administrator on 2017/6/7.
 *
 * @author smileLee
 */

class Block : IExpression {
    val statements: List<IExpression>

    companion object {
        fun parse(s: String): Block {
            return parse(StringAnalyzer(s))
        }

        fun parse(s: StringAnalyzer): Block {
            val statements = ArrayList<IExpression>()
            while (!s.isEmpty() && !s.firstOperatorIs("}")) {
                statements.add(IExpression.parse(s))
                while (s.firstOperatorIs(";")) s.removeBeginning(1)
            }
            return Block(statements)
        }
    }

    constructor() {
        statements = ArrayList()
    }

    constructor(statement: IExpression) {
        this.statements = Collections.singletonList(statement)
    }

    constructor(statements: List<IExpression>) {
        this.statements = statements
    }

    override fun invoke(s: Stack): Value {
        var ret = Value()
        for (statement in statements) {
            ret = statement(s)
        }
        return ret
    }

    override fun toCodeString(tab: String): CodeString {
        val codeString: CodeString = CodeString()
        for (statement in statements) {
            codeString.append(tab).append(statement.toCodeString(tab)).append("\n")
        }
        return codeString
    }
}
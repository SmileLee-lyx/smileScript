package org.smileLee.smilescript.expression

import org.smileLee.smilescript.analyzer.*
import org.smileLee.smilescript.exception.*
import org.smileLee.smilescript.expression.controlExpression.*
import org.smileLee.smilescript.expression.operatorExpression.*
import org.smileLee.smilescript.expression.symbolExpression.*
import org.smileLee.smilescript.expression.symbolExpression.FunctionExpression.*
import org.smileLee.smilescript.expression.symbolExpression.VariableDefinitionExpression.*
import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.stack.*
import org.smileLee.smilescript.value.*
import org.smileLee.smilescript.value.Number.*
import org.smileLee.smilescript.value.`object`.*
import org.smileLee.smilescript.value.function.FunctionValueData.*

/**
 * Created by Administrator on 2017/6/6.
 *
 * @author smileLee
 */
interface IExpression : IParsableToCodeString {
    companion object {
        fun parse(s: String): IExpression = parse(StringAnalyzer(s))
        fun parse(s: StringAnalyzer): IExpression = analyseEvaluation(s)
        fun analyseEvaluation(s: StringAnalyzer): IExpression {
            var left: IExpression = analyseAndOr(s)
            if (s.firstOperatorIs("=")) {
                s.removeBeginning(1)
                left = EvaluationExpression(left, analyseEvaluation(s))
            }
            return left
        }

        fun analyseAndOr(s: StringAnalyzer): IExpression {
            var left: IExpression = analyseComparision(s)
            while (!s.isEmpty()) {
                if (s.firstOperatorIs("&&")) {
                    s.removeBeginning(2)
                    left = AndExpression(left, analyseComparision(s))
                } else if (s.firstOperatorIs("||")) {
                    s.removeBeginning(2)
                    left = OrExpression(left, analyseComparision(s))
                } else {
                    return left
                }
            }
            return left
        }

        fun analyseComparision(s: StringAnalyzer): IExpression {
            var left: IExpression = analyseAddSub(s)
            while (!s.isEmpty()) {
                if (s.firstOperatorIs(">")) {
                    s.removeBeginning(1)
                    left = GreaterExpression(left, analyseAddSub(s))
                } else if (s.firstOperatorIs("<")) {
                    s.removeBeginning(1)
                    left = LessExpression(left, analyseAddSub(s))
                } else if (s.firstOperatorIs(">=")) {
                    s.removeBeginning(2)
                    left = GreaterEqualExpression(left, analyseAddSub(s))
                } else if (s.firstOperatorIs("<=")) {
                    s.removeBeginning(2)
                    left = LessEqualExpression(left, analyseAddSub(s))
                } else if (s.firstOperatorIs("==")) {
                    s.removeBeginning(2)
                    left = EqualExpression(left, analyseAddSub(s))
                } else if (s.firstOperatorIs("!=")) {
                    s.removeBeginning(2)
                    left = NotEqualExpression(left, analyseAddSub(s))
                } else {
                    return left
                }
            }
            return left
        }

        fun analyseAddSub(s: StringAnalyzer): IExpression {
            var left: IExpression = analyseUnaryPlusMinus(s)
            while (!s.isEmpty()) {
                if (s.firstOperatorIs("+")) {
                    s.removeBeginning(1)
                    left = AddExpression(left, analyseUnaryPlusMinus(s))
                } else if (s.firstOperatorIs("-")) {
                    s.removeBeginning(1)
                    left = SubExpression(left, analyseUnaryPlusMinus(s))
                } else {
                    return left
                }
            }
            return left
        }

        fun analyseUnaryPlusMinus(s: StringAnalyzer): IExpression {
            if (s.firstOperatorIs("+")) {
                s.removeBeginning(1)
                return UnaryPlusExpression(analyseUnaryPlusMinus(s))
            } else if (s.firstOperatorIs("-")) {
                s.removeBeginning(1)
                return UnaryMinusExpression(analyseUnaryPlusMinus(s))
            } else {
                return analyseMulDivMod(s)
            }
        }

        fun analyseMulDivMod(s: StringAnalyzer): IExpression {
            var left: IExpression = analyseRightIncDec(s)
            while (!s.isEmpty()) {
                if (s.firstOperatorIs("*")) {
                    s.removeBeginning(1)
                    left = MulExpression(left, analyseRightIncDec(s))
                } else if (s.firstOperatorIs("/")) {
                    s.removeBeginning(1)
                    left = DivExpression(left, analyseRightIncDec(s))
                } else if (s.firstOperatorIs("%")) {
                    s.removeBeginning(1)
                    left = ModExpression(left, analyseRightIncDec(s))
                } else {
                    return left
                }
            }
            return left
        }

        fun analyseRightIncDec(s: StringAnalyzer): IExpression {
            var left: IExpression = analyseLeftIncDec(s)
            while (!s.isEmpty()) {
                if (s.firstOperatorIs("++")) {
                    s.removeBeginning(2)
                    left = RightIncExpression(left)
                } else if (s.firstOperatorIs("--")) {
                    s.removeBeginning(2)
                    left = RightDecExpression(left)
                } else {
                    return left
                }
            }
            return left
        }

        fun analyseLeftIncDec(s: StringAnalyzer): IExpression {
            if (s.firstOperatorIs("++")) {
                s.removeBeginning(2)
                return LeftIncExpression(analyseLeftIncDec(s))
            } else if (s.firstOperatorIs("--")) {
                s.removeBeginning(2)
                return LeftDecExpression(analyseLeftIncDec(s))
            } else {
                return analyseInvokeGetObjectField(s)
            }
        }

        fun analyseInvokeGetObjectField(s: StringAnalyzer): IExpression {
            var left = analyseSymbol(s)
            while (!s.isEmpty()) {
                if (s.firstOperatorIs("(")) {
                    s.removeBeginning(1)
                    val params = ArrayList<IExpression>()
                    while (!s.firstOperatorIs(")")) {
                        params.add(if (s.firstOperatorIs(",")) LeftOutParamExpression() else IExpression.parse(s))
                        if (s.firstOperatorIs(",")) s.removeBeginning(1)
                    }
                    s.removeAssertingBeginningIs(")")
                    left = FunctionCalExpression(left, params)
                } else if (s.firstOperatorIs("[")) {
                    s.removeBeginning(1)
                    val index = analyseEvaluation(s)
                    s.removeAssertingBeginningIs("]")
                    left = ArrayElementExpression(left, index)
                } else if (s.firstOperatorIs(".")) {
                    s.removeBeginning(1)
                    val field = s.symbol
                    left = ObjectFieldExpression(left, field)
                } else {
                    return left
                }
            }
            return left
        }

        fun analyseSymbol(s: StringAnalyzer): IExpression {
            if (s.firstOperatorIs("(")) {
                s.removeBeginning(1)
                val content: IExpression = analyseEvaluation(s)
                s.removeBeginning(1)
                return BracketExpression(content)
            } else if (s.firstSymbolIs("true")) {
                s.removeBeginning(4)
                return ConstValueExpression(BooleanValueData(true))
            } else if (s.firstSymbolIs("false")) {
                s.removeBeginning(5)
                return ConstValueExpression(BooleanValueData(false))
            } else if (s.firstSymbolIs("null")) {
                s.removeBeginning(4)
                return ConstValueExpression(NullValueData())
            } else if (s.firstSymbolIs("var")) {
                s.removeBeginning(3)
                val variables: ArrayList<VariableInfo> = ArrayList()
                while (true) {
                    val varName = if (s.beginWithSymbol) s.symbol else throw SyntaxException()
                    val init = if (s.firstOperatorIs("=")) {
                        s.removeBeginning(1)
                        analyseEvaluation(s)
                    } else ConstValueExpression(NullValueData())
                    variables.add(VariableInfo(varName, init))
                    if (s.firstOperatorIs(",")) {
                        s.removeBeginning(1)
                    } else break
                }
                return VariableDefinitionExpression(variables)
            } else if (s.firstSymbolIs("return")) {
                s.removeBeginning(6)
                val name = if (s.firstOperatorIs("@")) {
                    s.removeBeginning(1)
                    s.symbol
                } else ""
                return ReturnExpression(analyseEvaluation(s), name)
            } else if (s.firstSymbolIs("break")) {
                s.removeBeginning(5)
                val label = if (s.firstOperatorIs("@")) {
                    s.removeBeginning(1)
                    s.symbol
                } else ""
                return BreakExpression(label)
            } else if (s.firstSymbolIs("if")) {
                s.removeBeginning(2)
                s.removeAssertingBeginningIs("(")
                val condition = IExpression.parse(s)
                s.removeAssertingBeginningIs(")")
                val trueStatements: Block
                if (s.firstOperatorIs("{")) {
                    s.removeBeginning(1)
                    trueStatements = Block.parse(s)
                    s.removeAssertingBeginningIs("}")
                } else {
                    trueStatements = Block(parse(s))
                }
                val falseStatements: Block
                if (s.firstSymbolIs("else")) {
                    s.removeBeginning(4)
                    if (s.firstOperatorIs("{")) {
                        s.removeBeginning(1)
                        falseStatements = Block.parse(s)
                        s.removeAssertingBeginningIs("}")
                    } else {
                        falseStatements = Block(parse(s))
                    }
                } else {
                    falseStatements = Block()
                }
                return IfExpression(condition, trueStatements, falseStatements)
            } else if (s.firstSymbolIs("for")) {
                s.removeBeginning(3)
                val label = if (s.firstOperatorIs("@")) {
                    s.removeBeginning(1)
                    s.symbol
                } else ""
                s.removeAssertingBeginningIs("(")
                val initialization = IExpression.parse(s)
                s.removeAssertingBeginningIs(";")
                val condition = IExpression.parse(s)
                s.removeAssertingBeginningIs(";")
                val update = IExpression.parse(s)
                val statements: Block
                s.removeAssertingBeginningIs(")")
                if (s.firstOperatorIs("{")) {
                    s.removeBeginning(1)
                    statements = Block.parse(s)
                    s.removeAssertingBeginningIs("}")
                } else {
                    statements = Block(parse(s))
                }
                return ForExpression(initialization, condition, update, statements, label)
            } else if (s.firstSymbolIs("while")) {
                s.removeBeginning(5)
                val label = if (s.firstOperatorIs("@")) {
                    s.removeBeginning(1)
                    s.symbol
                } else ""
                s.removeAssertingBeginningIs("(")
                val condition = IExpression.parse(s)
                s.removeAssertingBeginningIs(")")
                val statements: Block
                if (s.firstOperatorIs("{")) {
                    s.removeBeginning(1)
                    statements = Block.parse(s)
                    s.removeAssertingBeginningIs("}")
                } else {
                    statements = Block(parse(s))
                }
                return WhileExpression(condition, statements, label)
            } else if (s.firstSymbolIs("function")) {
                s.removeBeginning(8)
                val label = if (s.firstOperatorIs("@")) {
                    s.removeBeginning(1)
                    s.symbol
                } else ""
                val returnsReference = if (s.firstOperatorIs("&")) {
                    s.removeBeginning(1)
                    true
                } else false
                val params = ArrayList<ParameterInfo>()
                s.removeAssertingBeginningIs("(")
                while (!s.firstOperatorIs(")")) {
                    val isReference = if (s.firstOperatorIs("&")) {
                        s.removeBeginning(1)
                        true
                    } else false
                    val name = s.symbol
                    val init: IExpression
                    if (s.firstOperatorIs("=")) {
                        s.removeBeginning(1)
                        init = analyseEvaluation(s)
                    } else {
                        init = ConstValueExpression(NullValueData())
                    }
                    params.add(ParameterInfo(name, isReference, init))
                    if (s.firstOperatorIs(",")) {
                        s.removeBeginning(1)
                    }
                }
                s.removeAssertingBeginningIs(")")
                val valueCaptures = ArrayList<ConstValueCapture>()
                if (s.firstOperatorIs("[")) {
                    s.removeBeginning(1)
                    while (!s.firstOperatorIs("]")) {
                        val name = s.symbol
                        val init = if (s.firstOperatorIs("=")) {
                            s.removeBeginning(1)
                            analyseEvaluation(s)
                        } else {
                            VariableExpression(name)
                        }
                        valueCaptures.add(ConstValueCapture(name, init))
                        if (s.firstOperatorIs(",")) {
                            s.removeBeginning(1)
                        }
                    }
                    s.removeAssertingBeginningIs("]")
                }
                s.removeAssertingBeginningIs("{")
                val statements = Block.parse(s)
                s.removeAssertingBeginningIs("}")
                return FunctionExpression(params, statements, returnsReference, valueCaptures, label)
            } else if (s.firstOperatorIs("[")) {
                val elements = ArrayList<IExpression>()
                s.removeBeginning(1)
                while (!s.firstOperatorIs("]")) {
                    elements.add(analyseEvaluation(s))
                    if (s.firstOperatorIs(",")) {
                        s.removeBeginning(1)
                    }
                }
                s.removeAssertingBeginningIs("]")
                return ArrayExpression(elements)
            } else if (s.firstOperatorIs("{")) {
                val fields = HashMap<String, IExpression>()
                s.removeBeginning(1)
                while (!s.firstOperatorIs("}")) {
                    val name = s.symbol
                    val init: IExpression
                    if (s.firstOperatorIs(":")) {
                        s.removeBeginning(1)
                        init = analyseEvaluation(s)
                    } else {
                        init = ConstValueExpression(NullValueData())
                    }
                    fields[name] = init
                    if (s.firstOperatorIs(";")) {
                        s.removeBeginning(1)
                    }
                }
                s.removeAssertingBeginningIs("}")
                return ObjectExpression(fields)
            } else if (s.beginWithSymbol) {
                return VariableExpression(s.symbol)
            } else if (s.beginWithNumber) {
                return ConstValueExpression(IValueData.parse(s.number))
            } else if (s.firstOperatorIs("\'")) {
                return ConstValueExpression(CharValueData(s.char))
            } else if (s.firstOperatorIs("\"")) {
                return ConstValueExpression(StringValueData(s.string))
            } else throw SyntaxException()
        }
    }

    operator fun invoke(s: Stack): Value
    operator fun invoke(s: Stack, thisReference: ThisReference): Value {
        thisReference.thisReference = null
        return this(s)
    }
}

package com.arm.spring.el;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author z-ewa
 */
public class Test1 {
    public static void main(String[] args) {
        test3();
    }

    private static void test1() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("('Hello' + ' World').concat(#end)");
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("end", "!");
        System.out.println(expression.getValue(context));
    }

    private static void test2() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("('Hello' + ' World').concat(#sql_no)");
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("sql_no", "!");
        System.out.println(expression.getValue(context));
    }

    private static void test3() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("T(java.lang.Math).random()");
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("sql_no", "!");
        System.out.println(expression.getValue(context));
    }


}

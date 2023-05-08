package interpreter.expr;

import interpreter.value.BoolValue;
import interpreter.value.Value;

public class ConditionalExpr extends Expr{
    
    private Expr cond;
    private Expr trueExpr;
    private Expr falseExpr;

    public ConditionalExpr(int line, Expr cond, Expr trueExpr, Expr falseExpr) {
        super(line);
        this.cond = cond;
        this.trueExpr = trueExpr;
        this.falseExpr = falseExpr;
    }

    
    @Override
    public Value<?> expr() {
        Value<?> value = cond.expr();
        boolean boolValue =  BoolValue.convert(value);

        if (boolValue) {
            Value<?> value_aux = trueExpr.expr();
            return value_aux;       
        } else {
            Value<?> value_aux2 = falseExpr.expr();
            return value_aux2;
        }
    }
}

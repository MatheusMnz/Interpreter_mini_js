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
        Value<?> v1 = cond.expr();
        boolean b1 =  BoolValue.convert(v1);

        if (b1) {
            Value<?> v2 = trueExpr.expr();
            return v2;       
        } else {
            Value<?> v3 = falseExpr.expr();
            return v3;
        }
    }
}

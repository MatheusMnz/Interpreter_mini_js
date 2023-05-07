package interpreter.expr;

import interpreter.value.*;

public class AccessExpr extends SetExpr{

    private SetExpr base;
    private Expr index;

    public AccessExpr(int line, SetExpr base, Expr index)
    {
        super(line);
        this.base = base;
        this.index = index;
    }

    @Override
    public Value<?> expr() { //Ta errado, botei só pro VS parar de reclamar
        Value<Expr> v1 = null;

        return v1;
    }

    @Override
    public void setValue(Value<?> value) {

    }

}
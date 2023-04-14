package interpreter.expr;

import interpreter.value.BoolValue;
import interpreter.value.Value;

public class UnaryExpr extends Expr {
    
    public static enum Op {
        NotOp,
        PosOp,
        NegOp,
        PreInc,
        PosInc,
        PreDec,
        PosDec
    }

    private Expr expr;
    private Op op;

    public UnaryExpr(int line, Expr expr, Op op) {
        super(line);
        this.expr = expr;
        this.op = op;
    }

    @Override
    public Value<?> expr() {
        Value<?> v = expr.expr();

        switch (this.op) {
            case NotOp:
                return notOp(v);
            case PosOp:
                return posOp(v);
            case NegOp:
                return negOp(v);
            case PreInc:
                return preIncOp(v);
            case PosInc:
                return posIncOp(v);
            case PreDec:
                return preDecOp(v);
            case PosDec:
            default:
                return posDecOp(v);
        }
    }

    private Value<?> notOp(Value<?> v) {
        boolean b = BoolValue.convert(v);
        return new BoolValue(!b);
    }

    private Value<?> posOp(Value<?> v) {
        throw new RuntimeException("implement me!");
    }

    private Value<?> negOp(Value<?> v) {
        throw new RuntimeException("implement me!");
    }

    private Value<?> preIncOp(Value<?> v) {
        throw new RuntimeException("implement me!");
    }

    private Value<?> posIncOp(Value<?> v) {
        throw new RuntimeException("implement me!");
    }

    private Value<?> preDecOp(Value<?> v) {
        throw new RuntimeException("implement me!");
    }

    private Value<?> posDecOp(Value<?> v) {
        throw new RuntimeException("implement me!");
    }

}

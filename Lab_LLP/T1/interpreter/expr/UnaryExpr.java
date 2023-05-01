package interpreter.expr;

import interpreter.value.BoolValue;
import interpreter.value.NumberValue;
import interpreter.value.Value;

public class UnaryExpr extends Expr {

    public static enum Op {
        Not,
        Pos,
        Neg,
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

    public Value<?> expr() {
        Value<?> v = this.expr.expr();
        switch (this.op) {
            case Not:
                return notOp(v);
            case Pos:
                return posOp(v);
            case Neg:
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
        double n = NumberValue.convert(v);
        return new NumberValue(n);
    }

    private Value<?> negOp(Value<?> v) {
        double n = NumberValue.convert(v);
        return new NumberValue(-n);
    }

    private Value<?> preIncOp(Value<?> v) {
        double n = NumberValue.convert(v);
        return new NumberValue(++n);
    }

    private Value<?> posIncOp(Value<?> v) {
        double n = NumberValue.convert(v);
        return new NumberValue(n++);
    }

    private Value<?> preDecOp(Value<?> v) {
        double n = NumberValue.convert(v);
        return new NumberValue(--n);
    }

    private Value<?> posDecOp(Value<?> v) {
        double n = NumberValue.convert(v);
        return new NumberValue(n--);
    }
}

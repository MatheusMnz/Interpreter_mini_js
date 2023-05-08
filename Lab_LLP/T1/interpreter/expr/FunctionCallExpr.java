package interpreter.expr;

import java.util.ArrayList;
import java.util.List;

import interpreter.InterpreterException;
import interpreter.function.Function;
import interpreter.value.FunctionValue;
import interpreter.value.ListValue;
import interpreter.value.Value;

public class FunctionCallExpr extends Expr {

    private Expr expr;
    private List<Expr> args;

    public FunctionCallExpr(int line, Expr expr, List<Expr> args) {
        super(line);
        this.expr = expr;
        this.args = args;
    }
    
    @Override
    public Value<?> expr() {

        Value<?> value  = expr.expr();

        if (value instanceof FunctionValue){

            FunctionValue functionValue = (FunctionValue) value;
            Function f = functionValue.value();

            List<Value<?>> listValue = new ArrayList<Value<?>>();
            
            for (Expr iter: args)
                listValue.add(iter.expr());

            ListValue lv = new ListValue(listValue);
            Variable params = f.getParams();
            params.setValue(lv);
            
            return f.call();
        } else {
            throw new InterpreterException(super.getLine());
        }
    }
}

package interpreter.function;

import interpreter.InterpreterException;
import interpreter.expr.Variable;
import interpreter.value.ListValue;
import interpreter.value.NumberValue;
import interpreter.value.Value;

public class NativeFunction extends Function{

    public static enum Op {
        Log,
        Read,
        Random
    }

    private Op op;

    public NativeFunction (Variable params, Op op) {

        super(params);
        this.op = op; //Pode estar errado, verificar depois
    }

    @Override
    public Value<?> call() { //TA ERRADO 

        switch (op) {
            case Log:
                return callLog();
            case Read:
                return callRead();
            case Random:
            default: 
               return callRandom();
            
        }
    }

    private Value<?> callLog() {
        Value<?> v = super.getParams().expr();
        if (v instanceof ListValue)
            throw new InterpreterException(-1);

        ListValue lv = (ListValue) v;
        for (Value<?> v2 : lv.value())
            System.out.println((v2 == null ? "undefined" : v2.toString()) + " ");

        return null;
    }

    private Value<?> callRead() {
        return null;
    }
        
    private Value<?> callRandom() {
        double d = Math.random();
        return new NumberValue(d);
    }

}
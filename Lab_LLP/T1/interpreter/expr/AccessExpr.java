package interpreter.expr;

import java.util.List;
import java.util.Map;

import interpreter.InterpreterException;
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
    public Value<?> expr() { 
        if(base.expr() instanceof ListValue){
            int i = (int) NumberValue.convert(index.expr());
            ListValue lv = (ListValue) base.expr();
            List<Value<?>> value = lv.value();
            return value.get(i);
        } else if(base.expr() instanceof ObjectValue){
           
            TextValue tv = new TextValue(TextValue.convert(index.expr()));
            ObjectValue ov = (ObjectValue) base.expr();
            Map<TextValue, Value<?>> value = ov.value();
            return value.get(tv);
        } else{
            throw new InterpreterException(super.getLine());
        }
    }

    @Override
    public void setValue(Value<?> value) {

    }

}
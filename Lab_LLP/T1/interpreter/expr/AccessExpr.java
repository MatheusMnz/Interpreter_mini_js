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
        
            if ( i > value.size()){
              return null;
            }
            else{
                return value.get(i);
            }

        } else if(base.expr() instanceof ObjectValue){
           
            TextValue tv = new TextValue(TextValue.convert(index.expr()));
            ObjectValue ov = (ObjectValue) base.expr();
            Map<TextValue, Value<?>> value = ov.value();

            // Caso contenha a key no hashmap, retorno
            if( value.containsKey(tv))
                return value.get(tv);
            else
                return null;
        } else{
            throw new InterpreterException(super.getLine());
        }
    }

    @Override
    public void setValue(Value<?> value) {
        if (base.expr() instanceof ListValue) {
            int i = (int) NumberValue.convert(index.expr());

            ListValue lv = (ListValue) base.expr();
            List<Value<?>> var = lv.value();

            if( i < var.size()){
                var.set(i, value);
                ListValue temp = new ListValue(var);
                base.setValue(temp);
            }
            else{
                for(int k=var.size(); k<i; k++){
                    var.add(k, null);
                }
                
                // Adiciono o elemento na posição especificada
                var.add(i, value);
            }

        } else if (base.expr() instanceof ObjectValue) {
            TextValue tv = new TextValue(TextValue.convert(index.expr()));
            ObjectValue ov = (ObjectValue) base.expr();
            Map<TextValue, Value<?>> var = ov.value();
            var.replace(tv, value);
        } else {
            throw new InterpreterException(super.getLine());
        }
    }
}
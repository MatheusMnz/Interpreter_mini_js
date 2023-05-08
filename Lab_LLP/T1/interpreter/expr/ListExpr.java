package interpreter.expr;

import java.util.ArrayList;
import java.util.List;

import interpreter.value.ListValue;
import interpreter.value.Value;

public class ListExpr extends Expr{
    
    private List<Expr> items;
    
    public ListExpr(int line, List<Expr> items) {
        super(line);
        this.items = items;
    }


    @Override
    public Value<?> expr()
    {
        List<Value<?>> listValueAux = new ArrayList<Value<?>>();

        for(Expr e : items) {
            listValueAux.add(e.expr());
        }

        ListValue listValue = new ListValue(listValueAux);
        return listValue; 
    }
}

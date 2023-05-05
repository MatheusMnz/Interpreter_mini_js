package interpreter.expr;

import java.util.List;

import interpreter.value.Value;;

public class ObjectExpr extends Expr{
    
    private List<ObjectItem> items;

    public ObjectExpr(int line) {
        super(line);
    }

    @Override
    public Value<?> expr() {
        return null;
    }


}

package interpreter.expr;

import java.util.HashMap;
import java.util.List;

import interpreter.value.ObjectValue;
import interpreter.value.TextValue;
import interpreter.value.Value;

public class ObjectExpr extends Expr{
    
    private List<ObjectItem> items;

    public ObjectExpr(int line, List<ObjectItem> items) {
        super(line);
        this.items = items;
    }

    @Override
    public Value<?> expr() {

        // Criando o hashMap do meu Objeto
        HashMap<TextValue, Value<?>> hashMap = new HashMap<TextValue, Value<?>>();

        for(ObjectItem objectItem : items)
        {
            // Para cada iteração eu crio um new TextValue que é a key e adiciono o valor
            TextValue textValue = new TextValue(objectItem.key);
            hashMap.put(textValue, objectItem.value.expr());
        }

        ObjectValue objectList = new ObjectValue(hashMap);
        return objectList;
    }
}
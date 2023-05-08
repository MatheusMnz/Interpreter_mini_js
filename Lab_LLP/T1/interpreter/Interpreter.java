package interpreter;

import java.util.HashMap;
import java.util.Map;

import interpreter.command.Command;
import interpreter.expr.Expr;
import interpreter.value.FunctionValue;
import interpreter.value.ObjectValue;
import interpreter.value.TextValue;
import interpreter.value.Value;
import lexical.Token;
import interpreter.expr.Variable;
import interpreter.function.NativeFunction;
import lexical.Token.Type;

public class Interpreter {

    public final static Environment globals;

    static {
        globals = new Environment();

        //Criando Token de params
        Variable params = globals.declare(new Token("params", Type.NAME, null), false);

        //Criando Token de Console
        Variable console_var = globals.declare(new Token("console", Type.NAME, null), false);
        
        //Gerando Mapas para tratar as NativesFunction do JS
        Map<TextValue, Value<?>> hash_map = new HashMap<TextValue, Value<?>>();
        hash_map.put(new TextValue("log"), new FunctionValue(new NativeFunction(params, NativeFunction.Op.Log)));
        hash_map.put(new TextValue("read")   , new FunctionValue(new NativeFunction(params, NativeFunction.Op.Read)));
        hash_map.put(new TextValue("random") , new FunctionValue(new NativeFunction(params, NativeFunction.Op.Random)));

        // Crio o objeto que vai conter o hashmap
        ObjectValue objFieldValue = new ObjectValue(hash_map);
        console_var.setValue(objFieldValue);
    }

    private Interpreter() {
    }

    public static void interpret(Command cmd) {
        cmd.execute();
    }

    public static void interpret(Expr expr) {

        Value<?> value = expr.expr();
        if (value == null)
            System.out.println("undefined");
        else
            System.out.println(value);
    }

}

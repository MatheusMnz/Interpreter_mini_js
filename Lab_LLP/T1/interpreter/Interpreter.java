package interpreter;

import interpreter.command.Command;
import interpreter.expr.Expr;
import interpreter.value.Value;

public class Interpreter {

    public final static Environment globals;

    static {
        globals = new Environment();
    }

    private Interpreter() {
    }

    public static void interpret(Command cmd) {
        cmd.execute();
    }

    public static void interpret(Expr expr) {
        Value<?> v = expr.expr();
        if (v == null)
            System.out.println("undefined");
        else
            System.out.println(v);
    }

}

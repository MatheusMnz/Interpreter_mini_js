package interpreter;

import interpreter.command.Command;

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

}

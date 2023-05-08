package interpreter.command;

import interpreter.expr.Expr;
import interpreter.value.BoolValue;
import interpreter.value.Value;

public class WhileCommand extends Command {

    private Expr expr;
    private Command cmds;

    public WhileCommand(int line, Expr expr, Command cmds) {
        super(line);
        this.expr = expr;
        this.cmds = cmds;
    }

    @Override
    public void execute() {

        while (true) {
            Value<?> value = expr.expr();
            boolean boolVar = BoolValue.convert(value);
            
            if (!boolVar)
                break;

            cmds.execute();
        }
    }
}

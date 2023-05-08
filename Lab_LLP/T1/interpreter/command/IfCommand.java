package interpreter.command;

import interpreter.expr.Expr;
import interpreter.value.Value;
import interpreter.value.BoolValue;

public class IfCommand extends Command {

    private Command thenCmds;
    private Command elseCmds;
    private Expr expr;

    public IfCommand(int line, Expr expr, Command thenCmds, Command elseCmds) {
        super(line);
        this.expr = expr;
        this.thenCmds = thenCmds;
        this.elseCmds = elseCmds;
    }

    @Override
    public void execute() {

        // Capturo o valor com o expr
        Value<?> value = expr.expr();

        // Realizo a conversão e valido
        boolean boolVar = BoolValue.convert(value);
        if (boolVar) {
            thenCmds.execute();
        } else {
            elseCmds.execute();
        }
    }

}

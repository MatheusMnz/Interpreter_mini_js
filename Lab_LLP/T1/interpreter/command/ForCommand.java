package interpreter.command;

import interpreter.expr.Expr;
import interpreter.expr.Variable;
import interpreter.value.BoolValue;
import interpreter.value.Value;

public class ForCommand extends Command {

    private Expr expr;
    private Command cmds;
    private Variable var;


    public ForCommand(int line,Variable var,Expr expr,Command cmds){
        super(line);
        this.expr = expr;
        this.cmds = cmds;
        this.var = var;

    }



    @Override
    public void execute() {
        while (true) {
            Value<?> v = expr.expr();
            boolean b = BoolValue.convert(v);
            if (!b)
                break;

            cmds.execute();
        }
    }
}

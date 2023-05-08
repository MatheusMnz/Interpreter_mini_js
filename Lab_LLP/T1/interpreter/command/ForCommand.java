package interpreter.command;

import java.util.List;

import interpreter.InterpreterException;
import interpreter.expr.Expr;
import interpreter.value.ListValue;
import interpreter.value.NumberValue;
import interpreter.value.Value;
import interpreter.expr.Variable;

public class ForCommand extends Command {

    private Expr expr;
    private Command cmds;
    private Variable var;

    public ForCommand(int line, Expr expr, Command cmds, Variable var) {
        super(line);
        this.expr = expr;
        this.cmds = cmds;
        this.var = var;
    }

    @Override
    public void execute() {

        Value<?> value = expr.expr();
        int index = (int) NumberValue.convert(var.expr());

        // Checo se o valor é uma instancia de ListValue
        if (value instanceof ListValue) 
        {

            ListValue lv = (ListValue) value;
            List<Value<?>> listValue = lv.value();

            // Para cada elemento eu itero no for
            for(int i = index; i<(int) NumberValue.convert(listValue.get(listValue.size()-1)); i++)
            {
                for (int j = 0; j < listValue.size(); j++)
                {
                    if (index == (int) NumberValue.convert(listValue.get(j))) 
                    {
                        cmds.execute();
                        var.setValue(new NumberValue((double) index));
                    }
                }
                index++;       
            }
            cmds.execute();
        } 
        else 
            throw new InterpreterException(super.getLine());
    }
}
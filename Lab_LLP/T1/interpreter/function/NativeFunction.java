package interpreter.function;

import java.io.BufferedReader;
import java.io.InputStreamReader;


import interpreter.expr.Variable;
import interpreter.value.ListValue;
import interpreter.value.NumberValue;
import interpreter.value.TextValue;
import interpreter.value.Value;

public class NativeFunction extends Function{


    public static enum Op {
        Log,
        Read,
        Random
    } private Op op;


    public NativeFunction (Variable params, Op op) {

        super(params);
        this.op = op; 
    }

    @Override
    public Value<?> call()
    {
        switch (op) {
            case Log:
                return callLog();
            case Read:
                return callRead();
            case Random:
               return callRandom();
            default:
                return null;
        }
    }

    private Value<?> callLog()
    {
        // Recebe um valor
        Value<?> value = super.getParams().expr();

        // Valor pode ser uma Lista de Valores, realizo um casting para o ListValue
        if(value instanceof ListValue){

            ListValue lv = (ListValue) value;

            // Itero cada posição do meu ListValue
            for (Value<?> v2 : lv.value())
                System.out.println((v2 == null ? "undefined" : v2.toString()) + " ");

        }
        return null;
    }

    
    private Value<?> callRead()
    {
        try
        {
            InputStreamReader input = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(input);

            String line = reader.readLine();

            return new TextValue(line);

        } 
        catch(Exception e)
        {
            System.err.println("Erro ao pegar string " + e.getMessage());
        }   

        return new TextValue("ERRO\n");
    }

        
    private Value<?> callRandom()
    {
        double d = Math.random();
        return new NumberValue(d);
    }    
}
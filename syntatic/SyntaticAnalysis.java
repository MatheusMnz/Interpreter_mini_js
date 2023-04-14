package syntatic;

import static lexical.Token.Type.*;

import interpreter.Environment;
import interpreter.Interpreter;
import interpreter.expr.ConstExpr;
import interpreter.expr.Expr;
import interpreter.expr.UnaryExpr;
import interpreter.value.BoolValue;
import interpreter.value.Value;
import lexical.LexicalAnalysis;
import lexical.Token;

public class SyntaticAnalysis {

    private LexicalAnalysis lex;
    private Token current;
    private Token previous;
    private Environment environment;

    public SyntaticAnalysis(LexicalAnalysis lex) {
        this.lex = lex;
        this.current = lex.nextToken();
        this.previous = null;
        this.environment = Interpreter.globals;
    }

    public Expr process() {
        Expr expr = procExpr();
        eat(END_OF_FILE);
        return expr;
    }

    private void advance() {
        System.out.println("Found " + current);
        previous = current;
        current = lex.nextToken();
    }

    private void eat(Token.Type type) {
        if (type == current.type) {
            advance();
        } else {
            System.out.println("Expected (..., " + type + ", ..., ...), found " + current);
            reportError();
        }
    }

    private boolean check(Token.Type ...types) {
        for (Token.Type type : types) {
            if (current.type == type)
                return true;
        }

        return false;
    }

    private boolean match(Token.Type ...types) {
        if (check(types)) {
            advance();
            return true;
        } else {
            return false;
        }
    }

    private void reportError() {
        String reason;
        switch (current.type) {
            case INVALID_TOKEN:
                reason = String.format("Lexema inválido [%s]", current.lexeme);
                break;
            case UNEXPECTED_EOF:
            case END_OF_FILE:
                reason = "Fim de arquivo inesperado";
                break;
            default:
                reason = String.format("Lexema não esperado [%s]", current.lexeme);
                break;
        }

        throw new SyntaticException(current.line, reason);
    }

    // <code> ::= { <cmd> }
    private void procCode() {
        while (check(OPEN_CUR, CONST, LET, DEBUG,
                IF, WHILE, FOR, NOT, ADD, SUB,
                INC, DEC, OPEN_PAR, UNDEFINED,
                FALSE, TRUE, NUMBER, TEXT,
                OPEN_BRA, OPEN_CUR, FUNCTION, NAME)) {
            procCmd();
        }
    }

    // <cmd> ::= <block> | <decl> | <debug> | <if> | <while> | <for> | <assign>
    private void procCmd() {
        if (check(OPEN_CUR)) {
            procBlock();
        } else if (check(CONST, LET)) {
            procDecl();
        } else if (check(DEBUG)) {
            procDebug();
        } else if (check(IF)) {
            procIf();
        } else if (check(WHILE)) {
            procWhile();
        } else if (check(FOR)) {
            procFor();
        } else {
            procAssign();
        }
    }

    // <block> ::= '{' <code> '}'
    private void procBlock() {
        eat(OPEN_CUR);
        procCode();
        eat(CLOSE_CUR);
    }

    // <decl> ::= ( const | let ) <name> [ '=' <expr> ] { ',' <name> [ '=' <expr> ] } ';'
    private void procDecl() {
        if (match(CONST, LET)) {
            // fazer nada
        } else {
            reportError();
        }

        procName();

        if (match(ASSIGN)) {
            procExpr();
        }

        while (match(COMMA)) {
            procName();

            if (match(ASSIGN)) {
                procExpr();
            }
        }

        eat(SEMICOLON);
    }

    // <debug> ::= debug <expr> ';'
    private void procDebug() {
        eat(DEBUG);
        procExpr();
        eat(SEMICOLON);
    }

    // <if> ::= if '(' <expr> ')' <cmd> [ else <cmd> ]
    private void procIf() {
        eat(IF);
        eat(OPEN_PAR);
        procExpr();
        eat(CLOSE_PAR);
        procCmd();
        if (match(ELSE)) {
            procCmd();
        }
    }

    // <while> ::= while '(' <expr> ')' <cmd>
    private void procWhile() {
        eat(WHILE);
        eat(OPEN_PAR);
        procExpr();
        eat(CLOSE_PAR);
        procCode();
    }

    // <for> ::= for '(' [ let ] <name> in <expr> ')' <cmd>
    private void procFor() {
        eat(FOR);
        eat(OPEN_PAR);
        if (match(LET)) {
            // fazer nada
        }
        procName();
        eat(IN);
        procExpr();
        eat(CLOSE_PAR);
        procCmd();
    }

    // <assign> ::= [ <expr> '=' ] <expr> ';'
    private void procAssign() {
        procExpr();

        if (match(ASSIGN)) {
            procExpr();
        }

        eat(SEMICOLON);
    }

    // <expr> ::= <cond> [ '?' <expr> ':' <expr> ]
    /*
     * 
     * 
     * 
     * É ISSO MESMO?
     * 
     */
    private Expr procExpr() {
        Expr expr = procCond();

        if(match(TERNARY, COLON)){
            procExpr();
        }
        return expr;
    }

    // <cond> ::= <rel> { ( '&&' | '||' ) <rel> }
    private Expr procCond() {
        Expr expr = procRel();
        while (match(AND, OR)) {
            // TODO: lembrar de implementar.
            procRel();
        }

        return expr;
    }

    // <rel> ::= <arith> [ ( '<' | '>' | '<=' | '>=' | '==' | '!=' ) <arith> ]
        /*
     * 
     * 
     * 
     * É ISSO MESMO?
     * 
     */
    private Expr procRel() {
        Expr expr = procArith();

        if(match(LOWER_THAN, GREATER_THAN, LOWER_EQUAL, GREATER_EQUAL, EQUALS, NOT_EQUALS)){
            procArith();
        }
        return expr;
    }

    // <arith> ::= <term> { ( '+' | '-' ) <term> }
    private Expr procArith() {
        Expr expr = procTerm();
        while (match(ADD, SUB)) {
            // TODO: lembrar de implementar.
            procTerm();
        }

        return expr;
    }

    // <term> ::= <prefix> { ( '*' | '/' ) <prefix> }
        /*
     * 
     * 
     * 
     * É ISSO MESMO?
     * 
     */
    private Expr procTerm() {
        Expr expr = procPrefix();

        while(match(MUL, DIV)){
            procPrefix();
        }
        return expr;
    }

    // <prefix> ::= [ '!' | '+' | '-' | '++' | '--' ] <factor>
    private Expr procPrefix() {
        Token token = null;
        if (match(NOT, ADD, SUB, INC, DEC)) {
            token = previous;
        }

        Expr expr = procFactor();

        if (token != null) {
            UnaryExpr.Op op;
            switch (token.type) {
                case NOT:
                    op = UnaryExpr.Op.NotOp;
                    break;
                case ADD:
                    op = UnaryExpr.Op.PosOp;
                    break;
                case SUB:
                    op = UnaryExpr.Op.NegOp;
                    break;
                case INC:
                    op = UnaryExpr.Op.PreInc;
                    break;
                case DEC:
                default:
                    op = UnaryExpr.Op.PreDec;
                    break;
            }

            UnaryExpr uexpr = new UnaryExpr(token.line,
                expr, op);
            expr = uexpr;
        }

        return expr;
    }

    // <factor> ::= ( '(' <expr> ')' | <rvalue> ) <calls>  [ '++' | '--' ]
    private Expr procFactor() {
        Expr expr = null;
        if (match(OPEN_PAR)) {
            procExpr();
            eat(CLOSE_PAR);
        } else {
            expr = procRValue();
        }

        procCalls();

        if (match(INC, DEC)) {
            // fazer nada
        }

        return expr;
    }

    // <rvalue> ::= <const> | <list> | <object> | <function> | <lvalue>
    private Expr procRValue() {
        Expr expr = null;
        if (check(UNDEFINED, FALSE, TRUE, NUMBER, TEXT)) {
            Value<?> v = procConst();
            expr = new ConstExpr(previous.line, v);
        } else if (check(OPEN_BRA)) {
            procList();
        } else if (check(OPEN_CUR)) {
            procObject();
        } else if (check(FUNCTION)) {
            procFunction();
        } else {
            procLValue();
        }

        return expr;
    }

    // <const> ::= undefined | false | true | <number> | <text>
    private Value<?> procConst() {
        Value<?> v = null;
        if (match(UNDEFINED, FALSE, TRUE)) {
            switch (previous.type) {
                case UNDEFINED:
                    v = null;
                    break;
                case FALSE:
                    v = new BoolValue(false);
                    break;
                case TRUE:
                default:
                    v = new BoolValue(true);
                    break;
            }
            // fazer nada
        } else if (check(NUMBER)) {
            v = procNumber();
        } else if (check(TEXT)) {
            v = procText();
        } else {
            reportError();
        }

        return v;
    }

    // <list> ::= '[' [ <expr> { ',' <expr> } ] ']'
    private void procList() {
        eat(OPEN_BRA);

        if (check(NOT, ADD, SUB, INC, DEC, OPEN_PAR,
                UNDEFINED, FALSE, TRUE, NUMBER, TEXT, OPEN_BRA,
                OPEN_CUR, FUNCTION, NAME)) {
            procExpr();

            while (match(COMMA)) {
                procExpr();
            }
        }

        eat(CLOSE_BRA);
    }

    // <object> ::= '{' [ <name> ':' <expr> { ',' <name> ':' <expr> } ] '}'
    private void procObject() {
        eat(OPEN_CUR);

        if (check(NAME)) {
            procName();
            eat(COLON);
            procExpr();

            while (match(COMMA)) {
                procName();
                eat(COLON);
                procExpr();
            }
        }

        eat(CLOSE_CUR);
    }

    // <function> ::= function '(' ')' '{' <code> [ return <expr> ';' ] '}'
    private void procFunction() {
        eat(FUNCTION);
        eat(OPEN_PAR);
        eat(CLOSE_PAR);
        eat(OPEN_CUR);
        procCode();
        if (match(RETURN)) {
            procExpr();
            eat(SEMICOLON);
        }
        eat(CLOSE_CUR);
    }

    // <lvalue> ::= <name> { '.' <name> | '[' <expr> ']' }
    private void procLValue() {
        procName();

        while (check(DOT, OPEN_BRA)) {
            if (match(DOT)) {
                procName();
            } else {
                procExpr();
                eat(CLOSE_BRA);
            }
        }

    }

    // <calls> ::= { '(' [ <expr> { ',' <expr> } ] ')' }
    private void procCalls() {
        while (match(OPEN_PAR)) {
            if (check(NOT, ADD, SUB, INC, DEC, OPEN_PAR,
                    UNDEFINED, FALSE, TRUE, NUMBER, TEXT, OPEN_BRA,
                    OPEN_CUR, FUNCTION, NAME)) {
                procExpr();
                while (match(COMMA)) {
                    procExpr();
                }
            }

            eat(CLOSE_PAR);
        }
    }

    private Value<?> procNumber() {
        eat(NUMBER);
        return previous.literal;
    }

    private Value<?> procText() {
        eat(TEXT);
        return previous.literal;
    }

    private void procName() {
        eat(NAME);
    }

}

package ast;

public enum Operator {

    PLUS("+"),
    MINUS("-"),
    TIMES("*"),
    DIV("/"),
    MOD("mod"),
    EQ("="),
    NEQ("#"),
    LTHAN("<"),
    LEQ("<="),
    GTHAN(">"),
    GEQ(">="),
    AND("and"),
    OR("or"),
    NOT("not");

    private String type;

    private Operator(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}

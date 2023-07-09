package ast;

public enum Operator {

    PLUS("+"),
    MINUS("-"),
    TIMES("*"),
    DIV("div"),
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

    public boolean isUnary() {
        return this.equals(Operator.PLUS) || this.equals(Operator.MINUS) || this.equals(Operator.NOT);
    }

    public boolean isRelational() {
        return this.equals(Operator.EQ) || this.equals(Operator.NEQ)
                || this.equals(Operator.GTHAN) || this.equals(Operator.GEQ)
                || this.equals(Operator.LTHAN) || this.equals(Operator.LEQ)
                || this.equals(Operator.AND) || this.equals(Operator.OR);
    }
}

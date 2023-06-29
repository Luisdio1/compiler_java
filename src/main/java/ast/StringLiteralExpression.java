package ast;

public class StringLiteralExpression extends Expression {

    private String literal;
    private Expression expression;

    public StringLiteralExpression(String literal, Expression expression) {
        this.literal = literal;
        this.expression = expression;
    }

    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }

}

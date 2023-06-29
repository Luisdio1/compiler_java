package ast;

public class IdentifierExpression extends Expression {

    private String identifier;
    private Expression expression;

    public IdentifierExpression(String identifier, Expression expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifer) {
        this.identifier = identifer;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }

}

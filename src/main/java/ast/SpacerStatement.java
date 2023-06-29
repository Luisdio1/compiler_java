package ast;

public class SpacerStatement extends Statement {

    private String identifier;
    private Expression expression1;
    private Expression expression2;

    public SpacerStatement(String identifier, Expression expression2) {
        this.identifier = identifier;
        this.expression2 = expression2;
    }

    public SpacerStatement(Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Expression getExpression1() {
        return expression1;
    }

    public void setExpression1(Expression expression1) {
        this.expression1 = expression1;
    }

    public Expression getExpression2() {
        return expression2;
    }

    public void setExpression(Expression expression2) {
        this.expression2 = expression2;
    }

    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }

}

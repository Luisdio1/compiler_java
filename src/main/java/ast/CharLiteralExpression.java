package ast;

public class CharLiteralExpression extends Expression {

    private char literal;

    public CharLiteralExpression(char literal) {
        this.literal = literal;
    }

    public char getLiteral() {
        return literal;
    }

    public void setLiteral(char literal) {
        this.literal = literal;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
}
